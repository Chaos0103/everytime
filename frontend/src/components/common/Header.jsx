import React from "react";
import common from "../../css/common.partial.module.css";

function NavBar() {
    return (
        <nav>
            <div className={common.wrap}>
                <div id={common.logo}>
                    <a href="#"><img src="/images/common/nav.logo.png" alt="nav.logo.png"/></a>
                    <p><span className={[common.name, common.multiple].join(" ")}>에브리타임</span><span className={common.subname}>세종대</span></p>
                </div>
                <div id={common.account}>
                    <a href="/message" title="쪽지함" className={[common.icon, common.message].join(" ")}>쪽지함</a>
                    <a href="/my" title="내 정보" className={[common.icon, common.my].join(" ")}>내 정보</a>
                    <input type="hidden" id="userUserid" value="lyt1228"/>
                    <input type="hidden" id="userSchool" value="36"/>
                    <input type="hidden" id="userCampus" value="60"/>
                </div>
                <ul id={common.menu}>
                    <li className={common.active}><a href="/static">게시판</a></li>
                    <li><a href="/timetable">시간표</a></li>
                    <li><a href="/lecture">강의실</a></li>
                    <li><a href="/calculator">학점계산기</a></li>
                    <li><a href="/friend">친구</a></li>
                    <li><a href="/book">책방</a></li>
                </ul>
            </div>
        </nav>
    );
}

export default NavBar;