import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const Container = styled.div`
@media screen and (max-width: 920px) {    
    .category-icon{
        display: none;
    }
    .search{
    }    
    .search .search-box{
        width: 85%;
        /* 검색창 */
    }
    .search .submit {
        display: none;
    }
    .dropdown-icon  img{
        /* 로그인 아이콘 */
    }
}
    width : 100%;
    background-color: black;
    color: white;
    a{
        text-decoration: none;
        color: white;
    }
    ul li{
        list-style: none;
    }
    .logo img{
        margin-left :20px;
        margin-top: 10px;
        width: 80px;
        height: 70px; // 로고 원본크기 유지만 수정했습니다.
    }      
    //검색창
    .search{
        display: inline-block;
        width: 61%; 
        margin-top: 5px;
    }
    .search option{
        position : absolute;
        z-index : 1; /*다른 요소들보다 앞에 배치*/
        font-weight: 400;
    }    
    .search-box{
        padding: 10px;
        border-radius: 15px;
        border:1px solid #FFD369;
        width: 80%;
        height: 50px;
        font-size: 15px;
        margin-right: 10px;
    }
    // 검색 버튼
    .submit {
        background-color: black;
        border: 2px solid #FFD369;
        border-radius: 0.6em;
        color: #FFD369;
        cursor: pointer;
        font-size: 1rem;
        font-weight: 400;
        width: 15%;
        min-width: 100px;
        height: 50px;
        text-align: center;
        text-transform: uppercase;
        font-weight: 700;
        &:active {
            // 눌렀을때 색 변경
            background-color: #FFD369;
            color: black;
            outline: 0;
        }
        &:hover {
        box-shadow: 1px 1px 0 rgb(0,0,0,0.5);
        position: relative;
        }
    }
    // 아이콘 드롭다운
    .dropdown-icon{
        margin-right: 30px;
        list-style : none; 
        margin-top: 20px;
        cursor : pointer;
    }
    .dropdown-content-icon{
        display : none;
        position : absolute;
        z-index : 1; /*다른 요소들보다 앞에 배치*/
        font-weight: 400;
        background-color: black;
        color: white;
        width: 115px;
        padding: 15px;
        margin-left: -57px;
    }
    .dropdown-content-icon li{
        color : white;
        font-size: 18px;
        padding : 10px 5px;
    }
    .dropdown-content-icon :hover{
        color: #FFD369;
    }
    .dropdown-icon:hover .dropdown-content-icon  {
        display: block;
    }
    .dropbtn-icon img {
        height:40px;
        width:35px;
    }
    .category-icon{
        color: #FFD369;
        font-size: 25px;
        list-style : none; 
        margin-top: 12px;
        cursor : pointer;
    }
    .category-content-icon{
        display : none;
        position : absolute;
        z-index : 1; /*다른 요소들보다 앞에 배치*/
        font-weight: 400;
        background-color: black;
        color: white;
        width: 200px;
        padding: 15px;
        margin-left: -44px;
    }
    .category-content-icon li{
        color : white;
        font-size: 18px;
        padding : 10px 5px;
    }
    .category-content-icon :hover{
        color: #FFD369;
    }
    .category-icon:hover .category-content-icon {
        display: block;
    }
`;

const Menu = () => {
    const [isLogin,setIsLogin] = useState(false);
    const [inputTxt,setInputTxt] = useState('');

    // 로그인 상태일떄, icon 화면에 조건부 랜더링 로직
    const isLoginStorage = window.localStorage.getItem("isLogin"); 
    useEffect(() => {
        if(isLoginStorage === "true"){
            setIsLogin(true);
        }
    }, [isLoginStorage]);    
    
    // 검색창에 입력값이 들어올때 value 에 값을 담음
    const onChangeTxt = (e) =>{
        const txt = e.target.value;
        setInputTxt(txt);
        console.log(txt);
    }
    
    // 검색버튼 클릭시 검색한 값을 localstorage 에 저장
    const onClickSearch = () =>{
        window.localStorage.setItem("inputTxt", inputTxt);
        if(inputTxt.length >= 2){
            window.location.replace("/Menu/SearchResult/SearchResult");
        } else {
            alert("2글자 이상 입력해 주세요.")
        }
    }
    // 엔터버튼으로 검색 기능
    const onKeyPress = e => {
        if(e.key === "Enter") {
            onClickSearch();
        }
    };

    // 로그아웃
    const onClickLogout = () =>{
        window.localStorage.setItem("userId", '');
        window.localStorage.setItem("userPwd",'');
        window.localStorage.setItem("isLogin", "false")
        window.location.replace("/");
    }
    
    return (
        <Container>
            <ul className="navbar">
                <li className="logo"><Link to ="/"><img src="/images/Logo.png" alt="Logo"/></Link></li>

                <li className="category-icon">
                    <p className="category-icon">영화</p>
                    <ul className="category-content-icon">
                        <li><Link to ="/pages/Category/NowPlaying">최신 영화</Link> </li>
                        <li><Link to ="/pages/Category/Popular">인기 영화</Link></li>
                        <li><Link to ="/pages/Category/TopRated">최고 평점 영화</Link></li>
                        <li><Link to ="/pages/Category/UpComing">개봉 예정작</Link></li>
                    </ul>
                </li>

                <li className="search" >
                        <input className="search-box" type="text" value={inputTxt} onKeyPress={onKeyPress} onChange={onChangeTxt} placeholder="영화 제목을 입력하세요."/>
                        <button className="submit" onClick={onClickSearch}>search</button>

                </li>

                {isLogin ?
                <li className="dropdown-icon">
                    <p className="dropbtn-icon"><img src="/images/mem.png" alt="icon"/></p>
                    <ul className="dropdown-content-icon">
                        <li><Link to ="/MyPage/Mypage">My Page</Link></li>
                        <li onClick={onClickLogout}>로그아웃</li>
                        <li><Link to ="/MyPage/Inquire">고객센터</Link></li>
                    </ul>
                </li>
                :
                <li className="dropdown-icon">
                    <p className="dropbtn-icon"><Link to="/Login/LoginPage"><img src="/images/mem.png" alt="icon"/></Link></p>
                </li>
            }
            </ul>
        </Container>
    );
}

export default Menu;