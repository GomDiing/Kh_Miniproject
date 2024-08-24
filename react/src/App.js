import './App.css';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import Main from './pages/Main';
import MainPage from './pages/MainPage/MainPage';
import Detail from './pages/MainPage/detail/Detail';

import LoginPage from './pages/Login/LoginPage';
import SignUp from './pages/Login/SignUp';
import FindPwd from './pages/Login/FindPwd';
import FindID from './pages/Login/FindID';

import Mypage from './pages/MyPage/Mypage';
import InfoSet from './pages/MyPage/InfoSet';
import Inquire from './pages/MyPage/Inquire';
import InquireList from './pages/MyPage/InquireList';
import DeleteAccount from './pages/MyPage/DeleteAccount';
import SearchResult from './pages/Menu/SearchResult/SearchResult';
import NowPlaying from './pages/Category/NowPlaying';
import TopRated from './pages/Category/TopRated';
import Popular from './pages/Category/Popular';
import UpComing from './pages/Category/UpComing';

function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Main/>}></Route>
        <Route path='/pages/MainPage/MainPage' element={<MainPage/>}/>
        <Route exact path="/movie/:movieId" element={<Detail/>}/>
        <Route path='/pages/Category/NowPlaying' element={<NowPlaying />} />
        <Route path='/pages/Category/TopRated' element={<TopRated />} />
        <Route path='/pages/Category/Popular' element={<Popular />} />
        <Route path='/pages/Category/UpComing' element={<UpComing />} />

        <Route path='/Login/LoginPage' element={<LoginPage/>}/>
        <Route path='/Login/SignUp' element={<SignUp/>}/>
        <Route path='/Login/FindPwd' element={<FindPwd/>}/>
        <Route path='/Login/FindID' element={<FindID/>}/>

        <Route path='/MyPage/Mypage' element={<Mypage />} />
        <Route path='/MyPage/InfoSet' element={<InfoSet />} />
        <Route path='/MyPage/Inquire' element={<Inquire />} />
        <Route path='/MyPage/InquireList' element={<InquireList />} />
        <Route path='/MyPage/DeleteAccount' element={<DeleteAccount/>}/>
        <Route path='/Menu/SearchResult/SearchResult' element={<SearchResult/>}/>

      </Routes>
    </Router>
  );
}
export default App;