import React, {Component} from "react";
import LoginService from "../service/LoginService";
import commonStyle from "../css/main/common.module.css";
import loginStyle from "../css/main/login.module.css";

class Login extends Component {
    constructor(props) {
        super(props);
        this.state ={loginId: "", password: ""}

        this.changeLoginIdHandler = this.changeLoginIdHandler.bind(this);
        this.changePasswordHandler = this.changePasswordHandler.bind(this);
        this.login = this.login.bind(this);
    }

    changeLoginIdHandler = (event) => {
        this.setState({loginId: event.target.value})
    }

    changePasswordHandler = (event) => {
        this.setState({password: event.target.value})
    }

    login = (event) => {
        event.preventDefault();


        if (this.state.loginId === "" || this.state.password === "") {
            window.alert("빈칸");
            return;
        }

        const loginRequest = {
            loginId: this.state.loginId,
            password: this.state.password
        };
        LoginService.login(loginRequest)
            .then(response => {
                if (response.data.message) {
                    this.setState({errorMessage: response.data.message});
                    return;
                }
                document.location.href = "/";
            })
            .catch(error => {
                this.state(error);
                alert(error);
                // this.setState({error: error.message})
            });
    }

    render() {
        return (
            <div id={loginStyle.container} className="login">
                <h1 className={loginStyle.logo}><a href="">에브리타임</a></h1>
                {this.state.errorMessage && <div>{this.state.errorMessage}</div>}
                <form action="/login" method="post">
                    <p className={loginStyle.input}>
                        <input type="text" name="loginId" maxLength="20" className={loginStyle.text} placeholder="아이디"
                        value={this.state.loginId} onChange={this.changeLoginIdHandler}/>
                    </p>
                    <p className={loginStyle.input}>
                        <input type="password" name="password" maxLength="20" className={loginStyle.text} placeholder="비밀번호"
                        value={this.state.password} onChange={this.changePasswordHandler}/>
                    </p>
                    <input type="hidden" name="redirect" value="/"/>
                    <p className={loginStyle.submit}><input type="submit" value="로그인" className={loginStyle.text}
                    onClick={this.login}/></p>
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
                <Login/>
                <FooterAddress/>
            </>
        );
    }
}

export default LoginPage;