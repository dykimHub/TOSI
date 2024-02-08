import { createRouter, createWebHistory } from "vue-router";
import TalesView from "@/views/TalesView.vue";
import HomeView from "@/views/HomeView.vue";
import NonMemberMainView from "@/views/NonMemberMainView.vue";
import CustomTaleView from "@/views/CustomTaleView.vue";
import CustomTaleCreate from "@/components/customTale/customTaleCreate.vue";
import CustomTaleList from "@/components/customTale/customTaleList.vue";
import CustomTaleDetail from "@/components/customTale/customTaleDetail.vue";
import CustomTaleSave from "@/components/customTale/customTaleSave.vue";
import LoadingModal from "@/components/customTale/loadingModal.vue";
import gptConversationSend from "@/components/gptConversation/gptConversationSend.vue";
import gptConversationView from "@/views/gptConversationView.vue";
import TaleDetail from "@/components/taleDetail/TaleDetail.vue";
import TalePlay from "@/components/taleDetail/TalePlay.vue";
import TaleEnd from "@/components/taleDetail/TaleEnd.vue";
import LoginView from "@/views/LoginView.vue";
import RegistView from "@/views/RegistView.vue";
import UserInfoView from "@/views/UserInfoView.vue";
import UserInfoUpdate from "@/components/user/UserInfoUpdate.vue";
import BookshelfView from "@/views/BookshelfView.vue";
import favoriteTaleList from "@/components/bookshelf/favoriteTaleList.vue";
import favoriteCustomList from "@/components/bookshelf/favoriteCustomList.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "NonMemberMainView",
            component: NonMemberMainView,
            meta: { requiresGuest: true },
        },
        {
            path: "/login",
            name: "login",
            component: LoginView,
            meta: { requiresGuest: true },
        },
        {
            path: "/regist",
            name: "regist",
            component: RegistView,
            meta: { requiresGuest: true },
        },
        {
            path: "/tosi",
            name: "tosi",
            component: HomeView,
            meta: { requiresAuth: true },
            children: [
                {
                    path: "/",
                    name: "NonMemberMainView",
                    component: NonMemberMainView,
                    meta: { requiresGuest: true },
                },
                {
                    path: "/tales/:taleId",
                    name: "taleDetail",
                    component: TaleDetail,
                    props: true,
                    // meta: { requiresAuth: true },
                },
                {
                    path: "/regist",
                    name: "regist",
                    component: RegistView,
                    meta: { requiresGuest: true },
                },
                {
                    path: "/tosi",
                    name: "tosi",
                    component: HomeView,
                    meta: { requiresAuth: true },
                    children: [
                        {
                            path: "/tales",
                            name: "tales",
                            component: TalesView,
                            meta: { requiresAuth: true },
                        },
                        {
                            path: "/tales/:taleId",
                            name: "taleDetail",
                            component: TaleDetail,
                            props: true,
                            meta: { requiresAuth: true },
                        },
                        {
                            path: "/tales/play/:speaker",
                            name: "talePlay",
                            component: TalePlay,
                            props: true,
                        },
                        {
                            path: "/tales/end/:taleId",
                            name: "taleEnd",
                            component: TaleEnd,
                            props: true,
                        },
                        {
                            path: "/customTale",
                            name: "customTale",
                            component: CustomTaleView,
                            meta: { requiresAuth: true },
                            children: [
                                {
                                    path: "",
                                    name: "customTaleList",
                                    component: CustomTaleList,
                                    meta: { requiresAuth: true },
                                },
                                {
                                    path: "create",
                                    name: "customTaleCreate",
                                    component: CustomTaleCreate,
                                    meta: { requiresAuth: true },
                                },
                                {
                                    path: "save",
                                    name: "customTaleSave",
                                    component: CustomTaleSave,
                                    meta: { requiresAuth: true },
                                    props: true,
                                },
                                {
                                    path: ":customTaleId",
                                    name: "customTaleDetail",
                                    component: CustomTaleDetail,
                                    meta: { requiresAuth: true },
                                },
                                {
                                    path: "loading",
                                    name: "loading",
                                    component: LoadingModal,
                                    meta: { requiresAuth: true },
                                },
                            ],
                        },
                        {
                            path: "/tales/chat",
                            name: "gptConversationView",
                            component: gptConversationView,
                            meta: { requiresAuth: true },
                        },
                        {
                            path: "/tales/end/chat/:cname/:bname",
                            name: "gptConversationSend",
                            component: gptConversationSend,
                            meta: { requiresAuth: true },
                            props: true,
                        },

                        {
                            path: "/userInfo",
                            name: "userInfo",
                            component: UserInfoView,
                            meta: { requiresAuth: true },
                        },
                        {
                            path: "/userInfo/update",
                            name: "userInfoUpdate",
                            component: UserInfoUpdate,
                            meta: { requiresAuth: true },
                        },
                        {
                            path: "/bookshelf",
                            name: "bookshelf",
                            component: BookshelfView,
                            meta: { requiresAuth: true },
                            children: [
                                {
                                    path: "",
                                    name: "favoriteTaleList",
                                    component: favoriteTaleList,
                                    meta: { requiresAuth: true },
                                },
                                {
                                    path: "custom",
                                    name: "favoriteCustomList",
                                    component: favoriteCustomList,
                                    meta: { requiresAuth: true },
                                },
                            ],
                        },
                    ],
                },
                {
                    path: "/customTale",
                    name: "customTale",
                    component: CustomTaleView,
                    meta: { requiresAuth: true },
                    children: [
                        {
                            path: "",
                            name: "customTaleList",
                            component: CustomTaleList,
                            meta: { requiresAuth: true },
                        },
                        {
                            path: "create",
                            name: "customTaleCreate",
                            component: CustomTaleCreate,
                            meta: { requiresAuth: true },
                        },
                        {
                            path: "save",
                            name: "customTaleSave",
                            component: CustomTaleSave,
                            meta: { requiresAuth: true },
                            props: true,
                        },
                        {
                            path: ":customTaleId",
                            name: "customTaleDetail",
                            component: CustomTaleDetail,
                            meta: { requiresAuth: true },
                        },
                        {
                            path: "loading",
                            name: "loading",
                            component: LoadingModal,
                            meta: { requiresAuth: true },
                        },
                    ],
                },
                {
                    path: "/tales/chat",
                    name: "gptConversationView",
                    component: gptConversationView,
                    meta: { requiresAuth: true },
                },
                {
                    path: "/tales/end/chat/:cname/:bname",
                    name: "gptConversationSend",
                    component: gptConversationSend,
                    meta: { requiresAuth: true },
                    props: true,
                },

                {
                    path: "/userInfo",
                    name: "userInfo",
                    component: UserInfoView,
                    meta: { requiresAuth: true },
                },
                {
                    path: "/userInfo/update",
                    name: "userInfoUpdate",
                    component: UserInfoUpdate,
                    meta: { requiresAuth: true },
                },
                {
                    path: "/bookshelf",
                    name: "bookshelf",
                    component: BookshelfView,
                    meta: { requiresAuth: true },
                    children: [
                        {
                            path: "",
                            name: "favoriteTaleList",
                            component: favoriteTaleList,
                            meta: { requiresAuth: true },
                        },
                        {
                            path: "custom",
                            name: "favoriteCustomList",
                            component: favoriteCustomList,
                            meta: { requiresAuth: true },
                        },
                    ],
                },
            ],
        },
    ],
});

router.beforeEach((to, from, next) => {
    const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";

    if (to.matched.some((record) => record.meta.requiresAuth) && !isLoggedIn) {
        alert("로그인 페이지로 이동합니다.");
        next("/login");
    } else if (to.matched.some((record) => record.meta.requiresGuest) && isLoggedIn) {
        alert("이미 로그인 되어있습니다.");
        next("/tosi");
    } else {
        next();
    }
});

export default router;