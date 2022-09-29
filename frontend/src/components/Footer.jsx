import React from "react";

function FooterDiv() {

    return (
        <div id="bottom">
            <ul className="links">
                <li><a href="#">이용약관</a></li>
                <li className="privacy"><a href="#">개인정보처리방침</a></li>
                <li><a href="#">커뮤니티이용규칙</a></li>
                <li><a href="#">공지사항</a></li>
                <li><a href="#">문의하기</a></li>
                <li className="copyright"><a href="/">&copy; 에브리타임</a></li>
            </ul>
        </div>
    );
}

export default FooterDiv;