<script setup>

import {useRouter} from 'vue-router'

const router = useRouter()

function changePage(page){
  router.push({ name: page })
}
</script>

<template>
  <div class="home-container page-main-container" >
    <div class="left-container" id="left-container" >
      <span id="first" style=" animation: slideInFromBottom 0.5s ease-in-out; transition: all 0.2s ease-in-out;">Hi!👋<br> Welcome to my blog</span>
      <span id="second"  style="animation: slideInFromBottom 0.8s ease-in-out; transition: all 0.8s ease-in-out; margin-top: 0.3em;">I'm A ⌨Developer and 🎮Gamer</span>
      <div class="buttons">
        <button id="btn" style="animation: slideInFromBottom 1s ease-in-out;" class="left-container-btn" @click="changePage('Blog')">Read More</button>
        <button id="btn" style="animation: slideInFromBottom 1s ease-in-out; margin-left: 10px;" class="left-container-btn" @click="changePage('About')">About Me</button>
      </div>

    </div>
<!--    <img src="../../../public/homeImg.jpg" width="300em" alt="Responsive image" class="rounded-img mobile-hidden " >-->
  </div>
</template>

<style scoped>
.home-container {
  display: flex;
  position: relative;
}
@media (max-width: 768px) {
  .left-container #first{
     font-size: 1.3em;
  }

  .left-container #second{
     font-size: 1em;
  }
  .buttons #btn{
     font-size: 1em;
  }
}
@media (min-width: 768px) {
  .left-container #first{
    font-size: 2em;
  }
  .left-container #second{
    font-size: 1.5em;
  }
  .buttons #btn{
    font-size: 1.2em;
  }

}
.left-container{
  position: relative;
  margin-top: 3em;
  width: 100%;
  font-size: 2em;
  display: flex;
  flex-direction: column;
}
.left-container-btn{
  padding: 0.5em 1em;
  font-size: 0.6em;
  cursor: pointer;
  margin-top: 1em;
  border-radius: 5px;
  transition: transform 0.1s ease-in-out;
  background-color: var(--colorBgContainer);
  color: var(--text-color);
  border:1px solid var(--border-color);
  width:8em

}
.left-container-btn:hover{
  transform: scale(1.03);
  box-shadow: var(--card-border-shadow) /* 设置阴影样式 */
}
.left-container-btn:active{
  transform: scale(0.95);
}
</style>