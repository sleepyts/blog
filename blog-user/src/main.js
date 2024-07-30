import "./assets/main.css";
import "./assets/theam.css";
import "./assets/myCss.css";
import { createApp } from "vue"; // 使用 Vue 3 的 createApp 方法
import App from "./App.vue";
import router from "./router/index.js";
import store from "@/store/index.js";
import Antd from "ant-design-vue";
import Icon from "@ant-design/icons-vue/lib/components/AntdIcon";
import "ant-design-vue/dist/reset.css";
import "vue3-cute-component/dist/style.css";

const app = createApp(App);
// 注册组件、插件、路由、状态管理等

app.use(router);
app.use(store);
app.use(Antd);
// 挂载 Vue 实例到 DOM 中
app.mount("#app");
