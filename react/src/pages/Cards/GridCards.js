import React, { useEffect, useState } from 'react';
import { Col } from 'antd';
import NowLoading from '../../util/Loading';

// 메인 페이지 카테고리별 영화 포스터용 그리드카드
function GridCards(props) {
    // 로딩중
    const [Loading, setLoading] = useState(true);
    useEffect(() => { setLoading(false) }, []);

    return (
    <div style={{display: 'inline-flex'}}>
    <Col style={{paddingBottom: '10px'}}>
        <div style={{ position: 'relative' }}>
            <a href={`/movie/${props.id}`} >
            {/* 렌더링 전 로딩중일시 보이게 */}
            {Loading && <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}><NowLoading/></div>}
            {/* MainPage에서 props로 받아와서 포스터 출력 */}
            <img style={{ width: '245px', height: '320px', border: '2px solid #FFD369'}} src={props.image} alt={props.movieName} />
            </a>
        </div>
    </Col>
    </div>
    );
}

export default GridCards;
