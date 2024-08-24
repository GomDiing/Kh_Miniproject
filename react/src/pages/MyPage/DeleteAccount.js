import styled from "styled-components";
import {useState} from "react";
import MovieApi from '../../api/MovieApi';
import Modal from '../../util/Modal';
import {useNavigate} from 'react-router-dom';

const DeleteBlock=styled.div`
.delete-container{
    position: absolute;
    top: 60px;
    bottom: 60px;
    width : 100%;
    height: 600px;
    padding: 65px 20px;
    max-width: 600px;
    left: 50%;
    transform: translate(-50%, 0);
    background-color: #393E46;
    color: #EEEEEE;
    overflow: hidden;
    text-align: center;
    display: flex;
    flex-direction: column;
}
.title{
    margin-bottom : 30px;
}
h2{
    margin-bottom: 20px;
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
.delButton{
    margin-top: 30px;
}
.revoke, .out{
    cursor: pointer;
    width: 180px;
    height: 50px;
    border-radius: 60px;
    margin: 0 25px;
    background-color: #FFD369;
    border: none;
    color:#222831;
    font-weight: bold;
}
hr{
    border: 2px solid #EEEEEE;
    height: 4px;
    margin: 35px 0;
}
`;
const DeleteAccount=()=>{

    // 아이디, 비번, 이메일
    const [inputId, setInputId] = useState("");
    const [inputPwd, setInputPwd] = useState("");
    const [inputEmail, setInputEmail] = useState("");
    
    // 정상 삭제시 모달
    const [deleteModal,setDeleteModal] = useState(false);
    let navigate = useNavigate();
    // 삭제 실패 모달
    // const [closefalsedeletModal,setclosefalsedeletModal] = useState(false);
    // 오류시 모달창
    const [modalOpen, setModalOpen] = useState(false);
    // 모달 header/text 입력
    const [modalHeader, setModalHeader] = useState(false);
    const [modalText, setModalText] = useState(false);

    // 삭제 성공시 모달창 이후 로그인 페이지로 이동
    const closedeletModal = () =>{
        setDeleteModal(true);
        window.localStorage.setItem("userId", '');
        window.localStorage.getItem("userPwd", '');
        window.location.replace('/Login/LoginPage');
    }
    const closeModal = () => {
        setModalOpen(false);
    };
    // 아이디/비밀번호/이메일 변경시 값 입력
    const onChangeId=(e)=>{
        setInputId(e.target.value);
    }
    const onChangePwd=(e)=>{
        setInputPwd(e.target.value);
    }
    const onChangeEmail=(e)=>{
        setInputEmail(e.target.value);
    }
    // 로그인된 값이랑 input 값 일치하는지 검사
    const loginCheck=()=>{
        const userId = window.localStorage.getItem("userId");
        const userPwd = window.localStorage.getItem("userPwd");
        if(inputId === userId && inputPwd === userPwd){
            console.log("로그인 계정이랑 일치");
        }else{
            setModalOpen(true);
            setModalHeader("오류");
            setModalText("일치하는 회원정보가 없습니다.");
        }
    };
    const onClickDelete = async()=> {
        if(loginCheck) {
            try{
                const deleteUser = await MovieApi.deleteUser(inputId,inputEmail,inputPwd);
                if(deleteUser.data.statusCode === 200) {
                    console.log("회원 정보가 탈퇴되었습니다.");
                    setDeleteModal(true);
                    const isAuto = window.localStorage.getItem("autoLogin");
                    console.log(isAuto);
                    window.localStorage.setItem("userId", '');
                    window.localStorage.setItem("userPwd",'');
                    window.localStorage.setItem("autoLogin",'');
                    window.localStorage.setItem("isLogin",'false');
                }}
                catch(e){
                    setModalOpen(true);
                    setModalHeader("오류");
                    setModalText("일치하는 회원정보가 없습니다.");
                }
            }
    };

    return(
        <DeleteBlock>
            <div className='delete-container'>
            <div className="title">
            <h2>회원 탈퇴 안내</h2>
            <div>회원님의 정보보호를 위해 비밀번호를 다시한번 입력해주시기 바랍니다.</div>
            </div>
            <div className="inputWrap"><input className="input" placeholder="아이디를 입력하세요*" type="text" value={inputId} onChange={onChangeId}/></div>
            <div className="inputWrap"><input className="input" placeholder="이메일을 입력하세요*" type="email" value={inputEmail} onChange={onChangeEmail}/></div>
            <div className="inputWrap"><input className="input" placeholder="비밀번호를 입력하세요*" type="password" value={inputPwd} onChange={onChangePwd} onBlur={loginCheck}/></div>
            <div className="delButton"><button className="out" onClick={onClickDelete}>탈퇴하기</button>
            <button className="revoke" onClick={()=>{navigate('/Mypage/Mypage')}} >취소하기</button></div>
            <Modal open={modalOpen} close={closeModal} header={modalHeader}>{modalText}</Modal>
            <Modal open={deleteModal} close={closedeletModal} header="회원탈퇴 완료">이용해 주셔서 감사합니다. 다음에 또 만나요.</Modal>
            <hr/>
            <div>회원탈퇴시 모든 정보가 삭제되오니<br/> 신중하게 탈퇴 신청을 해주시기 바랍니다.</div>
            </div>
        </DeleteBlock>
    );
}
export default DeleteAccount;