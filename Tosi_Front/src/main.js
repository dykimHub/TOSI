import { createApp } from 'vue';
import { createPinia } from 'pinia';

import App from './App.vue';
import router from './router';
import axios from "@/util/http-common";

import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const app = createApp(App);
app.config.globalProperties.$axios = axios;

app.use(createPinia());
app.use(router);

app.mount('#app');
