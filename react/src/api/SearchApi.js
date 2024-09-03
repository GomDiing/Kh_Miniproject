import axios from "axios";
const HEADER = 'application/json';
const MOVIE_DOMAIN="http://localhost:8080/api/movie/search?query=";

const SearchApi={
    movieSearch : async function(searchRes){
        const searchObj = {
            query : searchRes
        }
        return await axios.post(MOVIE_DOMAIN + searchRes , searchObj, HEADER)
    }};

export default SearchApi;