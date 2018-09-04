import axios from "axios";

const baseHttp = axios.create({
  baseURL: `http://localhost:4000`,
  timeout: 5000,
  headers: {
    accepts: "application/json"
  },
  validateStatus: status => {
    return status >= 200 && status < 300;
  }
});

export default baseHttp;
