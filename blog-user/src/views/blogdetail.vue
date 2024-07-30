<script setup>
import { onMounted } from "vue";
import store from "@/store/index.js";
import BlogHeader from "@/components/BlogHeader.vue";
import Detail from "@/components/Blog/Detail.vue";
import Comments from "@/components/Comment/Comments.vue";
import Guest from "@/components/Comment/Guest.vue";
onMounted(() => {
  store().dispatch("getVerify")
});
</script>
<template>
  <div class="app">
    <blog-header></blog-header>
    <div class="mt-5 cont page-main-container">
      <Detail></Detail>

    </div>
    <Guest :blog-id=Number(store().state.blogId)></Guest>
    <Comments :blog-id=Number(store().state.blogId) class="mt-5"></Comments>
  </div>
</template>
<style scoped>
@media (max-width: 768px) {
  .cont {
    margin-top: 5%;
    background-color: var(--colorBgContainer)
  }
}

.cont {
  animation: slid 0.2s ease-in-out;
}

@keyframes slid {
  from {
    transform: translateY(0%);
    opacity: 0;
  }

  to {
    transform: translateY(0);
    opacity: 1;
  }
}

@media (min-width: 768px) {
  .cont {
    margin-top: 5%;
    background-color: var(--colorBgContainer)
  }
}
</style>