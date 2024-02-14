import axios from "axios";

// Axios 인스턴스 생성
const instance = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true // withCredentials 설정
});

// 응답 Interceptor 설정
instance.interceptors.response.use(
  async (response) => {
    // console.log('응답 받은 후:', response);
    if (response.data['access-token']) {
      const accessToken = response.data['access-token'];
      await setCookie('access-token', accessToken, 1);
      console.log("common:" + getCookieValue('access-token'));
    }
    if (response.data['refresh-token']) {
      const refreshToken = response.data['refresh-token'];
      await setCookie('refresh-token', refreshToken, 7);
      console.log("common:" + getCookieValue('refresh-token'));
    }
    return response;
  },
  async (error) => {
    if (error.response && error.response.status === 302) {
      window.location.href = '/';
    } else {
      console.error('응답 실패:', error);
    }
    return Promise.reject(error);
  }
);

export default instance;

function setCookie(name, value, days) {
  const expirationDate = new Date();
  expirationDate.setDate(expirationDate.getDate() + days); // 유효기간을 days일 후로 설정
  const cookieString = `${name}=${value}; expires=${expirationDate.toUTCString()}; path=/`;
  document.cookie = cookieString;
}

function getCookieValue(key) {
  const cookies = document.cookie.split(';');
  for (let i = 0; i < cookies.length; i++) {
    const cookie = cookies[i].trim();
    if (cookie.startsWith(key + '=')) {
      return cookie.substring(key.length + 1);
    }
  }
  return null;
}


