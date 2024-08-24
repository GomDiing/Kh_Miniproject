import axios from "axios";
const HEADER = 'application/json';
const MOVIE_DOMAIN="http://cokebear756.synology.me:62322/api";

const MovieApi={
    // 로그인 api
    userLogin : async function(inputId, inputPwd){
        const loginObj = {
            id : inputId,
            password : inputPwd
        }
        return await axios.post(MOVIE_DOMAIN + "/member/signin", loginObj, HEADER)
    },
    // 회원가입 api
    memberReg : async function(inputId, inputPwd, inputName ,inputEmail){
        const memberObj = {
            id : inputId,
            password : inputPwd,
            name : inputName,
            email : inputEmail
        }
        return await axios.post(MOVIE_DOMAIN + "/member/signup", memberObj, HEADER)
    },
     // 아이디 찾기 api
    findUser : async function(inputName,inputEmail){
        const finding ={
            name : inputName,
            email : inputEmail
        }
        return await axios.post(MOVIE_DOMAIN + "/member/searchId", finding, HEADER)
    },
    // 비밀번호 찾기 api
    findPassword : async function(inputId, inputEmail){
        const finding ={
            id : inputId,
            email : inputEmail
        }
        return await axios.post(MOVIE_DOMAIN + "/member/searchPassword", finding, HEADER)
    },
    // 비밀번호 수정 API
    setPwd : async function(id ,password) {
        const setPwd = {
            id : id,
            password : password
        }
        return await axios.post(MOVIE_DOMAIN + "/member/updatePassword", setPwd, HEADER);
    },
    // 회원탈퇴 API
    deleteUser : async function(inputId, inputEmail, inputPwd){
        const deleteUser = {
            id : inputId,
            email : inputEmail,
            password : inputPwd
        }
        return await axios.post(MOVIE_DOMAIN + "/member/delete", deleteUser,HEADER);
    },
    // 마이페이지 정보 조회 API
    memberSelect : async function(id) {
        const member = {
            id : id
        }
        return await axios.post(MOVIE_DOMAIN + "/member/mypage", member, HEADER);
    },
    // 문의 내역 전송 API
    qnaSend : async function(id, qna_title, qna_content) {
        const qna = {
            id : id,
            qna_title : qna_title,
            qna_content : qna_content
        }
        return await axios.post(MOVIE_DOMAIN + "/member/qna/send", qna, HEADER);
    },
    // 관리자페이지 문의 내역 API
    inquireInfo : async function() {
        return await axios.post(MOVIE_DOMAIN + "/member/qna/receive", HEADER);
    },
    // 관리자페이지 회원 리스트 API
    memberInfo : async function() {
        return await axios.post(MOVIE_DOMAIN + "/member/viewMembers", HEADER);
    }
}
export default MovieApi;