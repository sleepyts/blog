<template>
  <div id="main-app">
    <a-config-provider :theme="{
      token: {
        colorPrimary: '#439b79',
      },

    }">

      <loading class="loading"></loading>
      <router-view />
      <RightContent></RightContent>

    </a-config-provider>
  </div>
  <a-float-button-group shape="circle" :style="{ right: '24px' }">
    <a-back-top :visibility-height=100></a-back-top>
    <a-float-button @click="handleShare">
      <template #icon>
        <ShareAltOutlined />
      </template>
    </a-float-button>
    <a-float-button v-if="router.currentRoute.value.name === 'BlogDetail'" @click="scorllToComment">
      <template #icon>
        <CommentOutlined />
      </template>
    </a-float-button>

  </a-float-button-group>
  <div class="line"></div>
  <a-layout-footer id="footer">
    <br>
    <a href="http://beian.miit.gov.cn/" target="_blank" class="link" style="font-size: 20px;">{{ ICP }}</a>
    <br>
    <br>
  </a-layout-footer>

</template>

<script setup>
import { getRandomBlog } from "@/api/blog.js";
import { getRecentMoment } from "@/api/Moment.js";
import { getSettings, getVisitorCount } from "@/api/settings.js";
import Loading from "@/components/Loading.vue";
import RightContent from "@/components/RightContent.vue";
import store from "@/store";
import { computed, onMounted } from "vue";
import { ShareAltOutlined, CommentOutlined } from "@ant-design/icons-vue";
import { message } from "ant-design-vue";
import router from "@/router/index.js";
const ICP = computed(() => store().state.siteSettings.icp)
onMounted(() => {

  getSettings().then(res => {
    if (res.data.code === 200) {
      store().commit('SET_SETTINGS', res.data.data)
    }
  })
  getRandomBlog().then(res => {
    if (res.data.code === 200) {
      store().commit('SET_RANDOM_BLOG', res.data.data)
    }
  })
  getRecentMoment().then(res => {
    if (res.data.code === 200) {
      store().commit('SET_RECENT_MOMENT', res.data.data)
    }
  })
  getVisitorCount().then(res => {
    if (res.data.code === 200) {
      store().state.siteSettings.visitorCount = res.data.data
    }
  })
})
const handleShare = () => {
  const url = window.location.href;
  navigator.clipboard.writeText(url).then(() => {
    message.success("链接已复制到剪贴板");
  }).catch(() => {
  });
}
const scorllToComment = () => {
  window.scrollTo({
    top: document.getElementsByClassName("comments")[0].offsetTop - 100,
    behavior: "smooth"
  });
}
</script>

<style>
.app {
  min-height: 100vh
}

#footer {
  max-height: 10vh;
  text-align: center;
  margin-top: auto;
  font: 14px/1.5 "Microsoft YaHei", Arial, sans-serif;
}

.loading {
  position: sticky;

  top: 0;
}

.right-content {
  position: fixed;
  right: 5%;
  top: 110px
}
</style>
