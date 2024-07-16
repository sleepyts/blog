<script setup>

import Title from "@/components/Title.vue";
import BlogHeader from "@/components/BlogHeader.vue";
import FriendCard from "@/components/Friends/FriendCard.vue";
import {ref,onMounted} from "vue";
import CardTitle from "@/components/CardTitle.vue";
import {getFriendList} from "@/api/friend.js";
import state from "../store/state.js";

const friends = ref([]);
onMounted(()=>{
  getFriendList().then(res => {
    if(res.data.code===200) friends.value=res.data.data;
  })
})
</script>

<template>
  <div class="app">
    <blog-header></blog-header>
    <Title title="Links"></Title>
    <div class="mt-5 page-main-container">
      <CardTitle text="友链"></CardTitle>
      <div v-if="friends.length === 0" class="flex center">
        <span style="font-size: 2em">No links yet :(</span>
      </div>
      <div class="friends-container">
        <friend-card v-for="friend in friends" :friend="friend" :key="friend.name"></friend-card>
      </div>

      <div class="padding-10">
        <span style="font-weight: 600">友链格式:<br></span>
        <div style="line-height: 2em;margin-left:2em;margin-top:1em">
          <ul >
            <li>{{'名称：' + state.siteSettings.indexName}}</li>
            <li>网站：<a :href=state.siteSettings.indexUrl class="link"> {{state.siteSettings.indexUrl}}</a></li>
            <li>{{'简介：' + state.siteSettings.description}}</li>
            <li>{{'Logo：' + state.siteSettings.logoUrl}}</li>
            <li>交换友链请前往留言板：<a href="/Guestbook" class="link">留言板</a></li>
          </ul>
        </div>

      </div>
    </div>
  </div>
</template>

<style scoped>
.mt-5 {
  margin-top: 40px;
}

.friends-container {
  display: flex;
  width: 100%;
  flex-wrap: wrap;
}

</style>