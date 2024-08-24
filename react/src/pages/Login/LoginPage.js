import { useEffect, useState } from "react";
import {useNavigate} from 'react-router-dom';
import styled from "styled-components";
import { Link } from 'react-router-dom';
import Modal from "../../util/Modal";
import MovieApi from "../../api/MovieApi";

const LoginBlock=styled.div`
.page{
    position: absolute;
    top: 135px;
    bottom: 60px;
    width : 100%;
    padding: 0 20px;
    max-width: 600px;
    left: 50%;
    transform: translate(-50%, 0);
    background-color: #393E46;
    overflow: hidden;
    display: flex;
    flex-direction: column;
    height : 700px; // 높이 만 추가했습니다.
}
.titleWrap{
    color: #EEEEEE;
    margin-top: 80px;
    font-size: 30px;
    font-weight: bold;
    text-align: center;
}
.titleWrap img{
    size: initial;
    margin-right :10px;
} 
.loginWrap{
    flex: 1;
}
.inputTitle{
    margin-top: 17px;
    font-size: 12px;
    font-weight: 500px;
    color: #EEEEEE;
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
.error{
    margin-top: 8px;
    font-size: 12px;
    color: #FFD369;
}
.auto{
    color:#EEEEEE;
    margin-left: 10px;
    margin-top: 15px;
}
label{
    margin-left: 10px;
}
.loginButton, .goButton{
    width: 100%;
    height: 48px;
    border: none;
    font-weight: 18px;
    cursor: pointer;
    border-radius: 60px;
    background-color: #FFD369;
    color:#222831;
    margin: 15px 0 ;
    font-weight: bold;
}
.find{
    text-align: center;
    margin-top: 20px;
    color: #EEEEEE;
    cursor: pointer;
    span{
        margin: 0 15px;
    }
}
.findId:hover,.findPwd:hover{color: #FFD369;}
.loginButton:disabled {background-color: #222831;color: #EEEEEE;}
hr{
    border: 2px solid #EEEEEE;
    height: 4px;
    margin: 35px 0;
}
.goButton{
    margin-bottom: 40px;
}
.logo img{
    margin-top: 20px;
    size: initial;
    margin-left :5px;
}      
img{
    width: 80px;
    height:70px;
} 
`;
const LoginPage=()=>{
    // 처음에 userID, userPwd의 값을 ''으로 잡는다 그러면 처음 접속하는 사람들도 userId의 값이 생김
    window.localStorage.setItem('userId', '');
    window.localStorage.setItem('userPwd', '');
    let navigate = useNavigate();

    const [inputId, setInputId] = useState(window.localStorage.getItem(window.localStorage.getItem('autoLogin') === "TRUE" ? 'autoId' : 'userId'));
    const [inputPwd, setInputPwd] = useState(window.localStorage.getItem(window.localStorage.getItem('autoLogin') === "TRUE" ? 'autoPwd' : 'userPwd'));
    const [isId, setIsId] = useState(false);
    const [isPwd, setIsPwd] = useState(false);
    const [submit, setSubmit] = useState(true);
    const [autoLogin,setAutoLogin] = useState(false);

    const onChangeId = (e) => {
        const idCurrent = e.target.value; // 변수 하나 만들어서 실시간 적용되게
        setInputId(idCurrent)
        const regId = /^[A-Za-z]+[A-Za-z0-9]{5,19}$/g;
        if(regId.test(idCurrent)){
            setIsId(true);
        }else{
            setIsId(false)
        }};
    const onChangePwd = (e) => {
        const pwdCurrent = e.target.value;
        setInputPwd(pwdCurrent);
        const regPwd = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}$/;
        if(regPwd.test(pwdCurrent)){
            setIsPwd(true);
        }else{
            setIsPwd(false)
        }};

    // 유효값 충족되는지 데이터 추가될때마다 확인
    useEffect(()=>{
        if(isId&&isPwd){
            setSubmit(false);
            return;
        }setSubmit(true);
    }, [isId, isPwd]);
    const [modalOpen, setModalOpen] = useState(false);

    const closeModal = () => {
        setModalOpen(false);
    };

    const onClickLogin = async()=>{
        try {
            const res = await MovieApi.userLogin(inputId,inputPwd);
            console.log(res.data.result);

            if(res.data.statusCode === 200){
                window.localStorage.setItem("userId", inputId);
                window.localStorage.setItem("userPwd",inputPwd);
                window.localStorage.setItem('autoId', inputId);
                window.localStorage.setItem('autoPwd', inputPwd);
                window.localStorage.setItem("isLogin", 'true');
                window.location.replace("/");
                console.log("로그인 성공");
            } else {
                setModalOpen(true);
                window.localStorage.setItem("isLogin", 'false');
            }
        } catch(e) {
            setModalOpen(true);
            console.log("로그인 에러..");
        }
    }
    const onKeyPress = e => {
        if(e.key === "Enter") {
            onClickLogin();
        }
    }

    const AutoLogin = e =>{
        if(e.target.checked){
            window.localStorage.setItem("autoLogin", "TRUE");
            console.log(window.localStorage.getItem('autoLogin'));
            setAutoLogin(!autoLogin);
            console.log(autoLogin);
        } else {
            window.localStorage.setItem("autoLogin", "FALSE");
            console.log(window.localStorage.getItem('autoLogin'));
            setAutoLogin(!autoLogin);
            console.log(autoLogin);
            setInputId('');
            setInputPwd('');
        }
    }
    return(
    <LoginBlock>
    <div className="page">
        <div className='titleWrap'><Link to ="/"><img src="/images/Logo.png" alt="Logo"/></Link>LOGIN</div>
            <div className="loginWrap">
                
                {window.localStorage.getItem('autoLogin') === "TRUE" ?
                <>
                <div className="inputTitle">아이디</div>
                <div className="inputWrap">
                    <input className="input" placeholder="아이디*" type="text" value={inputId} onChange={onChangeId}/>
                </div>
                </>
                :
                <>
                <div className="inputTitle">아이디</div>
                <div className="inputWrap">
                    <input className="input" placeholder="아이디*" type="text" value={inputId} onChange={onChangeId}/>
                </div>
                <div className="error">{!isId && inputId.length >0 &&('영문자 또는 숫자 6~20자')}</div>
                </>
                }
                
                {window.localStorage.getItem('autoLogin') === "TRUE" ?
                <>
                <div className="inputTitle">비밀번호</div>
                <div className="inputWrap">
                    <input className="input" placeholder="패스워드*" type="password" value={inputPwd} onChange={onChangePwd} onKeyPress={onKeyPress}/>
                </div>
                </>
                :
                <>
                <div className="inputTitle">비밀번호</div>
                <div className="inputWrap">
                    <input className="input" placeholder="패스워드*" type="password" value={inputPwd} onChange={onChangePwd} onKeyPress={onKeyPress}/>
                </div>
                <div className="error">{!isPwd && inputPwd.length >0 &&('영문자 포함 숫자 8~16자')}</div>
                </>
                }

                {/* 체크 되는 것 확인. */}
                {window.localStorage.getItem('autoLogin') === "TRUE" ?
                <div className="auto"><input type="checkbox" checked onClick={AutoLogin} id="remember"/><label for="remember">Remember me</label></div>
                : <div className="auto"><input type="checkbox" onClick={AutoLogin} id="remember"/><label for="remember">Remember me</label></div>}
                
                {window.localStorage.getItem('autoLogin') === "TRUE" ?
                <div className="item"><button type="submit" className="loginButton" onClick={onClickLogin} >확인</button></div>
                :
                <div className="item"><button type="submit" checked={false} className="loginButton" disabled={submit} onClick={onClickLogin} >확인</button></div>
                }
                    <Modal open={modalOpen} close={closeModal} header="오류">아이디 및 패스워드를 재확인해 주세요.</Modal>
                <div className="find">
                    <span className="findId" onClick={()=>{navigate('/Login/FindId')}}>아이디 찾기</span>
                    <span className="findPwd" onClick={()=>{navigate('/Login/FindPwd')}}>비밀번호 찾기</span>
                </div>
                <hr/>
                <div className="item"><button type="submit" className="goButton" onClick={()=>{navigate('/Login/SignUp')}}>회원가입</button></div>
                </div>
            </div> 
        </LoginBlock>
    );
}
export default LoginPage;