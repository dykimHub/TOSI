import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { useCookieStore } from "@/stores/cookieStore";
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
  //회원 가입
  const postUser = (userInfo) => {
    axios
      .post(`/users/regist`, userInfo)
      .then((response) => {
        console.log(response.data);
        console.log(userInfo);
        let loginInfo = {
          email: userInfo.email.value,
          password: userInfo.password.value,
          autoLogin: false
        };
        postLogin(loginInfo);
      })
      .catch((error) => {
        console.error("에러 발생:", error);
      });
  };
  //회원 정보 조회
  const getUser = function () {
    axios({
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
      router.push({ name: "userInfoUpdate" });
    });
  };
  //회원 탈퇴
  const deleteUser = function () {
    axios({
      method: "delete",
      url: `/users`,
      withCredentials: true,
    }).then(() => {
      window.location.replace("/tosi")
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
// // 로그인
// const postLogin = function(loginInfo) {
//   axios({
//     method: "POST",
//     url: `/users/login`,
//     data: loginInfo,
//   })
//     .then((response) => {
//       let loginType = '';
//       if(loginInfo.autoLogin == true) {
//         localStorage.setItem('isLoggedIn', 'true');
//       } else {
//         sessionStorage.setItem('isLoggedIn', 'true');
//       }
//       alert("환영합니다.")
//       window.location.replace(`http://localhost:5173/tosi`);
//     })
//     .catch(() => {
//       // 로그인 실패 처리
//       return false;
//     });
// };
// 로그인
const postLogin = function(loginInfo) {
  axios.post(`/users/login`, loginInfo)
    .then(response => {
      if(loginInfo.autoLogin == true) {
        const accessToken = response.data['access-token'];
        const refreshToken = response.data['refresh-token'];
        cookieStore.setCookie('access-token', accessToken, 1);
        cookieStore.setCookie('refresh-token', refreshToken, 7);
        localStorage.setItem('isLoggedIn', 'true');
      } else {
        sessionStorage.setItem('isLoggedIn', 'true');
      }
      alert("환영합니다.")
      window.location.replace(`http://localhost:5173/tosi`);
    })
    .catch(() => {
      // 로그인 실패 처리
      return false;
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
    window.location.replace(`http://localhost:5173/`)
  };
  //비밀번호 확인
  const getPasswordCheck = function (password) {
    axios
      .post(`/users/password-check`, { password }, { withCredentials: true })
      .then((response) => {
        console.log(response);
        router.push({ name: "userInfoUpdate" });
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
    getChildrenList,
    childrenList,
    isLoggedIn,
  };
});