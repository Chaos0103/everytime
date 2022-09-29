import React from "react";
import NavBar from "./Header";
import SubMenuDiv from "./SubMenu";
import FooterDiv from "./Footer";
// import "../css/common.partial.css";
// import "../css/container/container.community.css"

function CommunityDiv() {
    return (
        <div id="container" className="community">
            <div className="banners">
                <a href="frontend/src/components/Main#"><img src="https://cf-eba.everytime.kr/20220922_fint_1000_home_____.png" alt="banners"/></a>
            </div>
            <LeftSideDiv/>
            <RightSideDiv/>
            <MainDiv/>
        </div>
    );
}

function LeftSideDiv() {
    return (
        <div className="leftside">
            <div className="card pconly">
                <form className="logged">
                    <img src="/images/common/0.png" className="picture" alt="0.png"/>
                        <p className="nickname">nickname</p>
                        <p className="school">username</p>
                        <p className="school">loginId</p>
                        <ul className="buttons">
                            <li><a href="/my">내 정보</a></li>
                            <li><a href="/user/logout">로그아웃</a></li>
                        </ul>
                        <hr/>
                </form>
            </div>
            <div className="card">
                <div className="menus">
                    <a href="/myarticle" className="myarticle">내가 쓴 글</a>
                    <a href="/mycommentarticle" className="mycommentarticle">댓글 단 글</a>
                    <a href="/myscrap" className="myscrap">내 스크랩</a>
                    <hr/>
                </div>
            </div>
            <div className="card">
                <div className="banner">
                    <a href="frontend/src/components/Main#"><img src="https://cf-eba.everytime.kr/20220908_LGCNS_card_re.png" alt="banners"/></a>
                </div>
            </div>
            <div className="card">
                <div className="banner">
                    <a href="frontend/src/components/Main#"><img src="https://cf-eba.everytime.kr/20220925_monimo_Supporters_card.jpg" alt="banners"/></a>
                </div>
            </div>
            <div className="card">
                <div className="banner">
                    <a href="frontend/src/components/Main#"><img src="https://cf-eba.everytime.kr/20220913_Heineken_Silver_card.jpg" alt="banners"/></a>
                </div>
            </div>
        </div>
    );
}

function RightSideDiv() {
    return (
        <div className="rightside">
            <form className="search">
                <input type="text" name="keyword" placeholder="전체 게시판의 글을 검색하세요!" className="text"/>
            </form>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">실시간 인기 글</a></h3>
                    <a href="frontend/src/components/Main#" className="article">
                        <p className="title">title</p>
                        <p className="small">content</p>
                        <h4>boardName</h4>
                        <ul className="status">

                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="article">
                        <p className="title">title</p>
                        <p className="small">content</p>
                        <h4>boardName</h4>
                        <ul className="status">
                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">HOT 게시물<span>더 보기</span></a></h3>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>방금</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>2분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>4분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>5분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">BEST 게시판<span>더 보기</span></a></h3>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">학교 소식</a></h3>
                    <a href="frontend/src/components/Main#" className="article">
                        <p className="title">title</p>
                        <p className="small">content</p>
                        <h4>boardName</h4>
                        <ul className="status">
                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">최근 강의평<span>더 보기</span></a></h3>
                    <a href="frontend/src/components/Main#" className="article">
                            <span className="star">
                                {/*<span className="on" style="width: 50%;"></span>*/}
                            </span>
                        <p className="title">title</p>
                        <p className="small">content</p>
                        <hr/>
                    </a>
                </div>
            </div>
        </div>
    );
}

function MainDiv() {
    return (
        <div className="main">
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">자유게시판</a></h3>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>방금</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>2분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>5분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>09:25 22:41</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">비밀게시판</a></h3>
                    <a href="frontend/src/components/Main#" className="article">
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className="status">
                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="article">
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className="status">
                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">졸업생게시판</a></h3>
                    <a href="frontend/src/components/Main#" className="article">
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className="status">
                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="article">
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className="status">
                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">새내기게시판</a></h3>
                    <a href="frontend/src/components/Main#" className="article">
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className="status">
                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="article">
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className="status">
                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">시사·이슈</a></h3>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">장터게시판</a></h3>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">정보게시판</a></h3>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">취업·진로</a></h3>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">공무원 게시판</a></h3>
                    <a href="frontend/src/components/Main#" className="article">
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className="status">
                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="article">
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className="status">
                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">CPA, 금공 준비반</a></h3>
                    <a href="frontend/src/components/Main#" className="article">
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className="status">
                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="article">
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className="status">
                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">감정평가사 게시판</a></h3>
                    <a href="frontend/src/components/Main#" className="article">
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className="status">
                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="article">
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className="status">
                            <li className="vote active">0</li>
                            <li className="comment active">0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">홍보게시판</a></h3>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className="card">
                <div className="board">
                    <h3><a href="frontend/src/components/Main#">동아리·학회</a></h3>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="frontend/src/components/Main#" className="list">
                        <time>n분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <hr/>
                <div className="bookstore">
                    <a href="frontend/src/components/Main#" className="item">
                        {/*<div className="image" style="background-image: url(/static/images/nobook.png);"></div>*/}
                        <h4>title</h4>
                        <span className="price">10,000원</span>
                    </a>
                    <a href="frontend/src/components/Main#" className="item">
                        {/*<div className="image" style="background-image: url(/static/images/nobook.png);"></div>*/}
                        <h4>title</h4>
                        <span className="price">10,000원</span>
                    </a>
                    <a href="frontend/src/components/Main#" className="item">
                        {/*<div className="image" style="background-image: url(/static/images/nobook.png);"></div>*/}
                        <h4>title</h4>
                        <span className="price">10,000원</span>
                    </a>
                    <a href="frontend/src/components/Main#" className="item">
                        {/*<div className="image" style="background-image: url(/static/images/nobook.png);"></div>*/}
                        <h4>title</h4>
                        <span className="price">10,000원</span>
                    </a>
                </div>
        </div>
    );
}

function MainPage() {
    return (
        <>
            <NavBar/>
            <SubMenuDiv/>
            <CommunityDiv/>
            <FooterDiv/>
        </>
    );
}

export default MainPage;