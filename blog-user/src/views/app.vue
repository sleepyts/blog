<script setup>
import {ref,onMounted} from "vue";
import BlogHeader from "@/components/Common/BlogHeader.vue";
import Title from "@/components/Common/Title.vue";
import Appcard from "@/components/Apps/Appcard.vue";
import 'md-editor-v3/lib/preview.css';
import {getApps} from "@/api/app.js";
const apps=ref([]);
onMounted(() => {
    getApps().then((res) => {
      if(res.data.code===200) apps.value=res.data.data;
      else console.log(res.data.message);
    });

})
</script>
<template>
  <div class="app">
    <blog-header></blog-header>
    <Title title="Apps"></Title>
    <div class="apps page-main-container" >
      <appcard  v-for="(app, index) in apps" :key="index" :app="app" class="app-card "></appcard>
    </div>
  </div>
</template>

<style scoped>
@media (max-width: 768px) {
  .apps {
     width: 90%;
     margin-left: 5%;
     margin-top: 50px;
     display: flex;
     flex-wrap: wrap; /* 设置 flex-wrap 属性为 wrap */
  }
  .app-card {
    width: calc(50% - 10px); /* 设置每个 app-card 的宽度为 50%，减去 margin-right */
    margin-bottom: 10px; /* 添加底部间距，使得每行的 app-card 垂直对齐 */
    margin-left: 10px; /* 添加左侧间距 */
    transition: all 0.1s ease-in-out; /* 应用过渡效果 */
    animation: slideInFromBottom 0.4s ease-in-out;
  }
}
@media (min-width: 768px) {
  .apps {
    margin-top: 50px;
    display: flex;
    flex-wrap: wrap; /* 设置 flex-wrap 属性为 wrap */
  }

  .app-card {
    width: calc(33.33% - 10px); /* 设置每个 app-card 的宽度为 33.33%，减去 margin-right */
    margin-bottom: 10px; /* 添加底部间距，使得每行的 app-card 垂直对齐 */
    margin-left: 10px; /* 添加左侧间距 */
    transition: all 0.1s ease-in-out; /* 应用过渡效果 */
    animation: blogCardsSlideInFromBottom 0.4s ease-in-out;
  }
}

.app-card:nth-child(3n+1) {
  margin-left: 0;
}
.app-card:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 应用阴影效果 */
  cursor: url(/public/new-tab.png) 10 10,pointer;; /* 鼠标悬停时显示手型 */
  scale: 1.02; /* 鼠标悬停时放大 5% */
  transition: all 0.1s ease-in-out;
}
.app-card:active {
  transform: scale(0.98); /* 鼠标按下时缩小 2% */
  transition: all 0.1s ease-in-out;
}
</style>

