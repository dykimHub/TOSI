import { createRouter, createWebHistory } from "vue-router";
import TalesView from '@/views/TalesView.vue'
import HomeView from '@/views/HomeView.vue'
import CustomTaleView from '@/views/CustomTaleView.vue'
import CustomTaleCreate from '@/components/customTale/customTaleCreate.vue'
import CustomTaleList from '@/components/customTale/customTaleList.vue'
import CustomTaleDetail from '@/components/customTale/customTaleDetail.vue'
import CustomTaleSave from '@/components/customTale/customTaleSave.vue'
import LoginView from '@/views/LoginView.vue'
import RegistView from '@/views/RegistView.vue'
import UserInfoView from '@/views/UserInfoView.vue'
import UserInfoUpdate from '@/components/user/UserInfoUpdate.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
        path: '/tosi',
        name: 'tosi',
        component: HomeView
        },
        {
        path: '/tales',
        name: 'tales',
        component: TalesView
        },
        {path: '/customTale',
          name: 'customTale',
          component: CustomTaleView,
          children: [
            {
              path: '',
              name: 'customTaleList',
              component: CustomTaleList,
            },
            {
              path: 'create',
              name: 'customTaleCreate',
              component: CustomTaleCreate,
            },
            {
              path: 'save',
              name: 'customTaleSave',
              component: CustomTaleSave,
              props: true,
              // props: route => ({ imageUrl: route.params.imageUrl, gptMessage: route.params.gptMessage })
            },
            {
              path: ':customTale_id',
              name: 'customTaleDetail',
              component: CustomTaleDetail,
            },
          ]
        },
        {
          path: "/login",
          name: "login",
          component: LoginView
        },
        {
          path: "/regist",
          name: "regist",
          component: RegistView
        },
        {
          path: "/userInfo",
          name: "userInfo",
          component: UserInfoView
        },
        {
          path: "/userInfo/update",
          name: "userInfoUpdate",
          component: UserInfoUpdate
        },
        
      
    ],
});

export default router;
