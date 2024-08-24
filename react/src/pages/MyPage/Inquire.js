import { useState } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import styled from "styled-components";
import MovieApi from "../../api/MovieApi";
import LoginPage from "../Login/LoginPage";
import Main from "../Main";
import Mypage from "./Mypage";

const StyleInquire = styled.div`
/* 1:1 문의하기 페이지 css */
.nav {
    text-decoration: none;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 90px;
}
.nav .nav-link, .mypage {
    font-size: 1.2em;
    color: #ffd369;
}
.nav .mypage {
    font-size: 1.5em;
    font-weight: bold;
    color: #ffd369;
}
.inquire-container {
    display: flex;
    flex-direction: column;
    flex-wrap: nowrap;
    align-items: center;
    justify-content: center;
    width: 50vw;
    height: 100vh;
    color: white;
    margin: 0 auto;
    padding: 3rem 0;
    border: 5px solid #ffd369;
}
.inquire {
    margin: 1rem 0;
    width: 40vw;
    font-size: 20px;
}
.content {
    width: 40vw;
    height: 80vh;
    background-color: #eeeeee;
    font-size: 20px;
}
.inquire-submit {
    width: 15vw;
    height: 10vh;
    margin: 1rem 0;
    border-radius: 8px;
    background-color: #ffd369;
    color: #232323;
    font-weight: bold;
    font-size: large;
}
.inquire-head {
    margin: 3rem 0;
    font-weight: bold;
    color: #ffd369;
    text-align: center;
}
.inquire-text {
    font-size: 30px;
    color: #ffd369;
    margin-bottom: 20px;
}
@media screen and (max-width: 768px){
    .inquire-container {
        width: 100%;
        height: 100%;
    }
    .content {
        width: 80%;
        height: 250px;
    }
}
`;

const Inquire = () => {
    let userId = window.localStorage.getItem("userId");

    // 문의 내용 입력받기
    const [inputText, setInputText] = useState("");
    const [inputSelect, setInputSelect] = useState("단순 변심");
    const navigate = useNavigate();

    // 비로그인 상태 도메인 입력 접속 시 막습니다.
    let isLogin = window.localStorage.getItem('isLogin');
    if(isLogin !== 'true') {
        return(
            <LoginPage />
        );
    }

    // 문의 이유와 내용의 값을 담아줌
    const onChangeText = e => setInputText(e.target.value);
    const onChangeSelect = e => setInputSelect(e.target.value);
    
    /** 문의 내용 전송 함수 */
    const onClickSubmit = async e => {
        if(inputText.length <= 10 || inputText.length >= 1000) {
            alert('문의 사항을 최소 10 ~ 1000글자 이내로 부탁드립니다.');
            e.preventDefault();
        } else {
            const send = await MovieApi.qnaSend(userId, inputSelect, inputText);
            if(send.data.statusCode === 200) {
                alert('문의가 정상 전송 되었습니다.');
            } else {
                alert('문의 전송이 실패 하였습니다.');
                e.preventDefault();
            }
        }
    }
    
    return ( 
    <StyleInquire>
        <div>
            <nav className="nav nav-pills nav-justified">
                <Link className="nav-link" style={{fontSize: '25px'}} to="/">홈</Link>
                <Link className="nav-link mypage" to="/MyPage/Mypage">마이페이지</Link>
                <Link className="nav-link" to="/MyPage/infoset">회원정보수정</Link>
            </nav>
                <h1 className='inquire-head'>1:1 문의하기</h1>
            <form className="inquire-container">
                    <label id='why' className="inquire-text">문의 이유를 선택해주세요.</label>
                    <select id="why" className="inquire" value={inputSelect} onChange={onChangeSelect}>
                        <option>단순 변심</option>
                        <option>시작할 때 문제가 있음</option>
                        <option>사이트가 맘에 들지가 않음</option>
                        <option>개인정보 보호 문제</option>
                        <option>광고가 너무 많음</option>
                        <option>기타</option>
                    </select>
                    <p />
                    <textarea className="content" placeholder="문의 내용" rows={50} cols={50} value={inputText} onChange={onChangeText} minLength={30} maxLength={1000}/>
                    <p />
                    <Link to='/' onClick={onClickSubmit}><button className="inquire-submit">제출</button></Link>
            </form>
        </div>
    </StyleInquire>
    );
}   

export default Inquire;