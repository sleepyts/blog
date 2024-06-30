<script setup>
import Title from "@/components/Common/Title.vue";
import BlogHeader from "@/components/Common/BlogHeader.vue";
import Guest from "@/components/Comment/Guest.vue";
import Comments from "@/components/Comment/Comments.vue";
import { onBeforeMount} from "vue";
import store from "@/store/index.js";
onBeforeMount(async () => {
  await store().dispatch("getVerify")
  await store().commit("SET_BLOG", -1);
})
</script>

<template>
  <div class="app">
    <blog-header ></blog-header>
    <Title title="Guestbook" ></Title>
    <Guest :blog-id="-1" ></Guest>
    <Comments :blog-id="-1"  class="mt-5" ></Comments>
  </div>
</template>
