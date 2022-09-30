import React from "react";
import NavBar from "./common/Header";
import SubMenuDiv from "./common/SubMenu";
import FooterDiv from "./common/Footer";
import mainStyle from "../css/main.module.css";

function CommunityDiv() {
    return (
        <div id={mainStyle.container} className={mainStyle.community}>
            <div className={mainStyle.banners}>
                <a href="#"><img src="https://cf-eba.everytime.kr/20220922_fint_1000_home_____.png" alt="banners"/></a>
            </div>
            <LeftSideDiv/>
            <RightSideDiv/>
            <MainDiv/>
        </div>
    );
}

function LeftSideDiv() {
    return (
        <div className={mainStyle.leftside}>
            <div className={[mainStyle.card, mainStyle.pconly].join(" ")}>
                <form className={mainStyle.logged}>
                    <img src="/images/common/0.png" className={mainStyle.picture} alt="0.png"/>
                    <p className={mainStyle.nickname}>nickname</p>
                    <p className={mainStyle.school}>username</p>
                    <p className={mainStyle.school}>loginId</p>
                    <ul className={mainStyle.buttons}>
                        <li><a href="/my">내 정보</a></li>
                        <li><a href="/user/logout">로그아웃</a></li>
                    </ul>
                    <hr/>
                </form>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.menus}>
                    <a href="/myarticle" className={mainStyle.myarticle}>내가 쓴 글</a>
                    <a href="/mycommentarticle" className={mainStyle.mycommentarticle}>댓글 단 글</a>
                    <a href="/myscrap" className={mainStyle.myscrap}>내 스크랩</a>
                    <hr/>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.banner}>
                    <a href="#"><img
                        src="https://cf-eba.everytime.kr/20220908_LGCNS_card_re.png" alt="banners"/></a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.banner}>
                    <a href="#"><img
                        src="https://cf-eba.everytime.kr/20220925_monimo_Supporters_card.jpg" alt="banners"/></a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.banner}>
                    <a href="#"><img
                        src="https://cf-eba.everytime.kr/20220913_Heineken_Silver_card.jpg" alt="banners"/></a>
                </div>
            </div>
        </div>
    );
}

function RightSideDiv() {
    return (
        <div className={mainStyle.rightside}>
            <form className={mainStyle.search}>
                <input type="text" name="keyword" placeholder="전체 게시판의 글을 검색하세요!" className={mainStyle.text}/>
            </form>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">실시간 인기 글</a></h3>
                    <a href="#" className={mainStyle.article}>
                        <p className={mainStyle.title}>title</p>
                        <p className={mainStyle.small}>content</p>
                        <h4>boardName</h4>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.article}>
                        <p className={mainStyle.title}>title</p>
                        <p className={mainStyle.small}>content</p>
                        <h4>boardName</h4>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">HOT 게시물<span>더 보기</span></a></h3>
                    <a href="#" className={mainStyle.list}>
                        <time>방금</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>2분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>4분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>5분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">BEST 게시판<span>더 보기</span></a></h3>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">최근 강의평<span>더 보기</span></a></h3>
                    <a href="#" className={mainStyle.article}>
                            <span className={mainStyle.star}>
                                {/*<span className="on" style="width: 50%;"></span>*/}
                            </span>
                        <p className={mainStyle.title}>title</p>
                        <p className={mainStyle.small}>content</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">학교 소식</a></h3>
                    <a href="#" className={mainStyle.article}>
                        <p className={mainStyle.title}>title</p>
                        <p className={mainStyle.small}>content</p>
                        <h4>boardName</h4>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
        </div>
    );
}

function MainDiv() {
    return (
        <div className={mainStyle.main}>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">자유게시판</a></h3>
                    <a href="#" className={mainStyle.list}>
                        <time>방금</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>2분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>5분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>09:25 22:41</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">비밀게시판</a></h3>
                    <a href="/Main#" className={mainStyle.article}>
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.article}>
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">졸업생게시판</a></h3>
                    <a href="#" className={mainStyle.article}>
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.article}>
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">새내기게시판</a></h3>
                    <a href="#" className={mainStyle.article}>
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.article}>
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">시사·이슈</a></h3>
                    <a href="#" className={mainStyle.list}>
                        <time>방금</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>2분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>5분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>09:25 22:41</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">장터게시판</a></h3>
                    <a href="#" className={mainStyle.list}>
                        <time>방금</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>2분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>5분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>09:25 22:41</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>

            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">]정보게시판</a></h3>
                    <a href="#" className={mainStyle.list}>
                        <time>방금</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>2분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>5분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>09:25 22:41</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">취업·진료</a></h3>
                    <a href="#" className={mainStyle.list}>
                        <time>방금</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>2분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>5분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>09:25 22:41</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">공뮤원게시판</a></h3>
                    <a href="#" className={mainStyle.article}>
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.article}>
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">CPA, 금공 준비반</a></h3>
                    <a href="#" className={mainStyle.article}>
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.article}>
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">감정평가사 게시판</a></h3>
                    <a href="#" className={mainStyle.article}>
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.article}>
                        <p>content</p>
                        <time>n분 전</time>
                        <ul className={mainStyle.status}>
                            <li className={[mainStyle.vote, mainStyle.active].join(" ")}>0</li>
                            <li className={[mainStyle.comment, mainStyle.active].join(" ")}>0</li>
                        </ul>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">홍보게시판</a></h3>
                    <a href="#" className={mainStyle.list}>
                        <time>방금</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>2분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>5분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>09:25 22:41</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <div className={mainStyle.card}>
                <div className={mainStyle.board}>
                    <h3><a href="#">동아리·학회</a></h3>
                    <a href="#" className={mainStyle.list}>
                        <time>방금</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>2분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>5분 전</time>
                        <p>title</p>
                        <hr/>
                    </a>
                    <a href="#" className={mainStyle.list}>
                        <time>09:25 22:41</time>
                        <p>title</p>
                        <hr/>
                    </a>
                </div>
            </div>
            <hr/>
                <div className={mainStyle.bookstore}>
                    <a href="#" className={mainStyle.item}>
                        {/*<div className="image" style="background-image: url(/static/images/nobook.png);"></div>*/}
                        <h4>title</h4>
                        <span className={mainStyle.price}>10,000원</span>
                    </a>
                    <a href="#" className={mainStyle.item}>
                        {/*<div className="image" style="background-image: url(/static/images/nobook.png);"></div>*/}
                        <h4>title</h4>
                        <span className={mainStyle.price}>10,000원</span>
                    </a>
                    <a href="#" className={mainStyle.item}>
                        {/*<div className="image" style="background-image: url(/static/images/nobook.png);"></div>*/}
                        <h4>title</h4>
                        <span className={mainStyle.price}>10,000원</span>
                    </a>
                    <a href="#" className={mainStyle.item}>
                        todo inline style 적용
                        {/*<div className="image" style={{backgroundImage: "url('../images/common/nobook.png')"}}></div>*/}
                        <h4>title</h4>
                        <span className={mainStyle.price}>10,000원</span>
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