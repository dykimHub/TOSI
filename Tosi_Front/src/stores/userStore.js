import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import router from "@/router";
import axios from "axios";

const User_API = `http://localhost:8080/user`;

export const useUserStore = defineStore('user', () => {
  const userInfo = ref('')
  const searchResult = ref([]);
  const loginUserId = ref('')
  

  //회원 가입
  const postUser = (createUser) => {
    const userInfo = {
        email: createUser.email,
        password: createUser.password,
        name: createUser.name,

    };

    axios.post(User_API, userInfo)
        .then(response => {
            console.log(response.data);
            postLogin(userInfo)
        })
        .catch(error => {
            console.error('에러 발생:', error);
        });
};

  //회원 정보 조회
  const getUser = function (userId) {
    axios({
      method: "GET",
      url: User_API,
      params: {
        userId: userId,
      },
    }).then((response) => {
      userInfo.value = response.data;
    });
  };
  
  //회원정보 수정
  const updateUser = function (data) {
    axios.put(User_API, data).then(() => {
      console.log(data)
      console.log("회원정보 수정 완료")
      data.password = "";
      localStorage.setItem("loginUser", JSON.stringify(data));
      window.location.href = `http://localhost:5173/`
    });
  };

  //회원 탈퇴
  const deleteUser = function (data) {
    axios({
      method: "delete",
      url: User_API,
      params: {
        userId: data,
      }
    }).then(() => {
      window.location.href = `http://localhost:5173/`
    });
  };

  //이메일 중복 확인
  const getUserSearch = function (email) {
    axios({
      method: "GET",
      url: `${User_API}/search`,
      params: {
        email: email,
      },
    }).then((response) => {
      searchResult.value = response.data;
    });
  };

  //로그인
  const postLogin = function(email, password){
    axios.post(`${User_API}/login`, {email, password})
    .then((response)=>{
      console.log(response)

      sessionStorage.setItem('access-token', response.data["access-token"])
      sessionStorage.setItem('refresh-token', response.data["refresh-token"])

      const token = response.data['access-token'].split('.')
      let userId = token[1]
      userId = atob(userId)
      userId = JSON.parse(userId)
      console.log(userId['userId'])
      loginUserId.value = userId['userId']

      router.push({name: "tosi"})

    })
    .catch(()=>{

    })
  }

  //로그아웃
  const getLogout = () => {
    axios.get(`${User_API}/logout`)
    sessionStorage.removeItem('access-token')
    sessionStorage.removeItem('refresh-token')
    user.value = null;
    localStorage.removeItem("loginUser");
    alert("로그아웃 했습니다.");
    window.location.href = "http://localhost:5173/"
  };

  return { postUser, getUser, userInfo, updateUser, deleteUser, getUserSearch, searchResult, postLogin, loginUserId, getLogout };
});

