import axios from "axios";

const URL = "http://localhost:8080/?name=";

class IndexService {

    getSchoolList(param) {
        return axios.get(URL + param);
    }
}

export default new IndexService();