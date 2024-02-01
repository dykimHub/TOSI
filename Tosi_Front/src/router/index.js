import { createRouter, createWebHistory } from "vue-router";
import TalesView from "@/views/TalesView.vue";
import HomeView from "@/views/HomeView.vue";
import CustomTaleView from "@/views/CustomTaleView.vue";
import CustomTaleCreate from "@/components/customTale/customTaleCreate.vue";
import CustomTaleList from "@/components/customTale/customTaleList.vue";
import CustomTaleDetail from "@/components/customTale/customTaleDetail.vue";
import CustomTaleSave from "@/components/customTale/customTaleSave.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/tosi",
      name: "tosi",
      component: HomeView,
    },
    {
      path: "/tales",
      name: "tales",
      component: TalesView,
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
        },
        {
          path: ":customTaleId",
          name: "customTaleDetail",
          component: CustomTaleDetail,
        },
      ],
    },
  ],
});

export default router;
