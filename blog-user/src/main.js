import './assets/main.css'
import './assets/theam.css'
import {createApp} from 'vue'; // 使用 Vue 3 的 createApp 方法
import App from './App.vue';
import router from './router/index.js';
import store from '@/store/index.js';
import BlogCard from "@/components/Blog/BlogCard.vue";
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import 'vue3-cute-component/dist/style.css'


const app = createApp(App);
// 注册组件、插件、路由、状态管理等

app.component('blog-card', BlogCard);
app.use(router);
app.use(store);
app.use(Antd);
// 挂载 Vue 实例到 DOM 中
app.mount('#app');
