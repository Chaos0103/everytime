import axios from "axios";

const URL = "http://localhost:8080/login";

class LoginService {
    login(data) {
        return axios.post(URL, data);
    }
}

export default new LoginService();