import os
from flask import Flask, request, render_template
import requests
import time
import multiprocessing
import configparser
import apiTMDB
from apscheduler.schedulers.background import BackgroundScheduler

# 설정값 읽기
config = configparser.ConfigParser()
config.read('config.ini')


app = Flask(__name__)

# 영화 데이터 요청
def moviesRequest(movie_id):

    # 이미 DB 테이블에 있다면 생략
    if (apiTMDB.is_dupli_movie(movie_id) == True):
        print("movie_id( " + str(movie_id) + " ) is Working But Duplicated")
        return

    # 영화 API 요청 URL 설정
    requestURL_Front = config['DEFAULT']['TMDB_URL_APPEND_MoViIm_FRONT']
    requestURL_Back = config['DEFAULT']['TMDB_URL_APPEND_MoViIm_BACK']

    # 설정한 API URL에 요청 영화 설정
    requestURL = requestURL_Front + str(movie_id) + '?' + requestURL_Back
    print(requestURL)

    # 영화 데이터 요청
    response = requests.get(requestURL)
    time.sleep(0.2)

    # 응답 데이터의 상태 코드가 200이 아니면 생략
    if (response.status_code != 200):
        print('movie_id( ' + str(movie_id) + " ) is Pass : " + str(response.status_code))
        return

    # JSON으로 파싱하고 테이블에 추가
    responseJson = response.json()
    apiTMDB.insert_movies(responseJson)


# 영화 카테고리 데이터 요청 메서드
def mainRequest(page_count):
    print('mainRequest')
    # 영화 카테고리 테이블 이름
    table_now_playing = 'movies_now_playing'
    table_popular = 'movies_popular'
    table_top_rated = 'movies_top_rated'
    table_upcoming = 'movies_upcoming'

    # 영화 카테고리별 API 요청 URL 설정
    request_NOW_PLAYING_FRONT = config['DEFAULT']['TMDB_URL_NOW_PLAYING_FRONT']
    request_NOW_PLAYING_BACK = config['DEFAULT']['TMDB_URL_REGION']

    request_POPULAR_FRONT = config['DEFAULT']['TMDB_URL_POPULAR_FRONT']
    request_POPULAR_BACK = config['DEFAULT']['TMDB_URL_REGION']

    request_TOP_RATED_FRONT = config['DEFAULT']['TMDB_URL_TOP_RATED_FRONT']
    request_TOP_RATED_BACK = config['DEFAULT']['TMDB_URL_REGION']

    request_UPCOMING_FRONT = config['DEFAULT']['TMDB_URL_UPCOMING_FRONT']
    request_UPCOMING_BACK = config['DEFAULT']['TMDB_URL_REGION']

    # 설정한 API URL에 요청 영화 페이지 숫자 설정, 1페이지 당 20개의 영화 데이터가 존재
    request_NOW_PLAYING = request_NOW_PLAYING_FRONT + str(page_count) + request_NOW_PLAYING_BACK
    request_POPULAR = request_POPULAR_FRONT + str(page_count) + request_POPULAR_BACK
    request_TOP_RATED = request_TOP_RATED_FRONT + str(page_count) + request_TOP_RATED_BACK
    request_UPCOMING = request_UPCOMING_FRONT + str(page_count) + request_UPCOMING_BACK

    # 영화 데이터 요청 후 JSON 방식으로 응답받은 데이터를 파싱
    responseJSON_NOW_PLAYING = requests.get(request_NOW_PLAYING).json()
    responseJSON_POPULAR = requests.get(request_POPULAR).json()
    responseJSON_TOP_RATED = requests.get(request_TOP_RATED).json()
    responseJSON_UPCOMING = requests.get(request_UPCOMING).json()

    # 파싱한 JSON을 테이블에 추가
    apiTMDB.update_main_pages_table(responseJSON_NOW_PLAYING, table_now_playing)
    apiTMDB.update_main_pages_table(responseJSON_POPULAR, table_popular)
    apiTMDB.update_main_pages_table(responseJSON_TOP_RATED, table_top_rated)
    apiTMDB.update_main_pages_table(responseJSON_UPCOMING, table_upcoming)


# 영화 카테고리 갱신 요청 경로
@app.route('/main')
def main():
    print('main')
    # 멀티프로세스로 동작
    p = multiprocessing.Pool(2)
    p.map(mainRequest, range(1, 6))

    return "complete_mainRequest"


# 디버그용 메세지
def message():
    print('message')


# 백그라운드 스케쥴러, 3일마다 main 메서드 실행
schedule = BackgroundScheduler(daemon=True, timezone='Asia/Seoul')
schedule.add_job(main, 'interval', days=3)
schedule.start()


# 영화 데이터 요청 경로
@app.route('/api')
def api():
    # URL 파라미터로 요청할 영화 숫자 개수를 입력할 수 있도록 설정
    # 디폴트 값은 0 ~ 10번 영화 데이터 요청
    startAPI = request.args.get('start', default=0, type=int)
    endAPI = request.args.get('end', default=10, type=int)
    # 멀티프로세스로 동작
    p = multiprocessing.Pool(4)
    p.map(moviesRequest, range(startAPI, endAPI))

    return "complete_movieRequest"


# 홈 경로
@app.route('/')
def hello():
    # index.html 표시
    return render_template('index.html')


# main 실행
if __name__ == '__main__':
    # app.run()
    app.run(host="0.0.0.0", port=int(os.environ.get("PORT", 5000)))