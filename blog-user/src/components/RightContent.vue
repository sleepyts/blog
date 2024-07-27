<script setup>
import { ref, onMounted, computed } from 'vue'
import store from '../store'
import { BarsOutlined, NotificationOutlined } from '@ant-design/icons-vue'
import router from "@/router/index.js";
import { MdCatalog, MdPreview } from "md-editor-v3";

const scrollElement = document.documentElement;
const handleClick = async (id) => {
  await store().commit('SET_BLOG', id)
  await router.push('/Blog/' + id)
}
const randomBlogs = computed(() => {
  return store().state.randomBlog
})
const recentMoments = computed(() => {
  return store().state.recentMoment
})
const runTime = ref({
  days: 0,
  hours: 0,
  minutes: 0,
  seconds: 0
})
const startTime = new Date(2024, 5, 14, 16, 2, 32, 0)
const showTOC = () => {
  return router.currentRoute.value.name === 'BlogDetail'
}
const showRandomBlogsAndRecent = () => {
  return router.currentRoute.value.name !== 'BlogDetail'
}
const updateCurrentTime = () => {
  const now = new Date().getTime()
  const diff = now - startTime
  const days = Math.floor(diff / (1000 * 60 * 60 * 24))
  const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))
  const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60))
  const seconds = Math.floor((diff % (1000 * 60)) / 1000)

  runTime.value = {
    days,
    hours,
    minutes,
    seconds
  }
}
onMounted(() => {
  const now = new Date().getTime()
  const diff = now - startTime
  const days = Math.floor(diff / (1000 * 60 * 60 * 24))
  const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))
  const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60))
  const seconds = Math.floor((diff % (1000 * 60)) / 1000)

  runTime.value = {
    days,
    hours,
    minutes,
    seconds
  }
  setInterval(updateCurrentTime, 1000)
})

</script>

<template>
  <div v-if="router.currentRoute.value.name !== 'Home'" class="content"
    :style="{ position: showTOC() ? 'fixed' : 'absolute' }">
    <div class="mt-5 mobile-hidden">
      <div style="padding: 10px;">
        <NotificationOutlined />
        <span style="margin-left: 10px;">公告</span>
      </div>
      <div class="line-break"></div>
      <div style="padding: 10px;">
        <span>{{store().state.siteSettings.announcement }}</span>
      </div>
    </div>
    <div v-if="showTOC()" class="toc mt-5 mobile-hidden ">
      <div style="padding: 10px;">
        <BarsOutlined />
        <span style="margin-left: 10px;">Table of Content</span>
      </div>
      <div class="line-break"></div>
      <div style="padding: 10px;">
        <MdCatalog editorId="preview-only" :scrollElement="scrollElement" :scrollElementOffsetTop=90 :offsetTop="200"
          class="catalog"></MdCatalog>
      </div>
    </div>
    <div v-if="showRandomBlogsAndRecent()" class="mt-5 mobile-hidden">
      <div style="padding: 10px;">
        <BarsOutlined />
        <span style="margin-left: 10px;">随机文章</span>
      </div>
      <div class="line-break"></div>
      <div style="padding: 10px;">
        <div v-for="blog in randomBlogs" class="item-blog" @click="handleClick(blog.id)"
          :class="{ active: Number(blog.id) === Number(router.currentRoute.value.params.id) }">
          <span>{{ blog.title }}</span>
        </div>
      </div>
    </div>
    <div v-if="showRandomBlogsAndRecent()" class="mt-5 mobile-visible">
      <div style="padding: 10px;">
        <BarsOutlined />
        <span style="margin-left: 10px;">最近动态</span>
      </div>
      <div class="line-break"></div>
      <div class="timeline">
        <div class="timenode" v-for="moment in recentMoments">
          <div class="header">
            <span>{{ new Date(moment.createTime).toLocaleString() }}</span>
          </div>
          <div class="body">
            <span>{{ moment.content }}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="mt-5 mobile-hidden">
      <div style="padding: 10px;">
        <span>本站已存活：{{ runTime.days }}天{{ runTime.hours }}小时{{ runTime.minutes }}分{{ runTime.seconds }}秒</span>
      </div>
      <div class="line-break"></div>
      <div style="padding: 10px;">
        <span>访客数量：{{ store().state.siteSettings.visitorCount }}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.timeline:before {
  content: '';
  position: absolute;
  z-index: 0;
  background: #ccc;
  width: 4px;
  border-radius: 8px;
  top: 0.5rem;
  bottom: 0;
  left: 4px
}

.content {
  width: 17%;
  display: flex;
  flex-direction: column;
  position: absolute;
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

.item-blog {
  cursor: pointer;
  transition: 0.2s all ease-in-out;
  box-shadow: 0 0 #0000, 0 0 #0000, -0.5rem 0.875rem 2.375rem #272c310f, -0.0625rem 0.1875rem 0.5rem #272c3108;
  ;
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

.header {
  font-weight: 500;
  line-height: 1;
  display: flex;
  align-items: center;
  position: relative;
  font-size: 10px;
  padding: 10px;
  box-sizing: border-box;
  max-width: 100%;
  margin-left: 4px;
}

.header :before {
  content: '';
  position: absolute;
  left: 0;
  width: 4px;
  border-radius: 12px;
  height: 4px;
  top: calc(50% - 0.5 * 4px);
  background: #ccc;
  transition: background 0.2s ease-out, height 0.2s ease-out, top 0.2s ease-out, transform 0.2s ease-out;
  -moz-transition: background 0.2s ease-out, height 0.2s ease-out, top 0.2s ease-out, transform 0.2s ease-out;
  -webkit-transition: background 0.2s ease-out, height 0.2s ease-out, top 0.2s ease-out, transform 0.2s ease-out;
  -o-transition: background 0.2s ease-out, height 0.2s ease-out, top 0.2s ease-out, transform 0.2s ease-out;
  transform: scale(2);
}

.timenode:hover .header :before {
  background: red;
  height: 20px;
  top: calc(50% - 0.5 * 20px);
  transform: scale(1);
}

.timenode {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  box-sizing: border-box;
  max-width: 100%;
}

.body {
  min-width: 100%;
  left: -5px;
  display: block;
  line-height: 1.25;
  background-color: #ffffff;
  box-shadow: 0 0 #0000, 0 0 #0000, -0.5rem 0.875rem 2.375rem #272c310f, -0.0625rem 0.1875rem 0.5rem #272c3108;
  padding: 10px;
}
</style>