import axios from "axios";

const URL = "http://localhost:8080";

class IndexService {

    getSchoolList() {
        return axios.get(URL);
    }
}

export default new IndexService();