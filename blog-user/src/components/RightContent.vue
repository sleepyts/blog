<script setup>
import {watch, onMounted , computed} from 'vue'
import store from '../store'
import {BarsOutlined} from '@ant-design/icons-vue'
import router from "@/router/index.js";
import {MdCatalog} from "md-editor-v3";
const scrollElement = document.documentElement;
const handleClick= async (id) => {
  await store().commit('SET_BLOG', id)
  await router.push('/Blog/'+ id)
}
const randomBlogs = computed(() => {
  return store().state.randomBlog
})
const showTOC= () =>{
  return router.currentRoute.value.name === 'BlogDetail'
}
const showRandomBlogsAndRecent = () =>{
  return router.currentRoute.value.name !== 'BlogDetail'
}
</script>

<template>
  <div v-if="router.currentRoute.value.name !== 'Home'" class="content">
    <div  v-if="showTOC()" class="toc mt-5 mobile-hidden ">
      <div style="padding: 10px;">
        <BarsOutlined/>
        <span style="margin-left: 10px;">Table of Content</span>
      </div>
      <div class="line-break"></div>
      <div style="padding: 10px;">
        <MdCatalog editorId="preview-only" :scrollElement="scrollElement"
                   :scrollElementOffsetTop=90
                   :offsetTop="200"
                   class="catalog"
        ></MdCatalog>
      </div>
    </div>
    <div v-if="showRandomBlogsAndRecent()" class="mt-5 mobile-hidden">
      <div style="padding: 10px;">
        <BarsOutlined/>
        <span style="margin-left: 10px;">随机文章</span>
      </div>
      <div class="line-break"></div>
      <div style="padding: 10px;">
        <div v-for="blog in randomBlogs"  class="item-blog" @click="handleClick(blog.id)" :class="{active: Number(blog.id) === Number(router.currentRoute.value.params.id)}">
          <span >{{blog.title}}</span>
        </div>
      </div>
    </div>
<!--    <div v-if="showRandomBlogsAndRecent()" class="mt-5 mobile-visible">-->
<!--      <div style="padding: 10px;">-->
<!--        <BarsOutlined/>-->
<!--        <span style="margin-left: 10px;">最近动态</span>-->
<!--      </div>-->
<!--      <div class="line-break"></div>-->
<!--      <div style="padding: 10px;">-->

<!--      </div>-->
<!--    </div>-->
  </div>
</template>

<style scoped>
.content {
  width: 17%;
  display: flex;
  flex-direction: column;
  position: fixed;
  top: 10%;
  right: 2%;
}
.line-break {
  height: 1px;
  border-bottom: 1px solid #ccc;
}

.mt-5 {
  padding: 0;
}
.item-blog{
  cursor: pointer;
  transition: 0.2s all ease-in-out;
  box-shadow: 0 0 #0000,0 0 #0000,-0.5rem 0.875rem 2.375rem #272c310f,-0.0625rem 0.1875rem 0.5rem #272c3108;;
  padding: 10px;
  border-radius: 5px;
}
.item-blog:hover {
  scale: 0.98;
  background-color: var(--colorBgContainer);
  color: red;
  box-shadow: 0 0 #0000;
}
.item-blog.active {
  background-color: var(--colorBgContainer);
  color: red;
  box-shadow: 0 0 #0000;
}
</style>