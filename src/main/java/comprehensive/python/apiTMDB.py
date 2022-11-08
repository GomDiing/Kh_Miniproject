import configparser

import sqlalchemy
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import MetaData, Table, insert, select
from sqlalchemy.orm import sessionmaker
from sqlalchemy.exc import IntegrityError
import apiMain

# 설정값 읽기
config = configparser.ConfigParser()
config.read('config.ini')

SQLALCHEMY_DB_URL = config['DEFAULT']['SQLALCHEMY_DB_URL']

# DB와 연결
engine = sqlalchemy.create_engine(SQLALCHEMY_DB_URL, echo=False)

# Session 생성
metadata_obj = MetaData(bind=engine)
SessionLocal = sessionmaker(bind=engine)
db = SessionLocal()

Base = declarative_base()


# DB연결 확인
def create_tb(engine):
    Base.metadata.create_all(engine)
    print('테이블 생성 성공')


# 배우(cast), 스태프(crew) 각각 테이블 데이터 추가 메서드
def insert_credit(response):
    # 테이블 연결
    t_credit_crew = Table('credit_crew', metadata_obj, autoload_with=engine)
    t_credit_cast = Table('credit_cast', metadata_obj, autoload_with=engine)

    # 응답에서 데이터 추출
    movie_id = response['id']
    cast_list = response['credits']['cast']
    crew_list = response['credits']['crew']

    # 리스트 순회하며 배우(cast) 데이터 추가하고 Commit
    for cast in cast_list:
        cast_id = cast['id']
        credit_id = cast['credit_id']
        character = cast['character']
        known_for_department = cast['known_for_department']
        cast_order_id = cast['cast_id']
        order = cast['order']
        stmt = insert(t_credit_cast).values(movie_id=movie_id, cast_id=cast_id,
                                            credit_id=credit_id, character=character,
                                            known_for_department=known_for_department,
                                            cast_order_id=cast_order_id, order=order)
        commit_db(stmt)

    # 리스트 순회하며 스태프(crew) 데이터 추가하고 Commit
    for crew in crew_list:
        crew_id = crew['id']
        credit_id = crew['credit_id']
        department = crew['department']
        job = crew['job']
        stmt = insert(t_credit_crew).values(movie_id=movie_id, crew_id=crew_id,
                                            credit_id=credit_id, department=department,
                                            job=job)
        commit_db(stmt)


# 배우와 스태프(cast_and_crew) 테이블 데이터 추가 메서드
def insert_cast_and_crew(response):
    # 테이블 연결
    t_cast_and_crew = Table("cast_and_crew", metadata_obj, autoload_with=engine)

    # 응답에서 데이터 추출
    cast_list = response['credits']['cast']
    crew_list = response['credits']['crew']

    # 리스트 순회하며 스태프(crew) 데이터 추가하고 Commit
    for crew in crew_list:
        id = crew['id']
        adult = crew['adult']
        gender = crew['gender']
        profile_path = crew['profile_path']
        original_name = crew['original_name']
        name = crew['name']
        popularity = crew['popularity']

        stmt_cast_crew = insert(t_cast_and_crew).values(id=id, adult=adult, gender=gender,
                                                        profile_path=profile_path, original_name=original_name,
                                                        name=name, popularity=popularity)
        commit_db(stmt_cast_crew)

    # 리스트 순회하며 배우(cast) 데이터 추가하고 Commit
    for cast in cast_list:
        id = cast['id']
        adult = cast['adult']
        gender = cast['gender']
        profile_path = cast['profile_path']
        original_name = cast['original_name']
        name = cast['name']
        popularity = cast['popularity']

        stmt_cast_crew = insert(t_cast_and_crew).values(id=id, adult=adult, gender=gender,
                                                        profile_path=profile_path, original_name=original_name,
                                                        name=name, popularity=popularity)
        commit_db(stmt_cast_crew)

    # 배우와 스태프 테이블 각각 추가하는 메서드 실행
    insert_credit(response)


# 뒷 배경(backdrops) 테이블 데이터 추가 메서드
def insert_backdrops(response):
    # 테이블 연결
    t_backdrops = Table("backdrops", metadata_obj, autoload_with=engine)

    # 응답에서 데이터 추출
    movie_id = response['id']
    poster_result_list = response['images']['backdrops']

    # 리스트 순회하며 뒷 배경 데이터 추가(insert)하고 Commit
    for poster_result in poster_result_list:
        aspect_ratio = poster_result['aspect_ratio']
        height = poster_result['height']
        file_path = poster_result['file_path']
        vote_average = poster_result['vote_average']
        vote_count = poster_result['vote_count']
        width = poster_result['width']
        stmt = insert(t_backdrops).values(movie_id=movie_id, aspect_ratio=aspect_ratio,
                                          height=height, file_path=file_path,
                                          vote_average=vote_average, vote_count=vote_count,
                                          width=width)
        commit_db(stmt)


# 포스터(posters) 테이블 데이터 추가 메서드
def insert_poster(response):
    # 테이블 연결
    t_posters = Table("posters", metadata_obj, autoload_with=engine)

    # 응답에서 데이터 추출
    movie_id = response['id']
    poster_result_list = response['images']['posters']

    # 리스트 순회하며 포스터 데이터 추가(insert)하고 Commit
    for poster_result in poster_result_list:
        aspect_ratio = poster_result['aspect_ratio']
        height = poster_result['height']
        file_path = poster_result['file_path']
        vote_average = poster_result['vote_average']
        vote_count = poster_result['vote_count']
        width = poster_result['width']
        stmt = insert(t_posters).values(movie_id=movie_id, aspect_ratio=aspect_ratio,
                                        height=height, file_path=file_path,
                                        vote_average=vote_average, vote_count=vote_count,
                                        width=width)
        commit_db(stmt)


# 비디오(video) 테이블 데이터 추가 메서드
def insert_video(response):
    # 테이블 연결
    t_video = Table("video", metadata_obj, autoload_with=engine)

    # 응답에서 데이터 추출
    movie_id = response['id']
    videos_results_ = response['videos']['results']

    # 리스트 순회하며 비디오 데이터 추가(insert)하고 Commit
    for videos_result in videos_results_:
        name_ = videos_result['name']
        key_ = videos_result['key']
        site_ = videos_result['site']
        type_ = videos_result['type']
        size_ = videos_result['size']
        official_ = videos_result['official']
        published_at_ = videos_result['published_at']
        video_id_ = videos_result['id']
        stmt = insert(t_video).values(name=name_, key=key_, size=size_, type=type_,
                                      official=official_, published_at=published_at_, video_id=video_id_,
                                      movie_id=movie_id,
                                      site=site_)
        commit_db(stmt)


# 장르(genre) 테이블 데이터 추가 메서드
def insert_genre(response):
    # 테이블 연결
    t_genre = Table("genre", metadata_obj, autoload_with=engine)

    # 응답에서 데이터 추출
    genres_list = response['genres']

    # 리스트를 순회하며 장르 데이터 추가(insert)하고 Commit
    for genre in genres_list:
        id = genre['id']
        name = genre['name']
        stmt = insert(t_genre).values(id=id, name=name)
        commit_db(stmt)


# 영화 장르(movies_genre) 테이블 데이터 추가 메서드
def insert_movies_genre(response):
    # 테이블 연결
    t_movies_genre = Table("movies_genre", metadata_obj, autoload_with=engine)
    # 응답에서 데이터 추출
    movie_id = response['id']
    genres_list = response['genres']

    # 리스트를 순회하며 영화 장르 데이터 추가(insert)하고 Commit
    for genre in genres_list:
        genres_id = genre['id']
        stmt = insert(t_movies_genre).values(movie_id=movie_id, genre_id=genres_id)
        commit_db(stmt)


# 영화(movies) 테이블 데이터 추가 메서드
def insert_movies(response):
    # 테이블 연결
    t_movies = Table("movies", metadata_obj, autoload_with=engine)

    # 응답에서 데이터 추출
    id_ = response['id']

    adult_ = response['adult']
    backdrop_path_ = response['backdrop_path']
    budget_ = response['budget']
    homepage_ = response['homepage']
    imdb_id_ = response['imdb_id']
    original_language_ = response['original_language']
    original_title_ = response['original_title']
    overview_ = response['overview']
    popularity_ = response['popularity']
    poster_path_ = response['poster_path']
    release_date_ = response['release_date']
    revenue_ = response['revenue']
    runtime_ = response['runtime']
    status_ = response['status']
    tagline_ = response['tagline']
    title_ = response['title']
    video_ = response['video']
    vote_average_ = response['vote_average']
    vote_count_ = response['vote_count']

    # 추가(Insert) 쿼리문 설정하고 Commit
    stmt = insert(t_movies).values(adult=adult_, backdrop_path=backdrop_path_, budget=budget_,
                                   homepage=homepage_, id=id_, imdb_id=imdb_id_,
                                   original_language=original_language_, original_title=original_title_,
                                   overview=overview_, popularity=popularity_, poster_path=poster_path_,
                                   release_date=release_date_, revenue=revenue_, runtime=runtime_,
                                   status=status_,
                                   tagline=tagline_, title=title_, video=video_, vote_average=vote_average_,
                                   vote_count=vote_count_)
    commit_db(stmt)

    # 연관된 테이블 정보 추가 메서드 실행
    insert_movies_genre(response)
    insert_genre(response)
    insert_video(response)
    insert_poster(response)
    insert_backdrops(response)
    insert_cast_and_crew(response)

    # 디버그용
    print("movie_id( " + str(id_) + " ) is Completed")


# 영화 카테고리 목록들 갱신하는 메서드
def update_main_pages_table(response, table_name):

    # 테이블 연결
    t_main_page_table = Table(table_name, metadata_obj, autoload_with=engine)
    # 응답에서 데이터 추출
    page = response['page']
    count = (page - 1) * 20 + 1
    result_list = response['results']

    # 응답 데이터 순회하면서 테이블 갱신
    for result in result_list:
        isTrue = False
        movie_id = int(result['id'])
        # 응답된 영화 번호와 기존의 영화 번호가 동일한 순서에 위치한지 조회
        stmt = select(t_main_page_table.c.movie_id).where(t_main_page_table.c.id == count)
        origin_movie_id = db.execute(stmt)

        # 동일하지 않으면 기존 영화 정보를 갱신
        for row in origin_movie_id:
            isTrue = True

            if row[0] != movie_id:
                # 디버그용
                print("Update Table " + table_name + " id = ( " + str(count) + " )")
                stmt_query = t_main_page_table.update().where(t_main_page_table.c.id == count).values(movie_id=movie_id)
                commit_db(stmt_query)

        # 조회되지 않는다면 응답 영화 데이터 추가
        if isTrue is False:
            # 디버그용
            print("Insert Table " + table_name + " id = ( " + str(count) + " )")
            stmt_query = insert(t_main_page_table).values(id=count, movie_id=movie_id)
            commit_db(stmt_query)

        # 응답 영화 정보가 테이블에 있는지 확인
        # 없다면 영화 정보를 요청해서 테이블에 추가
        if is_dupli_movie(movie_id) == True:
            pass

        else:
            apiMain.moviesRequest(movie_id)

        # 카운트 수 증가
        count += 1


# 중복 영화 데이터 처리하는 메서드
# 중복 데이터가 있다면 True, 그렇지 않으면 False 반환
def is_dupli_movie(movie_id):
    # 테이블 연결
    t_movies = Table("movies", metadata_obj, autoload_with=engine)
    # 중복된 데이터를 조회하는 구문 실행
    stmt = select(t_movies).where(t_movies.c.id == movie_id)
    result_is_dupli = db.execute(stmt)

    # 중복 데이터가 있다면 True, 없다면 False
    for row in result_is_dupli:
        if row:
            return True

    return False


# Statement를 Commit하는 메서드
def commit_db(stmt):
    try:
        db.execute(stmt)
        db.commit()
    # 오류 발생하면 롤백하고 생략
    # 혹시 멈출때를 대비해서 계속 요청하도록 설정
    except IntegrityError as e:
        db.rollback()
        errorDict = extract_error_code(e)
        # 오류 코드가 1062이면 생략
        if errorDict['errorCode'] == 1062:
            pass
        pass
    # 오류 발생하면 생략
    except Exception as ex:
        pass
    # DB와의 연결 끊기
    finally:
        db.close()


# 에러 코드 추출하는 메서드
def extract_error_code(e):
    # 딕셔너리 생성
    errorDict = {}
    # Statement Error 정보 추출
    errorInfo = e.orig
    # 공백을 기준으로 나누고 앞의 숫자 추출
    errorCode = int(str(errorInfo).split(' ')[0])
    # 1062이면 영화 중복 에러
    if errorCode == 1062:
        errorMessage = 'PK 중복 에러 입니다'

    # 딕셔너리 타입으로 반환
    errorDict["errorCode"] = errorCode
    errorDict["errorMessage"] = errorMessage

    return errorDict


def check_null():
    isTrue = False
    t_movies = Table("movies", metadata_obj, autoload_with=engine)
    stmt = select(t_movies).where(t_movies.c.id == 2)
    data = db.execute(stmt)
    for datum in data:
        isTrue = True
        print(datum.id)
    if isTrue is False:
        print("hello")



# def update_popular(response_popular):
#     t_m_popular = Table('movies_popular', metadata_obj, autoload_with=engine)
#     page = response_popular['page']
#     count = (page - 1) * 20 + 1
#     result_list = response_popular['results']
#
#     for result in result_list:
#         isTrue = False
#         movie_id = result['id']
#         stmt = select(t_m_popular.c.movie_id).where(t_m_popular.c.id == count)
#         origin_movie_id = db.execute(stmt)
#
#
#         for row in origin_movie_id:
#             isTrue = True
#
#             if row[0] != movie_id:
#                 print("Update Table movies_now_playing id = ( " + str(count) + " )")
#                 stmt_query = t_m_popular.update().where(t_m_popular.c.id == count).values(movie_id=movie_id)
#                 commit_db(stmt_query)
#
#         if isTrue is False:
#             print("Insert movies_now_playing id = (" + str(count) + " )")
#             stmt_query = insert(t_m_popular).values(id=count, movie_id=movie_id)
#             commit_db(stmt_query)
#
#
#         if is_dupli_movie(movie_id) == True:
#             return
#
#         else:
#             apiMain.moviesRequest(movie_id)
#
#         count += 1