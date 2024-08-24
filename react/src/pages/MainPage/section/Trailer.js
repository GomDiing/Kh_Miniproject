import React, { useEffect, useState } from 'react';
import Carousel from 'react-bootstrap/Carousel';
import ReactPlayer from 'react-player/youtube';
import styled from 'styled-components';

const Trail = styled.div`
.carousel-indicators {
    visibility: hidden;
}
.carousel-control-prev,
.carousel-control-next {
    margin-top: 10em;
    height: 26em;
}
`;
// 예고편
function Trailer() {
    const [Trailer, setTrailer] = useState('');
    const [Trailer2, setTrailer2] = useState('');
    const [Trailer3, setTrailer3] = useState('');
    const [Trailer4, setTrailer4] = useState('');

    // 렌더링시 DB에서 예고편 youtube url 받아오는 작업
    useEffect(() => {
        const nowTrailer = `http://cokebear756.synology.me:62322/api/movie/436270`;
        FetchTrailer(nowTrailer)
    }, []);
    useEffect(() => {
        const nowTrailer2 = `http://cokebear756.synology.me:62322/api/movie/238`;
        FetchTrailer2(nowTrailer2)
    }, []);
    useEffect(() => {
        const nowTrailer3 = `http://cokebear756.synology.me:62322/api/movie/505642`;
        FetchTrailer3(nowTrailer3)
    }, []);
    useEffect(() => {
        const nowTrailer4 = `http://cokebear756.synology.me:62322/api/movie/663712`;
        FetchTrailer4(nowTrailer4)
    }, []);

    // 정보 받아오기 및 처리
    const FetchTrailer = (nowTrailer) => {
        fetch(nowTrailer, {
            // fetch() 함수에서 default method는 GET
            // POST인 경우에는 fetch() 함수에 method 정보를 인자로 넘겨줌
            method : "POST",
            // body는 JSON형태로 보내기 위해 JSON.stringfy() 함수에 객체를 인자로 전달하여 JSON형태로 변환
            body: JSON.stringify(Trailer)
        })
        // json 데이터 형식으로 변환
        // json() - body에 있는 정보들만 꺼내 바꿔줌
        .then(res => res.json())
        .then(res => {
        // url list에 있는것 중 첫번째 값 넣기
        setTrailer(res.results.youtube_url_list[0]) 
        })
    };
    const FetchTrailer2 = (nowTrailer2) => {
        fetch(nowTrailer2, {
            method : "POST",
            body: JSON.stringify(Trailer2)
        })
        .then(res => res.json())
        .then(res => {
        setTrailer2(res.results.youtube_url_list[0]) 
        })
    };
    const FetchTrailer3 = (nowTrailer3) => {
        fetch(nowTrailer3, {
            method : "POST",
            body: JSON.stringify(Trailer3)
        })
        .then(res => res.json())
        .then(res => {
        setTrailer3(res.results.youtube_url_list[0]) 
        })
    };
    const FetchTrailer4 = (nowTrailer4) => {
        fetch(nowTrailer4, {
            method : "POST",
            body: JSON.stringify(Trailer4)
        })
        .then(res => res.json())
        .then(res => {
        setTrailer4(res.results.youtube_url_list[0]) 
        })
    };

    return (
    // youtube 정책에 따라 재생 되지 않는 동영상 존재
    // 리액트 플에이어로 youtube 영상 출력
    <Trail>
    <Carousel style={{width: '80%', margin: '5px auto', borderRadius: '10px', backgroundColor: '#FFD369', padding: '30px', boxSizing: 'borderBox', height: '100%'}}>
        <Carousel.Item style={{position: 'relative', width: '100%', height: '100%'}}>
            <ReactPlayer className="d-block w-100" controls={true} width="800px" height="685px" url={Trailer.youtube_url} alt=""/>
        </Carousel.Item>
        <Carousel.Item style={{position: 'relative', width: '100%', height: '100%'}}>
            <ReactPlayer className="d-block w-100" controls={true} width="800px" height="685px" url={Trailer2.youtube_url} alt=""/>
        </Carousel.Item>
        <Carousel.Item style={{position: 'relative', width: '100%', height: '100%'}}>
            <ReactPlayer className="d-block w-100" controls={true} width="800px" height="685px" url={Trailer3.youtube_url} alt=""/>
        </Carousel.Item>
        <Carousel.Item style={{position: 'relative', width: '100%', height: '100%'}}>
            <ReactPlayer className="d-block w-100" controls={true} width="800px" height="685px" url={Trailer4.youtube_url} alt=""/>
        </Carousel.Item>
    </Carousel>
    </Trail>
    );
}

export default Trailer;