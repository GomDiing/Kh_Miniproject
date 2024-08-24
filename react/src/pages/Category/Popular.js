import { Row } from 'antd';
import React, { useEffect, useState } from 'react';
import NowLoading from '../../util/Loading';
import NoImage from '../../util/NoImage';
import GridCards3 from '../Cards/GridCard3';
import Menu from '../Menu/Menu';

// 인기 영화
function Popular() {
    // DB에서 영화 데이터 받아올 배열
    const [Movies, setMovies] = useState([]);
    // 더보기 위한 페이지 세팅
    const [CurrentPage, setCurrentPage] = useState(0);
    // 로딩중
    const [Loading, setLoading] = useState(true);
    
    // 렌더링시 DB에서 영화 정보 받아오는 작업
    useEffect(() => {
        const popular = `http://cokebear756.synology.me:62322/api/movie/popular?page=0`;
        FetchMovies(popular)
    }, []);

    // 정보 받아오기 및 처리
    const FetchMovies = (popular) => {
        // 로딩 보여주기위해 true
        setLoading(true)
        fetch(popular, {
            // fetch() 함수에서 default method는 GET
            // POST인 경우에는 fetch() 함수에 method 정보를 인자로 넘겨줌
            method : "POST",
            // body는 JSON형태로 보내기 위해 JSON.stringfy() 함수에 객체를 인자로 전달하여 JSON형태로 변환
            body: JSON.stringify(Movies)
        })
                // json 데이터 형식으로 변환
        // json() - body에 있는 정보들만 꺼내 바꿔줌
        .then(response => response.json())
        .then(response => {
        // ... spread operator 배열 복제 (ES6 추가)
        // DB 배열에 있는 정보 Movies에 넣기
        setMovies([...Movies,...response.results.contents])
        setCurrentPage(response.results.page)
        }, setLoading(false))
    };

    // 더보기
    const loadMore = () => {
        const popular = `http://cokebear756.synology.me:62322/api/movie/popular?page=${CurrentPage +1}`;
        FetchMovies(popular)
    };

    return (
        <div style={{width: '100%', margin: '0',  backgroundColor: 'black'}}>
        <Menu/>
        <br/>
        {/* 카테고리 부분 */}
        <div style={{width: '85%', margin: '1rem auto'}}>
            <h2 style={{color: '#FFD369'}}>인기 영화</h2>
            <hr/>
            {/* 렌더링 전 로딩중일시 보이게 */}
            {Loading && <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}><NowLoading/></div>}
            <Row gutter={[16, 16]}>
            {/* DB에서 받아온 데이터 map에 넣어서 출력 */}
            {Movies && Movies.map((movie, index) => (
                <React.Fragment key={index}>
                <GridCards3 id={movie.movie_id} image={movie.poster_path ? `${movie.poster_path}` : NoImage}/>
                </React.Fragment>
            ))}
            </Row>
        {/* 더보기 */}
        <div style={{display: 'flex', justifyContent: 'center' }}>
            <button style={{backgroundColor: '#FFD369', color: 'black'}} onClick={loadMore}>더 보기</button>
        </div>
        </div>
        </div>
        );
}

export default Popular;
