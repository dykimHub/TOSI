import axios from 'axios';

export default axios.create({
    baseURL: "http://localhost:8080",
    headers: {
        "Content-Type": "application/json",
    }
});

axios.defaults.withCredentials = true;