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

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/tosi",
            name: "tosi",
            component: HomeView,
            children: [
                {
                    path: "/tales",
                    name: "tales",
                    component: TalesView,
                },
                {
                    path: "/tales/:taleId",
                    name: "taleDetail",
                    component: TaleDetail,
                    props: true,
                },
                {
                    path: "/customTale",
                    name: "customTale",
                    component: CustomTaleView,
                    children: [
                        {
                            path: "",
                            name: "customTaleList",
                            component: CustomTaleList,
                        },
                        {
                            path: "create",
                            name: "customTaleCreate",
                            component: CustomTaleCreate,
                        },
                        {
                            path: "save",
                            name: "customTaleSave",
                            component: CustomTaleSave,
                            props: true,
                        },
                        {
                            path: ":customTaleId",
                            name: "customTaleDetail",
                            component: CustomTaleDetail,
                        },
                        {
                            path: "loading",
                            name: "loading",
                            component: LoadingModal,
                        },
                    ],
                },
                {
                    path: "/tales/chat",
                    name: "gptConversation",
                    component: gptConversationView,
                },
                {
                    path: "/tales/chat/send",
                    name: "gptConversationSend",
                    component: gptConversationSend,
                },
                {
                    path: "/login",
                    name: "login",
                    component: LoginView,
                },
                {
                    path: "/regist",
                    name: "regist",
                    component: RegistView,
                },
                {
                    path: "/userInfo",
                    name: "userInfo",
                    component: UserInfoView,
                },
                {
                    path: "/userInfo/update",
                    name: "userInfoUpdate",
                    component: UserInfoUpdate,
                },
            ],
        },
        {
            path: "/",
            name: "NonMemberMainView",
            component: NonMemberMainView,
        },
<<<<<<< HEAD
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
          children: [
            {
              path: "",
              name: "customTaleList",
              component: CustomTaleList,
            },
            {
              path: "create",
              name: "customTaleCreate",
              component: CustomTaleCreate,
            },
            {
              path: "save",
              name: "customTaleSave",
              component: CustomTaleSave,
              props: true,
            },
            {
              path: ":customTaleId",
              name: "customTaleDetail",
              component: CustomTaleDetail,
            },
           {
              path: "loading",
              name: "loading",
              component: LoadingModal,
            }
          ],
        },
        {
          path: "/tales/chat",
          name: "gptConversationView",
          component: gptConversationView,
        },
        {
          path: "/tales/end/chat/:cname/:bname",
          name: "gptConversationSend",
          component: gptConversationSend,
          props: true,
        },
        {
          path: "/login",
          name: "login",
          component: LoginView,
        },
        {
          path: "/regist",
          name: "regist",
          component: RegistView,
        },
        {
          path: "/userInfo",
          name: "userInfo",
          component: UserInfoView,
        },
        {
          path: "/userInfo/update",
          name: "userInfoUpdate",
          component: UserInfoUpdate,
        },
      ],
    },
    {
      path: "/",
      name: "NonMemberMainView",
      component: NonMemberMainView,
    },
  ],
=======
    ],
>>>>>>> frontend/feature/taledetail1
});
export default router;