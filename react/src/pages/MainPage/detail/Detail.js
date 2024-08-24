import { Button, Row } from 'antd';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import GridCards2 from '../../Cards/GridCards2';
import DetInfo from './DetInfo';
import DetailImage from './DetailImage';
import Reviews from './Reviews';
import Menu from '../../Menu/Menu';
import Genres from './Genres';

// 포스터 클릭시 보이는 영화 상세 페이지
function Detail(props) {

    // 리액트에서 제공하는 Hook으로 동적으로 라우팅을 생성하기 위해 사용
    const {movieId} = useParams();

    // 영화 정보
    const [Movie, setMovie] = useState([]);

    // 배우 정보
    const [Cast, setCast] = useState([]);

    // 배우 보기 토글
    const [CastToggle, setCastToggle] = useState(false);

    // 리뷰 목록
    const [ReviewLists, setReviewLists] = useState([]);
    
    // 장르
    const [Genre, setGenre] = useState([]);

    // 영화 상세정보/배우/리뷰
    useEffect(() => {
        let contentInfo = `http://cokebear756.synology.me:62322/api/movie/${movieId}`;

        // 영화 상세 정보 받아오기 및 처리
        fetch(contentInfo, {
            // fetch() 함수에서 default method는 GET
            // POST인 경우에는 fetch() 함수에 method 정보를 인자로 넘겨줌
            method: "POST",
            // body는 JSON형태로 보내기 위해 JSON.stringfy() 함수에 객체를 인자로 전달하여 JSON형태로 변환
            body: JSON.stringify(Movie, Cast, ReviewLists)
        })
            // json 데이터 형식으로 변환
            // json() - body에 있는 정보들만 꺼내 바꿔줌
            .then(response => response.json())
            .then(response => {
            // 영화 정보 넣기
            setMovie(response.results)
            // 배우 정보 넣기
            setCast(response.results.cast)
            // 리뷰 정보 넣기
            setReviewLists(response.results.review)
            // 장르 정보 넣기
            setGenre(response.results.genre)
        })
        }, []);
        // 배우 정보를 토글로 가렸다 보여주기
        const toggleCastView = () => {
            setCastToggle(!CastToggle)
        }
        // 리뷰 목록 업데이트
        const updateReview = (newReview) => {
            setReviewLists(ReviewLists.concat(newReview))
        };

    return (
        <div>
        <Menu/>
        <div style={{width: '100%', backgroundColor: 'black'}}>
            {/* 상세페이지 헤더영역 - 가로포스터 */}
            <div style={{width: '1280px', height: '700px', margin: '0.1rem auto'}}>
                <DetailImage image={`${Movie.backdrop_path}`} title={Movie.title}/>
            </div>

            {/* 상세 페이지 바디 */}
            <div style={{ width: '85%', margin: '1rem auto'}}>
                {/* 받아온 정보 DetInfo로 넘김 */}
                <DetInfo movie={Movie}/>
                {/* 장르 */}
                {Genre && Genre.map((genre, index) => (
                    <React.Fragment key={index}>
                        <Genres genre={genre}/>
                    </React.Fragment>
                ))}

            {/* 영화 배우 정보 */}
            <div style={{ display: 'flex', justifyContent: 'center', margin: '2rem'}}>
                <Button style={{backgroundColor: '#FFD669', color: 'black'}} onClick={toggleCastView}>출연 배우 보기</Button>
            </div>
            {CastToggle &&
                <Row gutter={[16, 16]}>
                    {Cast && Cast.map((cast, index) =>(
                        <React.Fragment key={index}>
                            <GridCards2 image={cast.profile_path ? `${cast.profile_path}` : null} characterName={`${cast.name}`} character={`${cast.character}`}/>
                        </React.Fragment>
                    ))}
                </Row>
            }
            <br/>
            
            {/* 리뷰 */}
            <div>
                <Reviews movieTitle={Movie.title} ReviewLists={ReviewLists} movId={movieId} refreshFunction={updateReview} />
            </div>
            </div>
            </div>
        </div>
    );
}

export default Detail;