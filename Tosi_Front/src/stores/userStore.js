import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import router from "@/router";
import axios from "axios";

const User_API = `http://localhost:8080/users`;

export const useUserStore = defineStore('user', () => {
  const userInfo = ref('')
  const searchResult = ref([]);
  const loginUserId = ref('');
  const childrenList = ref([]);
  const isAuth = ref(''); // 로그인 했으면 true / false
  

  //회원 가입
  const postUser = (userInfo) => {

    axios.post(`${User_API}/regist`, userInfo)
        .then(response => {
            console.log(response.data);
            console.log(userInfo);
            postLogin(userInfo)
        })
        .catch(error => {
            console.error('에러 발생:', error);
        });
  };

  //회원 정보 조회
  const getUser = function () {
    axios({
      method: "GET",
      url: User_API
    }).then((response) => {
      userInfo.value = response.data;
    });
  };
  
  //회원정보 수정
  const updateUser = function (data) {
    axios.put(User_API, data).then(() => {
      console.log(data)
      console.log("회원정보 수정 완료")
      alert("회원 정보를 수정했습니다.");
      router.push({name: "UserInfoUpdate"})
    });
  };

  //회원 탈퇴
  const deleteUser = function () {
    axios({
      method: "delete",
      url: User_API
    }).then(() => {
      window.location.href = `http://localhost:5173/`
    });
  };

  //이메일 중복 확인
  const getUserSearch = function (email) {
    axios({
      method: "GET",
      url: `${User_API}/email-check`,
      params: {
        email: email,
      },
    }).then((response) => {
      searchResult.value = response.data;
    });
  };

  //로그인
  const postLogin = function(userInfo){
    axios.post(`${User_API}/login`, userInfo)
    .then((response)=>{
      console.log(response)
      
  isAuth.value = true;
      router.push({name: "tosi"})

    })
    .catch(()=>{

    })
  }

  //로그아웃
  const getLogout = () => {
    axios.get(`${User_API}/logout`)
    isAuth.value = false;
    alert("로그아웃 했습니다.");
    window.location.href = "http://localhost:5173/"
  };

  //비밀번호 확인
  const getPasswordCheck = function(password){
    axios.post(`${User_API}/password-check`, {password})
    .then((response)=>{
      console.log(response)

      router.push({name: "userInfoUpdate"})

    })
    .catch(()=>{

    })
  }

  //아이 목록 조회
  const getChildrenList = function () {
    axios({
      method: "GET",
      url: `${User_API}/children-list`,
      params: {
        childrenList: childrenList,
      },
    }).then((response) => {
      userInfo.value = response.data;
    });
  };


  return { postUser, getUser, userInfo, updateUser, deleteUser, getUserSearch, searchResult, 
    postLogin, loginUserId, getLogout, getPasswordCheck, getChildrenList, childrenList };
});

