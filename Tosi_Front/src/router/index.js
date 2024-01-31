import { createRouter, createWebHistory } from "vue-router";
import TalesView from '@/views/TalesView.vue'
import HomeView from '@/views/HomeView.vue'
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
    ],
});

export default router;
