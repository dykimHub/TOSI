import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { useCookieStore } from "@/stores/cookieStore";
import router from "@/router";
import axios from "axios";

const User_API = `http://localhost:8080/users`;

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
            .post(`${User_API}/regist`, userInfo)
            .then((response) => {
                console.log(response.data);
                console.log(userInfo);
                postLogin(userInfo);
            })
            .catch((error) => {
                console.error("에러 발생:", error);
            });
    };

    //회원 정보 조회
    const getUser = function () {
        axios({
            method: "GET",
            url: User_API,
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
        axios.put(User_API, data, { withCredentials: true }).then(() => {
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
            url: User_API,
            withCredentials: true,
        }).then(() => {
            window.location.href = `http://localhost:5173/`;
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
            window.location.href = `http://localhost:5173/`;
            searchResult.value = response.data;
            console.log(searchResult.value);
        });
    };

    // 로그인
    const postLogin = function (userInfo) {
        axios
            .post(`${User_API}/login`, userInfo)
            .then((response) => {
                localStorage.setItem("isLoggedIn", "true");
                if (localStorage.getItem("isLoggedIn") != false) {
                    console.log("토큰" + localStorage.getItem("isLoggedIn"));
                    window.location.href = `http://localhost:5173/tosi`;
                }
            })
            .catch(() => {
                // 로그인 실패 처리
            });
    };

    //로그아웃
    const getLogout = () => {
        axios.get(`${User_API}/logout`, { withCredentials: true });
        localStorage.removeItem("isLoggedIn");
        console.log(localStorage.getItem("isLoggedIn"));
        alert("로그아웃 했습니다.");
        window.location.href = `http://localhost:5173/`;
    };

    //비밀번호 확인
    const getPasswordCheck = function (password) {
        axios
            .post(`${User_API}/password-check`, { password }, { withCredentials: true })
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
            url: `${User_API}/children-list`,
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
