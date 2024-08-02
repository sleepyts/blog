<script setup>
import { ref, onMounted, computed } from 'vue'
import store from '../store'
import { BarsOutlined, GithubOutlined, NotificationOutlined, MailOutlined } from '@ant-design/icons-vue'
import router from "@/router/index.js";
import { MdCatalog } from "md-editor-v3";


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
const showSiteInfo = () => {
  return router.currentRoute.value.name !== 'BlogDetail'
}
const showAnnouncement = () => {
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
  <div v-if="router.currentRoute.value.name !== 'Home' && router.currentRoute.value.name !== 'RSS'"
    class="content mobile-hidden" :style="{ position: showTOC() ? 'fixed' : 'absolute' }">
    <div v-if="showAnnouncement()" class="mt-5 mobile-hidden">
      <div style="padding: 10px;">
        <NotificationOutlined />
        <span style="margin-left: 10px;">公告</span>
      </div>
      <div class="line-break"></div>
      <div style="padding: 10px;">
        <span>{{ store().state.siteSettings.announcement }}</span>
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
    <div v-if="showRandomBlogsAndRecent()" class="mt-5 mobile-hidden">
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
    <div v-if="showSiteInfo()" class="mt-5 mobile-hidden">
      <div style="padding: 10px;">
        <span>运行时间：{{ runTime.days }}天{{ runTime.hours }}小时{{ runTime.minutes }}分{{ runTime.seconds }}秒</span>
      </div>
      <div class="line-break"></div>
      <div style="padding: 10px;">
        <span>访客数量：{{ store().state.siteSettings.visitorCount }}</span>
      </div>
      <div class="line-break"></div>
      <div style="padding: 10px; display: flex;flex-direction: row;">
        <a-button shape="circle">
          <svg t="1722311823863" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
            p-id="5868" width="20" height="20">
            <path
              d="M511.6 76.3C264.3 76.2 64 276.4 64 523.5 64 718.9 189.3 885 363.8 946c23.5 5.9 19.9-10.8 19.9-22.2v-77.5c-135.7 15.9-141.2-73.9-150.3-88.9C215 726 171.5 718 184.5 703c30.9-15.9 62.4 4 98.9 57.9 26.4 39.1 77.9 32.5 104 26 5.7-23.5 17.9-44.5 34.7-60.8-140.6-25.2-199.2-111-199.2-213 0-49.5 16.3-95 48.3-131.7-20.4-60.5 1.9-112.3 4.9-120 58.1-5.2 118.5 41.6 123.2 45.3 33-8.9 70.7-13.6 112.9-13.6 42.4 0 80.2 4.9 113.5 13.9 11.3-8.6 67.3-48.8 121.3-43.9 2.9 7.7 24.7 58.3 5.5 118 32.4 36.8 48.9 82.7 48.9 132.3 0 102.2-59 188.1-200 212.9 23.5 23.2 38.1 55.4 38.1 91v112.5c0.8 9 0 17.9 15 17.9 177.1-59.7 304.6-227 304.6-424.1 0-247.2-200.4-447.3-447.5-447.3z"
              p-id="5869"></path>
          </svg>
        </a-button>
        <a-button shape="circle" style="margin-left: 10px;">
          <svg t="1722253746767" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
            p-id="5193" width="20" height="20">
            <path
              d="M444.629333 485.333333c-12.544 43.861333 0.512 88.064 33.450667 113.194667 45.269333 34.56 109.44 12.8 122.624-42.453333 3.413333-14.378667 4.394667-30.805333 1.152-45.056-9.813333-42.709333-22.186667-84.821333-33.792-127.829334-56.746667 6.570667-108.501333 50.005333-123.434667 102.144z m236.714667-12.245333c11.648 43.178667 12.16 86.058667-5.418667 128-48.128 114.773333-201.429333 133.973333-280.448 35.242667-55.552-69.418667-54.613333-169.002667 2.56-244.650667 33.28-44.032 76.970667-72.832 130.048-87.637333l16.170667-4.437334c-3.584-17.706667-8.021333-34.816-10.368-52.224-7.509333-56.192 21.845333-106.794667 74.410667-129.706666 52.309333-22.826667 115.541333-9.216 150.613333 32.426666 17.322667 20.437333 16.853333 46.08-1.066667 62.464-17.578667 16.085333-42.496 14.762667-61.226666-3.84-10.538667-10.496-21.76-18.773333-37.418667-18.602666-22.4 0.256-42.112 17.834667-40.32 39.978666 1.578667 19.968 7.381333 39.68 12.8 59.136 0.938667 3.328 9.216 5.76 14.421333 6.528 56.917333 8.405333 106.837333 31.189333 148.138667 71.509334 109.141333 106.368 122.069333 278.656 28.672 402.773333-65.237333 86.698667-154.026667 135.168-261.418667 145.450667-197.162667 18.773333-369.664-107.946667-413.866666-301.141334-40.32-176.810667 58.88-365.568 227.626666-433.024 25.002667-9.984 48.768-1.322667 58.496 21.248 9.898667 22.912-0.810667 46.336-26.026666 57.6-101.034667 45.226667-162.858667 121.813333-179.84 231.424-22.741333 146.474667 70.656 289.109333 213.333333 329.386667 116.181333 32.853333 241.408-7.082667 311.808-99.413333 67.669333-88.746667 59.733333-217.557333-18.218667-293.248a169.813333 169.813333 0 0 0-77.781333-43.221334c8.448 30.549333 16.597333 59.221333 24.32 87.978667z"
              fill="#09121F" p-id="5194"></path>
          </svg>
        </a-button>
        <a-button shape="circle" style="margin-left: 10px;">
          <svg t="1722254075034" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
            p-id="7095" width="20" height="20">
            <path
              d="M306.005333 117.632L444.330667 256h135.296l138.368-138.325333a42.666667 42.666667 0 1 1 60.373333 60.373333l-78.037333 77.952L789.333333 256A149.333333 149.333333 0 0 1 938.666667 405.333333v341.333334a149.333333 149.333333 0 0 1-149.333334 149.333333h-554.666666A149.333333 149.333333 0 0 1 85.333333 746.666667v-341.333334A149.333333 149.333333 0 0 1 234.666667 256h88.96L245.632 177.962667a42.666667 42.666667 0 0 1 60.373333-60.373334zM789.333333 341.333333h-554.666666a64 64 0 0 0-63.701334 57.856L170.666667 405.333333v341.333334a64 64 0 0 0 57.856 63.701333L234.666667 810.666667h554.666666a64 64 0 0 0 63.701334-57.813334L853.333333 746.666667v-341.333334A64 64 0 0 0 789.333333 341.333333zM341.333333 469.333333a42.666667 42.666667 0 0 1 42.666667 42.666667v85.333333a42.666667 42.666667 0 1 1-85.333333 0v-85.333333a42.666667 42.666667 0 0 1 42.666666-42.666667z m341.333334 0a42.666667 42.666667 0 0 1 42.666666 42.666667v85.333333a42.666667 42.666667 0 1 1-85.333333 0v-85.333333a42.666667 42.666667 0 0 1 42.666667-42.666667z"
              p-id="7096"></path>
          </svg>
        </a-button>
        <a-button shape="circle" style="margin-left: 10px;">
          <svg t="1722311852323" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
            p-id="6869" width="20" height="20">
            <path
              d="M928 160H96c-17.7 0-32 14.3-32 32v640c0 17.7 14.3 32 32 32h832c17.7 0 32-14.3 32-32V192c0-17.7-14.3-32-32-32z m-40 110.8V792H136V270.8l-27.6-21.5 39.3-50.5 42.8 33.3h643.1l42.8-33.3 39.3 50.5-27.7 21.5z"
              fill="#010101" p-id="6870"></path>
            <path
              d="M833.6 232L512 482 190.4 232l-42.8-33.3-39.3 50.5 27.6 21.5 341.6 265.6c20.2 15.7 48.5 15.7 68.7 0L888 270.8l27.6-21.5-39.3-50.5-42.7 33.2z"
              p-id="6871"></path>
          </svg>
        </a-button>
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
  background: green;
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