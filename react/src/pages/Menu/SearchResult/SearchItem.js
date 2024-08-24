import React from 'react'
import { Col } from 'antd';

// 영화 검색 결과 영화 포스터로 보여주기
function SearchItem(props) {
    return (
    <div style={{display: 'inline-flex'}}>
    <Col lg={6} md={8} xs={24}  style={{paddingBottom: '10px'}}>
        <div style={{ position: 'relative' }}>
            <a href={`/movie/${props.id}`} >
                <img style={{ width: '245px', height: '320px', border: '2px solid #FFD369'}} src={props.image} alt={props.movieName} />
            </a>
        </div>
    </Col>
    </div>
    );
}

export default SearchItem;