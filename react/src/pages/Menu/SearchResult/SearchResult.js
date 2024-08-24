import styled from "styled-components";
import React, { useState, useEffect } from "react";
import Menu from "../Menu";
import {Row} from 'antd';
import NowLoading from "../../../util/Loading";
import SearchApi from "../../../api/SearchApi";
import SearchItem from "./SearchItem";

const Container = styled.div`
.noResult{
    display: flex;
    justify-content: center;
    color : white ;
    margin-top: 200px;
    span{
        margin-top: 35px;
        }
    .noimg{
        display: inline-block;
        width: 100px;
        height: 100px;
        }
}
`;

    const SearchResult = () =>{
        const [Movies , setMovies] = useState('');
        const [isResult, setResult] = useState(false);
        const [loading, setLoading] = useState(false);
        
        useEffect(() => {
            const fetchData = async () =>{
                setLoading(true);
                try{
                    // getItem 으로 Menu 에서 검색한값(a)을 받아옴 검색값(a)을 searchRes 에 담음
                    const searchRes = window.localStorage.getItem("inputTxt");
                    // 서버에 검색한값(a) 를 전송함
                    // 전송해주면 출력해야할 값을 자바 로직으로 처리
                    const res = await SearchApi.movieSearch(searchRes);                   
                    // 전송결과가 성공이라면 , statusCode 값에 200을 받기로 약속함
                    console.log(res.data.statusCode);
                    if(res.data.statusCode === 200){
                        //setMovie 함수로 Movies 에 결과값을 담음 
                        setMovies(res.data.results);
                        // 조건부 랜더링을 위한 Result
                        setResult(true);
                        console.log(res.data.results.page);
                        console.log(res.data.message);
                        console.log(Movies);
                    }else{
                        setMovies('');
                        setResult(false);
                    }
                } catch(e){
                    console.log("실패");
                }
                console.log(Movies)
                setLoading(false);
            };
            fetchData();
        }, []);
        // 로딩중에 화면 출력
        if(loading){
            return <div style={{display: 'flex', justifyContent: 'center', color: 'FFD369'}}><NowLoading/><p style={{color: 'white'}} >Loading ...</p></div>
        }

        return(
            <>
            <Container>
                    <Menu/>
                    {/* 검색결과가 존재할때 */}
                    {isResult ?
                        <div style={{width: '85%', margin: '1rem auto'}}>
                            <h1 style={{color: '#FFD369'}}>검색결과</h1>
                            <hr/>
                            <Row gutter={[16,16]}>
                            {Movies && Movies.contents.map((contents, index) => (
                                <React.Fragment key={index}>
                                    <SearchItem  
                                    image ={contents.poster_path}
                                    id={contents.movie_id} 
                                    movieName={contents.title}
                                    >
                                    </SearchItem>
                                </React.Fragment>
                            ))};
                            </Row>
                        </div>
                        :
                    // 검색결과가 존재하지 않을경우
                    <div style={{width: '85%', margin: '1rem auto'}}>
                        <h1 style={{color: '#FFD369'}}>검색결과 없습니다.</h1>
                        <div class="noResult">
                            <img class="noimg" src="/images/no.jpg" alt="icon"/>
                            <span>요청하신 검색결과가 존재하지 않습니다.검색내용을 한번더 확인해 주시길 바랍니다.</span>
                        </div>
                    </div>
                    }
            </Container>
        {/* <Footer/> */}
    </>
        );
    }

export default SearchResult;