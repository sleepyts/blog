<script setup>
import {ref, onMounted, onUnmounted} from 'vue'

const url = ref(window.location.pathname.split('/')[1])
</script>

<template>
  <div class="content">
    <div style="padding: 10px;display: flex;flex-direction: row;align-items: center">
      <div style="width: 25%">
        <img src="https://image.tsukiyo.cn/file/a48f9395bd0706e32b8c1.jpg" style="width: 100%;border-radius: 50%">
      </div>
      <div style="margin-left: 1em;display: flex;flex-direction: column;">
        <span style="font-size: 1.5em;font-weight: bold">未月拾叁</span>
        <span>愿岁并谢，与长友兮</span>
      </div>
    </div>
    <div style="padding: 10px;margin-top: 1em;display: flex;flex-direction: column;">
      <span>公告</span>
      <span>目前网站处于测试阶段，功能还在完善中，请多多指教。</span>

    </div>
    <div style="padding: 10px">
      <span>最近更新</span>
    </div>
  </div>

</template>

<style scoped>
.content {
  width: 17%;
  display: flex;
  flex-direction: column;
  position: fixed;
  top: 5em;
  right: 2%;
}
</style>