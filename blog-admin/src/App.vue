<template>
  <a-config-provider :locale="zhCN">
    <div class="app">
      <router-view class="content"></router-view>
    </div>
    <a-back-top :visibility-height=100></a-back-top>
    <div class="line"></div>
    <a-layout-footer id="footer">
      <br>
      <a href="http://beian.miit.gov.cn/" target="_blank" class="link" style="font-size: 20px;">鄂ICP备2024059759</a>
      <br>
      <br>
      @ Tsukiyo 2024
      <br>
      <br>
    </a-layout-footer>
  </a-config-provider>
</template>

<script setup>
import zhCN from 'ant-design-vue/es/locale/zh_CN';
</script>

<style>
.app {
  min-height: 100vh;
}

.content {
  flex: 1;
  transition: all 0.2s ease-in-out;
}

@keyframes animating-in {
  from {
    opacity: 0;
    transform: translateX(100px);
  }

  to {
    opacity: 1;
    transform: translateX(0);
  }
}

#footer {
  text-align: center;
  margin-top: auto;
  font: 14px/1.5 "Helvetica Neue", Arial, sans-serif;
}

.loading {
  position: sticky;

  top: 0;
}

</style>
