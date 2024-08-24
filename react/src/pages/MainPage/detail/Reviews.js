import axios from 'axios';
import React, { useState } from 'react'
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import styled from "styled-components";
import { Button } from 'antd';
import RComment from './Comment';
import Modal from '../../../util/Modal';

const PostBlock=styled.div`
color: white;
.under {
    margin-top: 10px;
    border-bottom: 2px solid silver;
}
`;

function Reviews(props) {
    // 리뷰
    const [Review, setReview] = useState("");

    // 리뷰 저장시 값 넣어주기
    const handleChange = (e) => {
        setReview(e.target.value);
    }
    // 로그인된 사용자 ID 받아오기
    const idc = window.localStorage.getItem("userId");

    // 리뷰 저장
    const onSubmit = async (e) => {
        e.preventDefault();
        const rev = {
            id : idc,
            movie_id : props.movId,
            review : Review
        }
        try {
            const response = await axios.post('http://cokebear756.synology.me:62322/api/member/review', rev)
            if(response.data.statusCode === 200) {
            setReview("")
            props.refreshFunction(response.data.result)
            window.location.replace('/movie/'+rev.movie_id);
            }
        } catch (e) {
    setModalOpen(true);
    }}

    // 모달 창
    const [modalOpen, setModalOpen] = useState(false);

    // 모달 닫기
    const closeModal = () => {
        setModalOpen(false);
    };

    return (
        <PostBlock>
        <b style={{color: '#FFD369'}}>{props.movieTitle}에 대한 리뷰를 남겨 보세요</b>
        <p className='under'></p>
        
        {/* 리뷰가 존재하는 경우 보여주는 리스트 map에 담아서 출력 */}
        {props.ReviewLists && props.ReviewLists.map((comment, index) => (
            <React.Fragment key={index}>
                <RComment comment={comment} movId={props.movId} refreshFunction={props.refreshFunction}/>
            </React.Fragment>
        ))}

        {/* 리뷰가 없을 경우 보여주기 */}
        {props.ReviewLists && props.ReviewLists.length === 0 &&
            <div style={{color: '#FFD369', display: 'flex', justifyContent:'center', alignItems:'center', height:'200px'}} >
                첫 리뷰를 남겨보세요.
            </div>
        }
        <br/><br/>
        {/* 리뷰 작성 */}
        <div style={{marginLeft: '13rem', marginRight: '13rem'}}>
        <InputGroup>
        <Form.Control as="textarea" style={{ width: '60%', borderRadius: '5px', marginLeft: '5px', marginRight: '5px' }} onChange={handleChange} value={Review} placeholder="리뷰를 남겨 보세요."/>
            <Button style={{ backgroundColor: '#FFD369' ,width: '10%', fontWeight: 'bold'}} onClick={onSubmit}>저장</Button>
            <Modal open={modalOpen} close={closeModal} header="리뷰 저장 실패">로그인 후 리뷰 작성이 가능합니다.</Modal>
        </InputGroup>
        <br/>      
        </div>
        </PostBlock>
    );
}

export default Reviews;