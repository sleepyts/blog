<script setup>
import { onMounted, ref,nextTick} from "vue";
import store from "@/store/index.js";
import 'md-editor-v3/lib/preview.css';
import { MdPreview } from "md-editor-v3";
import { getAboutData } from "@/api/about";
import MusicPlayer from "@/components/music-player.vue";

const text=ref("")
const meting=ref(null)
const isReady=ref(false)
let songId= ""
const settings=ref({
  aboutMe:"",
  aboutMePageSongId:""
})
onMounted(async () => {
  new Promise((resolve) => {
    setTimeout(() => {
      settings.value=store().state.siteSettings;
      isReady.value=true;
      resolve();
    }, 100); // 模拟2秒的延迟
  });

})
</script>

<template>
  <div class="container page-main-container">
    <div class="about-me"
         style="animation:blogCardsSlideInFromBottom 0.8s ease; background-color:var(--colorBgContainer)">
      <div style="padding: 20px">
<!--        <meting-js server="netease" type="song" id="1438518506"></meting-js>-->
        <music-player v-if="isReady" :id="settings.aboutMePageSongId" class="music-player"></music-player>
      </div>
      <MdPreview :modelValue=settings.aboutMe :preview-theme="'vuepress'"/>
    </div>
  </div>

</template>

<style scoped>
@media (max-width: 768px) {
  .container {
    margin-top: 40px;
    border-radius: 10px;
  }
}
@media  (min-width: 768px) {
  .container {
    margin-top: 40px;
    border-radius: 10px;
  }
}
.about-me {
  border: 1px solid var(--border-color);
  border-radius: 3px;
  box-shadow: var(--card-border-shadow);
}
.info{
}
.info:hover .dynamic-line {
  background-size: 100% 2px;
}

.icons *{
  margin-right: 10px;
}
.music-player *{
  font-size: 14px;
}
</style>