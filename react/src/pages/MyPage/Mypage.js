import { Link } from "react-router-dom";
import { BsFillPersonFill } from "react-icons/bs";
import styled from "styled-components";
import MovieApi from "../../api/MovieApi";
import { useEffect, useState } from "react";
import LoginPage from "../Login/LoginPage";

const StyleMypage = styled.div`
    box-sizing: border-box;
    margin: 0 auto;
    color: white;
.nav {
    text-decoration: none;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 90px;
}
.nav .nav-link, .mypage {
    font-size: 1.2em;
    color: white;
    color: #ffd369;
}
.nav .mypage {
    font-size: 1.5em;
    font-weight: bold;
    color: #ffd369;
    pointer-events: none;
}
.container {
    border: 5px solid #ffd369;
    margin: 0 auto;
    width: 60%;
    height: 90%;
}
.info {
    margin-top:2rem;
    margin-left: 1rem;
    font-weight: 200;
    color: #ffd369;
    font-size: 2rem;
}
.container .profil {
    text-decoration: none;
    display: inline;
    background-color: #ffd369; 
    color: #232323;
    font-size: 1.5em;
    padding: 8px;
    border-radius: 15px;
}
.logout {
    float: right;
    font-size: 1.4em;
    background-color: #ffd369;
    color: #232323;
    margin-right: 0.5rem;
    padding: 7px;
    border-radius: 15px;
}
.delete-member {
    float: right;
    text-decoration: none;
    font-size: 1.4em;
    color: #232323;
    border: 1px solid white;
    border-radius: 15px;
    background-color: #ffd369;
    text-align: center;
    margin-right: -0.25rem;
    padding: 2px;
}
.container-system {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 0 auto;
    width: 70%;
    height: 100%;
    text-align: center;
}
table, th, tr, td {
    border: 1px solid #ffd369;
    margin: 0 auto;
}
h1 {
    text-align: center;
    margin: 3rem 0;
    color: #ffd369;
    font-weight: bold;
}
.button {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 2rem 0;
}
.select-member {
    margin: 0 1.4rem;
}
button {
    border: 2px solid silver;
    background-color: #232323;
    color: #ffd369;
    height: 50px;
}
.list-open {
    border: 1px solid #ffd369;
    background-color: #ffd369;
    color: black;
    font-weight: 600;
    font-size: 1rem;
    margin: 0 auto;
    display: flex;
    align-items: center;
    margin-bottom: 2rem;
    border-radius: 15px;
}
@media screen and (max-width: 768px){
    .container {
        width: 100%;
        height: 100%;
    }
    .profil {
        width: 30%;
        height: auto;
    }
    .logout {
        width: 25%;
        height: auto;
    }
    .delete-member {
        width: auto;
        height: auto;
    }
    .container-system {
        width: 100%;
        height: 100%;
    }
    table, tr, td {
        width: 100%;
        height: 100%;
    }
    .num, .name, .id, .time, .email, .pwd {
        width: 10%;
    }
}
.total {
    // 크기 줄이면 안에 표가 테두리 밖으로 빠져나가는거 방지
    word-break:break-all;
}
`;

    // 관리자 계정인지 확인
    const userId = window.localStorage.getItem("userId");
    let userAdmin = false;
    if(userId === 'admin123') {
        userAdmin = true;
    }

    /** 로그아웃 함수 */
    const onClickLogout = () =>{
        window.localStorage.setItem("userId", '');
        window.localStorage.setItem("userPwd",'');
        window.localStorage.setItem("isLogin", "false")
        window.location.replace("/");
    }
    
    /** 유저 계정 페이지 */
    function UserMypage() {
    
    // 본인 회원 정보를 담을 변수
    const [memberSelect, setMemberSelect] = useState('');
    const [listInfo, setListInfo] = useState('');
    // 조건부 렌더링을 위한 변수
    const [listOpen, setListOpen] = useState(false);

    // userEffect를 통해 회원정보만 가져옴
        useEffect(() => {
            const memberInfo = async () => {
            try {
                // 위에서 window.localstorage로 받아온 아이디를 넣어서 그 회원의 정보만 조회
                const response = await MovieApi.memberSelect(userId);
                setMemberSelect(response.data.results);
            } catch(e) {
                console.log(e);
            }
        };
        memberInfo();
    }, []);

    // 본인이 쓴 문의 내역을 확인
    useEffect(() => {
        const info = async () => {
            try {
                const res = await MovieApi.inquireInfo();
                setListInfo(res.data.results);
            } catch(e) {
                console.log(e);
            }
        }
        info();
    }, []);

    // 비로그인 접근 금지
    let isLogin = window.localStorage.getItem('isLogin');
        if(isLogin !== 'true') {
            return(
                <LoginPage />
            );
        }
    
    /** 문의 내역을 배열로 데이터를 받아서 그것을 또 map함수를 이용하여 내역을 추출하여 컴포넌트로 사용*/
    const Tbody = () => (
        listInfo && listInfo.map(list => (
            <tbody key={list.id}>
                {/* 회원 아이디랑 아까 위에서 받은 유저 아이디랑 같은 문의 내역만 가지고 올 수 있도록 조건부 렌더링 */}
                {list.member_id === userId ?
                    <tr>
                        <td>{list.member_id}</td>
                        <td>{list.qna_title}</td>
                        <td>{list.qna_content}</td>
                        <td>{list.create_time}</td>
                    </tr> :
                    <></>}
            </tbody>
        )));
    
    /** 클릭 하면 리스트가 보이고 다시 하면 가려지는 함수. */
    const onClickList = () => {
        setListOpen(!listOpen);
    };

return (
    <StyleMypage>
        <div>
        <nav className="nav nav-pills nav-justified">
            <Link className="nav-link" style={{fontSize: '25px'}} to="/">홈</Link>
            <Link className="nav-link mypage" aria-current="page" to="./" mypage aria-disabled>마이페이지</Link>
            <Link className="nav-link" to="/MyPage/Inquire">1:1 문의하기</Link>
        </nav>
        <div className="container">
            <BsFillPersonFill size='160px' style={{border:'1px solid silver', margin:'30px 10px', backgroundColor: '#232323', color: '#ffd369'}}/>
            <button className="logout" style={{marginTop: '1rem'}} onClick={onClickLogout}>로그아웃</button>
            <br />
            <Link className="profil" to="/MyPage/Infoset">회원 정보 수정</Link> 
                {/* 위에서 회원정보를 memberSelect에 받아서 그 정보를 뿌려준다 */}
                <div key={memberSelect.id}>
                    <h3 className="info">이름  : {memberSelect.name}</h3>
                    <h3 className="info">아이디 : {memberSelect.id}</h3>
                    <h3 className="info">이메일 : {memberSelect.email}</h3>
                    <h3 className="info">가입일 : {memberSelect.create_time}</h3>
                </div>
            <br /> <br /><br />
            <button className="list-open" onClick={onClickList}>내 문의 내역</button>
            {/* 문의 내역 클릭하면 보이고 안 보이게 하는 그거.. */}
            {listOpen &&
            <table className="total" >
                <thead className="total">
                    <tr className="total">
                        <th className="total">회원 아이디</th>
                        <th className="total">문의 제목</th>
                        <th className="total">문의 내용</th>
                        <th className="total">문의 날짜</th>
                    </tr>
                </thead>
                {/* 위에서 짠 list.map 을 하나의 컴포넌트로 가져옴 */}
                <Tbody />
            </table>}
            <br/>
            <Link className="delete-member" to="/MyPage/DeleteAccount">회원 탈퇴</Link>
            <br/><br />
        </div>
    </div>
    </StyleMypage>
    );
}

/** 관리자 계정 페이지 */
function AdminPage() {

    // 회원정보를 담기 위한 변수와 number는 밑에 회원 리스트 조회 시 필요로 설정
    const [memberInfo, setMemberInfo] = useState('');
    let [number, setNumber] = useState(10);

    /** 회원 더 보기 기능인데 최대 회원이 예를 들어 40명이면 40명에서 더 눌렀을 때 조회 가능한 회원이 없다고 알림창 띄우게.. 설정하고
    return number = 39로 설정하였다. 40으로 하면 다시 알림창이 뜰 것 같아서...?
     */
    const onClickButton = () => {
        setNumber(number + 10);
        if(number >= 40) {
            alert('더 이상 조회 가능한 회원이 없습니다.');
            return number === 39;
        }
    }
    /** 회원 덜 보기 했을 때 10명 기준으로 회원을 보여주기 때문에 -10을 하면 된다 */
    const onClickButton2 = () => {
        setNumber(number - 10);
    }
    /** 전체 회원이 30명이면 30명을 다 보여준다 999명이면 + 999*/
    const onClickButtonAll = () => {
        setNumber(number + 30);
    }
    /** 0으로 만들면 전체 닫기 버튼 */
    const onClickButtonAll2 = () => {
        setNumber(number === 0);
    }
    // useEffect를 이용한 회원정보 전체를 불러옴
    useEffect(() => {
        const memberSelect = async () => {
            try {
                const response = await MovieApi.memberInfo();
                // memberInfo 변수에 데이터를 담음
                setMemberInfo(response.data.results);
            } catch(e) {
                console.log(e);
            }
        }
        memberSelect();
    }, []);
    
    /** 위에 유저 페이지랑 똑같이 밑에 코드에 들어가면 지저분 할 것 같아서 하나의 컴포넌트로 만들어서 사용 */
    const Tbody = () => (
        /* 회원 정보를 가져오는데 매개변수를 member만 받았다가 10명씩 조회하고 싶어서 index라는 매개변수를 추가
        그리고 index와 위에 number를 이용한 회원정보 10명씩 조회 기능입니다. 모르는 것은 물어보시면 설명을 해드리겠습니다.
        */
        memberInfo && memberInfo.map((member, index) => (
            <tbody key={member.id}>
                {index < number ?
                    <tr>
                        <td className="num">{member.id}</td>
                        <td className="name">{member.name}</td>
                        <td className="id">{member.member_id}</td>
                        <td className="pwd">{member.password}</td>
                        <td className="email">{member.email}</td>
                        <td className="time">{member.create_time}</td>
                    </tr> :
                    <></>}
            </tbody>
        )));

    return (
    <StyleMypage>
        <div>
        <nav className="nav nav-pills nav-justified">
            <Link className="nav-link" style={{fontSize: '25px'}} to="/">홈</Link>
            <Link className="nav-link mypage" aria-current="page" to="./"mypage aria-disabled>관리자 페이지</Link>
            <Link className="nav-link" to="/MyPage/InquireList">문의 내역 확인</Link>
            <button className="logout" onClick={onClickLogout}>로그아웃</button>
        </nav>
            <h1>회원 목록 조회</h1>
        <div className="container-system">
            <table>
                <thead>
                    <tr>
                        <th>회원 번호</th>
                        <th>이름</th>
                        <th>아이디</th>
                        <th>패스워드</th>
                        <th>이메일</th>
                        <th>가입일</th>
                    </tr>
                </thead>
                {/* 위에서 만든 회원정보 컴포넌트를 사용 */}
                <Tbody />
            </table>
        </div>
        <div className="button">
            <button className="select-member" onClick={onClickButton}>회원 더 보기</button>
            {/* 여기에 조건부 렌더링을 한 이유는 처음에 보여주는 회원이 10명으로 설정했는데 10명일 때는 덜 보기 버튼을 onClick 함수를 없게 설정함. */}
            {number === 10 ? <button className="select-member">회원 덜 보기</button>:
            <button className="select-member" onClick={onClickButton2}>회원 덜 보기</button>
            }
            <button className="select-member" onClick={onClickButtonAll}>회원 전체 보기</button>
            <button className="select-member" onClick={onClickButtonAll2}>회원 전체 닫기</button>
        </div>
    </div>
    </StyleMypage>
    );
}

/** 화면을 보여주는 것인데 위에서 관리자면 관리자 페이지 아니면 일반 유저의 페이지를 보여주기 위해서 조건부 렌더링 */
const Mypage = () => {
    return (
    <div>
        {userAdmin ?
            <AdminPage /> :
            <UserMypage />
        }
    </div>
    );
}

export default Mypage;