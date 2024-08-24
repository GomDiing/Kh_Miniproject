import {useNavigate} from 'react-router-dom';
import styled from "styled-components";
import { useEffect,useState } from 'react';
import Modal from '../../util/Modal';
import MovieApi from '../../api/MovieApi';

const FindPwdBlock=styled.div`
    .find-container{
        position: absolute;
        top: 60px;
        bottom: 60px;
        width : 100%;
        padding: 0 20px;
        max-width: 600px;
        left: 50%;
        transform: translate(-50%, 0);
        background-color: #393E46;
        color: #EEEEEE;
        overflow: hidden;
        display: flex;
        flex-direction: column;
    }
    .inputWrap{
        display: flex;
        border-radius: 8px;
        padding: 16px;
        margin-top: 8px;
        border: 2px solid #EEEEEE;
    }
    .inputWrap:focus-within{border: 2px solid #FFD369;}
    .input{
        width: 100%;
        outline: none;
        border: none;
        height: 17px;
        font-size: 14px;
        font-weight: 400px;
        background-color: inherit;
        color: #EEEEEE;
    }
    .input::placeholder{color : #dadada}
    .loginButton, .findButton{
        width: 100%;
        height: 48px;
        border: none;
        font-weight: 18px;
        cursor: pointer;
        border-radius: 60px;
        background-color: #FFD369;
        color:#222831;
        font-weight: bold;
    }
    .findButton:disabled{background-color: #222831;color: #EEEEEE;}
    hr{
        border: 2px solid #EEEEEE;
        height: 4px;
        margin: 35px 0;
    }
    h2{
        margin-top: 87px;
        font-size: 30px;
        font-weight: bold;
        text-align: center;
        margin-bottom: 30px;
    }
    .findDesc{
        text-align: center;
        font-size: 14px;
    }
    div{
        margin-bottom: 20px;
    }
`;
const FindPwd=()=>{
    let navigate = useNavigate();
    const [inputId, setInputId] = useState("");
    const [inputEmail, setInputEmail] = useState("");
    const [modalOpen, setModalOpen] = useState(false);
    const [modalHeader, setModalHeader] = useState(false);
    const [modalText, setModalText] = useState(false);
    const [submit, setSubmit] = useState(true);

    const onChangeId=(e)=>{
        setInputId(e.target.value);
    }
    const onchangeEmail=(e)=>{
        setInputEmail(e.target.value);
    }
    const closeModal = () => {
        setModalOpen(false);
    };
    const onClickFind=async()=>{
        try {
        const findPassword = await MovieApi.findPassword(inputId, inputEmail);
        console.log(findPassword.status);
        if(findPassword.data.statusCode === 200) {
            console.log(findPassword);
            const pwd = findPassword.data.results.password;
            setModalOpen(true);
            setModalHeader(inputId+" 님의 비밀번호는");
            setModalText(pwd+" 입니다.");
        }
    } catch (e) {
        setModalOpen(true);
        setModalHeader("오류");
        setModalText("아이디와 이메일이 일치하지 않습니다.");
    }
    }
    useEffect(()=>{
        if(inputId&&inputEmail){
            setSubmit(false);
            return;
        }setSubmit(true);
    }, [inputId, inputEmail]);

    return(
        <FindPwdBlock>
        <div className='find-container'>
        <h2>비밀번호 찾기</h2>
        <div className='findDesc'>가입 된 아이디와 이메일을 통해 찾을 수 있습니다. </div>
        <div className="inputWrap"><input className="input" placeholder="아이디를 입력하세요*" type="text" value={inputId} onChange={onChangeId}/></div>
        <div className="inputWrap"><input className="input" placeholder="이메일을 입력하세요*" type="email" value={inputEmail} onChange={onchangeEmail}/></div>
        <div className="item"><button type="submit" className="findButton" onClick={onClickFind} disabled={submit}>비밀번호 찾기</button></div>
        <hr/>
        <div className="item"><button type="button" className="loginButton" onClick={()=>{navigate('/Login/LoginPage')}}>로그인하러 가기</button></div>
        <Modal open={modalOpen} close={closeModal} header={modalHeader}>{modalText}</Modal>
        </div>
        </FindPwdBlock>
    );
}
export default FindPwd;