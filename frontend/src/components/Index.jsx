import React, {Component} from "react";
import IndexService from "../service/IndexService";
import style from "../css/index/styles.module.css";

class CampusList extends Component {
    constructor(props) {
        super(props);
        this.state = {schoolList: []}
    }

    componentDidMount() {
        let param = new URLSearchParams(window.location.search).get("name");
        if (param === null) {
            param = "";
        }
        IndexService.getSchoolList(param)
            .then((response) => {
                this.setState({schoolList: response.data})
            });
    }

    numberFormat(number) {
        return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }

    render() {
        return (
            <div className={style.campuslist}>
                {this.state.schoolList.map(
                    school =>
                        <a href="#" key={school.id}><span className={style.name}>{school.schoolName}</span><span className={style.count}>{this.numberFormat(school.count)}명</span></a>
                )}
            </div>
        )
    }
}

function Aside () {

    function Login() {
        return (
            <div className={style.login}>
                <a className={style.logo}><img src="/images/index/logo.png" alt="에브리타임"/></a>
                <a href="/login" className={`${style.button} ${style.login}`}>로그인</a>
                <a href="/register" className={`${style.button} ${style.register}`}>에브리타임 회원가입</a>
                <p className={style.find}>
                    <a href="/forgot">아이디/비밀번호 찾기</a>
                </p>
                <hr/>
            </div>
        );
    }

    function SearchForm() {

        return (
            <form className={style.search}>
                <p>우리 학교 커뮤니티 둘러보기</p>
                <input id="searchForm" type="text" name="name" placeholder="찾으시는 캠퍼스를 검색하세요." autoComplete="off"/>
            </form>
        );
    }

    return (
        <aside>
            <Login/>
            <SearchForm/>
            <CampusList/>
        </aside>
    );
}

function InitSection() {
    return (
        <section className={style.init}>
            <div className={style.wrap}>
                <h1>대학 생활을 더 편하고 즐겁게,<br/><strong>에브리타임</strong></h1>
                <div className={style.stores}>
                    <a href="frontend/src/components/Index#"><img src="/images/index/playstore.png" alt="에브리타임 - Play Store - Google"/></a>
                    <a href="frontend/src/components/Index#"><img src="/images/index/appstore.png" alt="에브리타임 - AppStore - Apple"/></a>
                </div>
                <hr/>
                <div className={style.device}>
                    <div className={style.screen}>
                        <div className={[style.image, style.home].join(" ")}></div>
                        <div className={[style.image, style.timetable].join(" ")}></div>
                        <div className={[style.image, style.board].join(" ")}></div>
                    </div>
                </div>
            </div>
        </section>
    );
}

function ServiceSection() {
    return (
        <section className={[style.service, style.white].join(" ")}>
            <h2>350만 대학생을 위한<br/><strong>국내 1위 대학생 서비스 에브리타임!</strong></h2>
            <div className={style.paragraph}>
                <p>시간표 작성, 수업 일정 및 할일 등 편리한 <strong>학업 관리</strong>가 가능하고,
                    <br/>학식 등 유용한 <strong>학교 생활 정보</strong>를 접할 수 있으며,
                    <br/>같은 캠퍼스의 학생들과 소통하는 <strong>익명 커뮤니티</strong>를 이용할 수 있습니다.</p>
            </div>
            <div className={style.figures}>
                <div>
                    <p className={style.number}><strong data-number="592">592</strong><span>만</span></p>
                    <p className={style.description}>가입한 대학생</p>
                </div>
                <hr/>
                <div>
                    <p className={style.number}><strong data-number="3577">3,577</strong><span>만</span></p>
                    <p className={style.description}>만들어진 시간표</p>
                </div>
                <hr/>
                <div>
                    <p className={style.number}><strong data-number="533">533</strong><span>만</span></p>
                    <p className={style.description}>강의평/시험정보</p>
                </div>
                <hr/>
                <div>
                    <p className={style.number}><strong data-number="229">229</strong><span>만</span></p>
                    <p className={style.description}>중고 거래된 책</p>
                </div>
                <hr/>
                <div>
                    <p className={style.number}><strong data-number="15">15</strong><span>억</span><strong
                        data-number="1981">1,981</strong><span>만</span></p>
                    <p className={style.description}>작성된 게시물</p>
                </div>
            </div>
        </section>
    );
}

function CommunitySection() {
    return (
        <section className={style.community}>
            <h2>전국 397개 캠퍼스
                <br/><strong>재학생 커뮤니티 에브리타임!</strong></h2>
            <div className={style.paragraph}>
                <p>학교 인증을 거친 재학생들의 안전한 대화를 위한 <strong>익명 시스템</strong>과
                    <br/>학생들이 직접 게시판을 개설하여 운영하는 <strong>커뮤니티 플랫폼</strong>을 통해
                    <br/>많은 대학교에서 가장 활발히 이용하는 재학생 커뮤니티로 자리잡았습니다.</p>
            </div>
            <div className={style.figures}>
                <div>
                    <p className={style.icon}><img src="/images/index/icon.authorized.png" alt=""/></p>
                    <p className={style.description}>철저한 학교 인증</p>
                </div>
                <div>
                    <p className={style.icon}><img src="/images/index/icon.anonymous.png" alt=""/></p>
                    <p className={style.description}>완벽한 익명 시스템</p>
                </div>
                <div>
                    <p className={style.icon}><img src="/images/index/icon.users.png" alt=""/></p>
                    <p className={style.description}>재학생 운영 게시판</p>
                </div>
            </div>
        </section>
    );
}

function FooterSection() {
    return (
        <section className={[style.footer, style.white].join(" ")}>
            <ul className={style.links}>
                <li><a href="frontend/src/components/Index#">이용약관</a></li>
                <li className={style.privacy}><a href="frontend/src/components/Index#">개인정보처리방침</a></li>
                <li><a href="frontend/src/components/Index#">문의하기</a></li>
                <li className={style.copyright}><a href="/Users/limwootaek/mainProject/everytime/frontend/src/components/Index">&copy; 에브리타임</a></li>
            </ul>
        </section>
    );
}

class IndexPage extends Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        return (
            <>
                <Aside/>
                <InitSection/>
                <ServiceSection/>
                <CommunitySection/>
                <FooterSection/>
            </>
        );
    }
}

export default IndexPage;