import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { useCookieStore } from "@/stores/cookieStore";
import router from "@/router";
import axios from "@/util/http-common";

export const useUserStore = defineStore("user", () => {
    const userInfo = ref({ email: "", bookshelfName: "", childrenList: [] });
    const searchResult = ref(false);
    const loginUserId = ref("");
    const childrenList = ref([]);
    const child = ref({ childName: "", gender: 0, isMyBaby: false });
    const isLoggedIn = ref(false); // 로그인 했으면 true / false
    const isAuthenticated = computed(() => isLoggedIn.value);
    const cookieStore = useCookieStore();
    const passwordCheck = ref(false);

  //회원 가입
  const postUser = (userInfo) => {
    axios
      .post(`/users/regist`, userInfo)
      .then((response) => {
        console.log(response.data);
        console.log(userInfo);
        let loginInfo = {
          email: userInfo.email,
          password: userInfo.password,
          autoLogin: false,
        };
        console.log(loginInfo)
        postLogin(loginInfo);
      })
      .catch((error) => {
        console.error("에러 발생:", error);
      });
  };
  //회원 정보 조회
  const getUser = async function () {
    await axios({
      method: "GET",
      url: `/users`,
      withCredentials: true,
    }).then((response) => {
      userInfo.value = response.data;
      console.log(userInfo.value.email);
      console.log(userInfo.value.bookshelfName);
      console.log(userInfo.value.childrenList);
    });
  };
  //회원정보 수정
  const updateUser = function (data) {
    axios.put(`/users`, data, { withCredentials: true }).then(() => {
      console.log(data);
      console.log("회원정보 수정 완료");
      alert("회원 정보를 수정했습니다.");
    });
  };
  //회원 탈퇴
  const deleteUser = function () {
    axios({
      method: "delete",
      url: `/users`,
      withCredentials: true,
    }).then(() => {
      window.location.replace("/tosi");
    });
  };
  //이메일 중복 확인
  const getUserSearch = async function (email) {
    await axios({
      method: "GET",
      url: `/users/email-check`,
      params: {
        email: email,
      },
    }).then((response) => {
      searchResult.value = response.data;
      console.log(searchResult.value);
    });
  };

// 로그인
const postLogin = async function(loginInfo) {
  await axios.post(`/users/login`, loginInfo)
    .then(response => {
      if(loginInfo.autoLogin == true) {
        localStorage.setItem('isLoggedIn', 'true');
        console.log(localStorage.getItem('isLoggedIn'));
      } else {
        sessionStorage.setItem('isLoggedIn', 'true');
      }
      alert("환영합니다.")
      // router.push({ name: 'tosi' });
      window.location.replace(`http://localhost:5173/tosi`);
    })
    .catch((error) => {
      if (error.response && error.response.status === 401) {
        alert("존재하지 않는 회원입니다.")
      } else {
        console.error('응답 실패:', error);
      }
    });
};
  //로그아웃
  const getLogout = () => {
    axios.get(`/users/logout`, { withCredentials: true });
    localStorage.removeItem("isLoggedIn");
    sessionStorage.removeItem("isLoggedIn");
    cookieStore.deleteCookie("isLoggedIn");
    console.log(localStorage.getItem("isLoggedIn"));
    alert("로그아웃 했습니다.");
    window.location.replace(`http://localhost:5173/`);
  };
  
  //비밀번호 확인
  const getPasswordCheck = async function (password) {
    await axios
      .post(`/users/password-check`, {password}, { withCredentials: true })
      .then((response) => {
        passwordCheck.value = response.data;
        console.log(response);
      })
      .catch(() => {});
  };
  //아이 목록 조회
  const getChildrenList = function () {
    axios({
      method: "GET",
      url: `/users/children-list`,
      withCredentials: true,
    }).then((response) => {
      userInfo.value = response.data;
    });
  };
  return {
    postUser,
    getUser,
    userInfo,
    updateUser,
    deleteUser,
    getUserSearch,
    searchResult,
    postLogin,
    loginUserId,
    getLogout,
    getPasswordCheck,
    passwordCheck,
    getChildrenList,
    childrenList,
    isLoggedIn,
  };
});
