import React, {Component} from "react";
import commonStyle from "../css/main/common.module.css";
import loginStyle from "../css/main/login.module.css";

function LoginDiv() {
    return (
        <div id={loginStyle.container} className="login">
            <h1 className={loginStyle.logo}><a href="">에브리타임</a></h1>
            <form action="/user/login" method="post">
                <p className={loginStyle.input}>
                    <input type="text" name="loginId" maxLength="20" className={loginStyle.text} placeholder="아이디"/>
                </p>
                <p className={loginStyle.input}><input type="password" name="password" maxLength="20" className={loginStyle.text} placeholder="비밀번호"/></p>
                <input type="hidden" name="redirect" value="/"/>

                <p className={loginStyle.submit}><input type="submit" value="로그인" className={loginStyle.text}/></p>
                <label className={loginStyle.autologin}><input type="checkbox" name="autologin" value="1"/>로그인 유지</label>
                <p className={loginStyle.find}><a href="/forgot">아이디/비밀번호 찾기</a></p>
                <p className={loginStyle.register}>
                    <span>에브리타임에 처음이신가요?</span>
                    <a href="/register">회원가입</a>
                </p>
            </form>
        </div>
    );
}

function FooterAddress() {
    return (
        <address>
            <ul className={commonStyle.links}>
                <li><a href="#">이용약관</a></li>
                <li className="privacy"><a href="#">개인정보처리방침</a></li>
                <li><a href="#">문의하기</a></li>
                <li className="copyright"><a href="#">&copy; 에브리타임</a></li>
            </ul>
        </address>
    );
}

class LoginPage extends Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        return (
            <>
                <LoginDiv/>
                <FooterAddress/>
            </>
        );
    }
}

export default LoginPage;