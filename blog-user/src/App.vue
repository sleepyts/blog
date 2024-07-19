<template>
  <div class="app">
    <a-config-provider
        :theme="{
          token: {
            colorPrimary: '#439b79',
          },

        }"
    >

      <loading class="loading"></loading>
      <router-view class="content"/>
      <RightContent></RightContent>

    </a-config-provider>
    <!--    <right-content class="right-content"></right-content>-->
  </div>
  <a-back-top :visibility-height=100></a-back-top>
  <div class="line"></div>
  <a-layout-footer id="footer">
    <br>
    <a href="http://beian.miit.gov.cn/" target="_blank" class="link" style="font-size: 20px;">{{ ICP }}</a>
    <br>
    <br>
  </a-layout-footer>

</template>

<script setup>
import Loading from "@/components/Loading.vue";
import {onMounted, computed} from "vue";
import store from "@/store";
import {getSettings} from "@/api/settings.js";
import RightContent from "@/components/RightContent.vue";
import {getRandomBlog} from "@/api/blog.js";
import {getRecentMoment} from "@/api/Moment.js";

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
})
</script>

<style>
.app {
  min-height: 100vh;
}

.content {
  flex: 1;
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

.right-content {
  position: fixed;
  right: 5%;
  top: 110px
}
</style>
