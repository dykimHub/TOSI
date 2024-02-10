import { createRouter, createWebHistory } from "vue-router";
import TalesView from '@/components/tale/TaleList.vue';
import SearchView from '@/components/tale/SearchTale.vue';
import HomeView from "@/views/HomeView.vue";
import NonMemberMainView from "@/views/NonMemberMainView.vue";
import CustomTaleView from "@/views/CustomTaleView.vue";
import CustomTaleCreate from "@/components/customTale/customTaleCreate.vue";
import CustomTaleList from "@/components/customTale/customTaleList.vue";
import CustomTaleDetail from "@/components/customTale/customTaleDetail.vue";
import CustomTaleSave from "@/components/customTale/customTaleSave.vue";
import CustomTalePlay from "@/components/customTale/customTalePlay.vue";
import CustomTaleCreatePlay from "@/components/customTale/customTaleCreatePlay.vue";
import CustomTaleEnd from "@/components/customTale/customTaleEnd.vue";
import LoadingModal from "@/components/customTale/loadingModal.vue";
import gptConversationSend from "@/components/gptConversation/gptConversationSend.vue";
import gptConversationView from "@/views/gptConversationView.vue";
import TaleDetail from "@/components/taleDetail/TaleDetail.vue";
import TalePlay from "@/components/taleDetail/TalePlay.vue";
import TaleEnd from "@/components/taleDetail/TaleEnd.vue";
import UserInfoView from "@/views/UserInfoView.vue";
import UserInfoUpdate from "@/components/user/UserInfoUpdate.vue";
import BookshelfView from "@/views/BookshelfView.vue";
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
          path: "/search",
          name: "search",
          component: SearchView,
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
          meta: { requiresAuth: true },
          props: true,
        },
        {
          path: "/tales/end/:taleId",
          name: "taleEnd",
          component: TaleEnd,
          meta: { requiresAuth: true },
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
              path: "play/:speaker",
              name: "customTalePlay",
              component: CustomTalePlay,
              meta: { requiresAuth: true },
              props: true,
            },
            {
              path: "createplay/:speaker",
              name: "customTaleCreatePlay",
              component: CustomTaleCreatePlay,
              meta: { requiresAuth: true },
              props: true,
            },
            {
              path: "end",
              name: "customTaleEnd",
              component: CustomTaleEnd,
              meta: { requiresAuth: true },
              props: true,
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
        },
      ],
    },
  ],
});

router.beforeEach((to, from, next) => {
  const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";
  const isLoggedInS = sessionStorage.getItem("isLoggedIn") === "true";
  if (to.matched.some((record) => record.meta.requiresAuth) && (!isLoggedIn && !isLoggedInS)) {
    alert("메인 페이지로 이동합니다.");
    next("/");
  } else if (to.matched.some((record) => record.meta.requiresGuest) && (isLoggedIn || isLoggedInS)) {
    alert("이미 로그인 되어있습니다.");
    next("/tosi");
  } else {
    next();
  }
});
export default router;
