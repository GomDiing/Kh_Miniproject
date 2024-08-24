import React from 'react';
import { Table } from 'react-bootstrap';

// 영화 정보 테이블
function DetInfo(props) {
    let {movie} = props;
    console.log(props);
    return (
        <div>
        <Table style={{color: '#FFD368'}}>
        <thead>
            <tr>
                <th colSpan={2} style={{textAlign: 'left', fontSize: 'larger'}}>영화 정보</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td style={{width: '25%'}}>제목</td>
                <td>{movie.title}</td>
            </tr>
            <tr>
                <td>개봉일</td>
                <td>{movie.release_date}</td>
            </tr>
            <tr>
                <td>상영시간</td>
                <td>{movie.runtime}분</td>
            </tr>
            <tr>
                <td>감독</td>
                <td>{movie.director}</td>
            </tr>
            <tr>
                <td>줄거리</td>
                <td>{movie.overview}</td>
            </tr>
            <tr>
                <td>평점</td>
                <td>{movie.vote_average}점</td>
            </tr>
        </tbody>
        </Table>
        </div>
    );
}

export default DetInfo;