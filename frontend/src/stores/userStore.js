import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "@/util/http-common";
import { useRouter } from "vue-router";
import { useToast } from "vue-toast-notification";

export const useUserStore = defineStore("user", () => {
  const router = useRouter();
  const toast = useToast();
  const userInfo = ref({ email: "", bookshelfName: "", childrenList: [] });
  const searchResult = ref(false);
  const loginUserId = ref("");
  const childrenList = ref([]);
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
        console.log(loginInfo);
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
    });
  };
  //회원정보 수정
  const updateUser = function (data) {
    axios.put(`/users`, data, { withCredentials: true }).then(() => {
      console.log(data);
      console.log("회원정보 수정 완료");
      toast.success("회원 정보를 수정했어요.", {
        position: "top",
        duration: 2000,
        queue: true,
        style: {
          backgroundColor: "#f1a8bc",
          color: "white",
        },
      });
    });
  };
  //회원 탈퇴
  const deleteUser = function () {
    axios({
      method: "delete",
      url: `/users`,
      withCredentials: true,
    }).then(() => {
      localStorage.removeItem("isLoggedIn");
      sessionStorage.removeItem("isLoggedIn");
      router.push({ name: "NonMemberMainView" });
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
  const postLogin = async function (loginInfo) {
    await axios
      .post(`/users/login`, loginInfo)
      .then((response) => {
        if (loginInfo.autoLogin == true) {
          localStorage.setItem("isLoggedIn", "true");
          console.log(localStorage.getItem("isLoggedIn"));
        } else {
          sessionStorage.setItem("isLoggedIn", "true");
        }

        router.push({ name: "tosi" });
        toast.success("안녕~ 반가워요!!", {
          position: "top",
          duration: 2000,
          queue: true,
          style: {
            backgroundColor: "#f1a8bc",
            color: "white",
          },
        });
      })
      .catch((error) => {
        if (error.response && error.response.status === 401) {
          toast.error("존재하지 않는 회원이에요.", {
            position: "top",
            duration: 2000,
            queue: true,
            style: {
              backgroundColor: "#f1a8bc",
              color: "white",
            },
          });
        } else {
          console.error("응답 실패:", error);
        }
      });
  };
  //로그아웃
  const getLogout = () => {
    axios.get(`/users/logout`, { withCredentials: true });
    localStorage.removeItem("isLoggedIn");
    sessionStorage.removeItem("isLoggedIn");
    console.log(localStorage.getItem("isLoggedIn"));
    router.push({ name: "NonMemberMainView" });
    toast.success("로그아웃. 또 만나요~!!", {
      position: "top", // 알림 위치 변경
      duration: 2000, // 알림 표시 시간 설정
      queue: true,
      style: {
        backgroundColor: "#f1a8bc", // 알림 배경색 변경
        color: "white", // 알림 텍스트 색상 변경
      },
    });
  };

  //비밀번호 확인
  const getPasswordCheck = async function (password) {
    await axios
      .post(`/users/password-check`, { password }, { withCredentials: true })
      .then((response) => {
        console.log(response);
        if (response.data == false) {
          toast.info("비밀번호가 달라요. 올바른 비밀번호를 입력해주세요.", {
            position: "top",
            duration: 2000,
            queue: true,
            style: {
              backgroundColor: "#f1a8bc",
              color: "white",
            },
          });
        } else if (response.data == true) {
          passwordCheck.value = response.data;
        }
      })
      .catch((error) => {});
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
    childrenList
  };
});
