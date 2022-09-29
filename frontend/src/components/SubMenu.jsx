import React from "react";

function SubMenuDiv() {

    return (
        <div id="submenu">
            <div className="wrap">
                <div className="divider"></div>
                <div className="group">
                    <ul>
                        <li><a href="#" data-id="377390" className="new">자유게시판</a></li>
                        <li><a href="#" data-id="258755" className="new">비밀게시판</a></li>
                        <li><a href="#" data-id="385977" className="new">졸업생게시판</a></li>
                        <li><a href="#" data-id="385889" className="new">새내기게시판</a></li>
                        <li><a href="#" data-id="482594" className="new">시사·이슈</a></li>
                        <li><a href="#" data-id="377482" className="new">장터게시판</a></li>
                        <li><a href="#" data-id="258757">정보게시판</a></li>
                        <li><a href="#" data-id="367450" className="new">홍보게시판</a></li>
                    </ul>
                </div>
                <div className="group">
                    <ul>
                        <li><a href="#" data-id="418786">동아리·학회</a></li>
                    </ul>
                </div>
                <div className="divider"></div>
                <div className="group">
                    <ul>
                        <li><a href="#" data-id="377467" className="new">취업·진로</a></li>
                        <li><a href="#" data-id="477445" className="new">공무원 게시판</a></li>
                        <li><a href="#" data-id="492291">CPA, 금공 준비반</a></li>
                    </ul>
                </div>
                <div className="divider"></div>
                <div className="group">
                    <ul>
                        <li><a href="#" data-id="509699">총동아리연합회</a></li>
                        <li><a href="#" data-id="483471">세종대신문사</a></li>
                        <li><a href="#" data-id="486137">방송국</a></li>
                        <li><a href="#" data-id="508542">영자신문사</a></li>
                        <li><a href="#" data-id="507594">전정대 학생회</a></li>
                        <li><a href="#" data-id="506628">생명과학대학 학생회</a></li>
                        <li><a href="#" data-id="506852">법학부 학생회</a></li>
                        <li><a className="more">더 보기</a></li>
                    </ul>
                </div>
                <div className="group hidden">
                    <ul>
                        <li><a href="#" data-id="514324">소프트웨어융합대학 학생회</a></li>
                    </ul>
                </div>
                <div className="divider"></div>
                <div className="group">
                    <ul>
                        <li><a href="#" data-id="473986" className="new">생시부 게시판</a></li>
                        <li><a href="#" data-id="512490" className="new">만화애니메이션텍 게시판</a></li>
                        <li><a href="#" data-id="490112" className="new">디이노 게시판</a></li>
                        <li><a href="#" data-id="484286">물리천문학과 정보 및 교류 게시판</a></li>
                    </ul>
                </div>
                <div className="divider"></div>
                <div className="group">
                    <ul>
                        <li><a href="#" data-id="389027" className="new">은밀한게시판</a></li>
                        <li><a href="#" data-id="391378" className="new">연애게시판</a></li>
                        <li><a href="#" data-id="419439">헬스 갤러리</a></li>
                        <li><a href="#" data-id="419832" className="new">코딩게시판</a></li>
                        <li><a href="#" data-id="456127" className="new">동네친구를 찾아보자</a></li>
                        <li><a href="#" data-id="389287" className="new">퀴어 게시판</a></li>
                        <li><a href="#" data-id="424353" className="new">애니덕후 게시판</a></li>
                        <li><a href="#" data-id="453659" className="new">아이돌덕질게시판</a></li>
                    </ul>
                </div>
                <div className="group">
                    <ul>
                        <li><a href="#" data-id="390999">끝말잇기 게시판😏😏🍉</a></li>
                        <li><a href="#" data-id="393410" className="new">이상형게시판</a></li>
                        <li><a href="#" data-id="390966" className="new">노래 추천 게시판🎵</a></li>
                        <li><a href="#" data-id="399074">메이플스토리 게시판</a></li>
                        <li><a href="#" data-id="487059" className="new">케인인님 게시판</a></li>
                        <li><a href="#" data-id="508039" className="new">이세돌게시판</a></li>
                        <li><a href="#" data-id="420671">로스트아크 게시판</a></li>
                        <li><a className="more">더 보기</a></li>
                    </ul>
                </div>
                <div className="group hidden">
                    <ul>
                        <li><a href="#" data-id="452292" className="new">주식투자 게시판</a></li>
                        <li><a href="#" data-id="509033" className="new">연뮤 게시판</a></li>
                        <li><a href="#" data-id="387226">세종냥이 게시판</a></li>
                        <li><a href="#" data-id="517947" className="new">패션 게시판</a></li>
                        <li><a href="/community/search" className="search">게시판 찾기</a></li>
                    </ul>
                </div>
                <div className="divider"></div>
                <hr/>
            </div>
            <input type="hidden" id="communityCampusId" value="60"/>
        </div>
    );
}

export default SubMenuDiv;