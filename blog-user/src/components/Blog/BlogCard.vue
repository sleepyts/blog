<script setup>
import '../../assets/theam.css'
import {defineProps, ref} from 'vue';
import router from "@/router/index.js";
import store from "@/store/index.js";
import {CalendarOutlined, CommentOutlined} from '@ant-design/icons-vue';
const props = defineProps({
  blog: {
    type: Object,
    required: true
  }
});

</script>

<template>
  <div class="blog-card" @click="[router.push('/Blog/'+blog.id),store().commit('SET_BLOG', props.blog.id)]">
    <div v-if="blog.img&&blog.img.length>0" class="blog-img-container">
      <img style=" width: 100%;border-radius: 5px;" v-if="blog.img&&blog.img.length>0" :src="blog.img"  class="blog-img"  alt="blog-img"/>
    </div>
    <div class="info" style="display: flex;flex-direction: column;width: 100%;">
      <span style="font-size: 1.5em;
      margin-bottom:0.4em;
      font-weight: 500;
      "
      >{{blog.title}}</span>
      <span v-if="blog.description&&blog.description.length>0" style="font-size: 1em;word-break: break-all;margin-bottom: 0.4em;font-weight: 400;margin-right: 10px;color: var(--second-text-color);">{{blog.description}}</span>
      <div style="margin-top:auto;display: flex; flex-direction: row; ">

          <span style="font-size: 0.7em;color: var(--second-text-color);">{{blog.date}}</span>
          <span style="font-size: 0.7em;margin-left: 10px;color: var(--second-text-color);">{{blog.categoryName}}</span>
        <div style="margin-left: auto;margin-right: 10px;">
          <span style="margin-right:5px">{{blog.commentNum}}</span>
          <CommentOutlined />
        </div>
      </div>
    </div>
  </div>

</template>

<style scoped>
.info {
  margin-left: 10px;
}

@media (max-width: 768px) {
  .blog-card {
    display: flex;flex-direction: column
  }
  .blog-img-container{
    width: 100%;
    margin-bottom: 10px;
  }
}
@media (min-width: 768px) {
  .blog-card {
    display: flex;flex-direction: row;
  }
  .blog-img-container{
    width: 40%;
  }
}
</style>