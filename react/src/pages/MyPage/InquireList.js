import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import MovieApi from "../../api/MovieApi";
import Modal from "../../util/Modal";
import Main from "../Main";

const StyleList = styled.div `
  margin:0 auto;
  box-sizing: border-box;
  color: white;
  
  .container {
    margin-top: 3rem;
    display: flex;
    border: 5px solid #eeeeee;
    width: 50%;
    height: 100%;
    flex-direction: column;
    text-align: center;
    padding: 3rem;
  }
  .var {
    font-size: 2.5em;
    font-weight: bold;
    color: #ffd369;
    pointer-events: none;
    text-decoration: none;
    display: flex;
    justify-content: center;
}
.nav {
    text-decoration: none;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 90px;
    color: #ffd369;
}
.nav-link {
  font-size: 1.35em;
    color: white;
    color: #ffd369;
}
table,th, td {
  border: 1px solid #ffd369;
}
`;

const InquireList = () => {

  // 모달
  const [modalOpen, setModalOpen] = useState(false);
  // 모달 내용
  const [modalText, setModalText] = useState('');
  // 문의 내용을 가져와서 담기 위한 변수
  const [inquireInfo, setInquireInfo] = useState('');

const closeModal = () => {
  setModalOpen(false);
}

  /** 문의 내용을 가져오는 useEffect */
  useEffect(() => {
    const inquireData = async () => {
      try {
        const response = await MovieApi.inquireInfo(); // 전체 리스트 조회
        setInquireInfo(response.data.results);
      } catch (e) {
        console.log(e);
      }
    };
    inquireData();
  }, []);
  
  // 비로그인 접근 금지
  const userId = window.localStorage.getItem("userId");
  if(userId !== 'admin123') {
    return(
      <Main />
    );
  }

    return (
        <StyleList>
          <div>
            <nav className="nav nav-pills nav-justified">
              <Link className="nav-link" style={{fontSize: '25px'}} to="/">홈</Link>
              <Link className="nav-link" aria-current="page" to="/MyPage/Mypage"mypage aria-disabled>관리자 페이지</Link>
            </nav>
            <Link className="var" aria-current="page" to="./"mypage aria-disabled>문의 내역</Link>
            <div className="container">
              <table>
                <thead>
                  <tr>
                    <th>회원 아이디</th>
                    <th>문의 제목</th>
                    <th>문의 날짜</th>
                  </tr>
                </thead>
                {/* 데이터가 배열 형태로 들어와서 map 함수를 써서 하나씩 까기 key 값은 고유한 문의 아이디(글번호?)를 집어넣음*/}
                {inquireInfo && inquireInfo.map(info => (
                  <tbody key={info.id}>
                    <tr>
                      <td>{info.member_id}</td>
                      {/* 처음에는 onClick 함수를 따로 만들어서 썼는데 계속 클릭 시 마지막 문의 내용만 보여서 중간에 바로 setModalText에 값을 넣어주니까 잘 됐음.. 이거 좀 힘들었어요 */}
                      <td onClick={() => {setModalText(info);setModalOpen(true);}}>{info.qna_title}</td>
                      <td>{info.create_time}</td>
                    </tr>
                  </tbody>
                ))}
              </table>
                <Modal open={modalOpen} close={closeModal} header="문의 내용"><div style={{color: 'white'}}>{modalText.qna_content}</div></Modal>
            </div>
          </div>
        </StyleList>
    );
}

export default InquireList;