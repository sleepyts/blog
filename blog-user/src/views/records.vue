<template>
  <div class="app">
    <blog-header></blog-header>
    <Title title="Records"></Title>
    <div class="mt-5 cont page-main-container">
      <card-title text="文章归档"></card-title>
      <span>共计 {{total}} 篇文章</span>
      <Timeline style="margin-top: 2em;">
        <div v-for="recordList in records" :key="recordList.year" >
          <timeline-title style="font-size: 2em;">{{recordList.year}}</timeline-title>
          <timeline-item v-for="record in recordList.recordList" :key="record.id" :date="record.date" class="timeline-item" >
            <span style=" font-weight: 500;">{{ record.date.slice(5) }}
            </span>
            <a class="link" @click="[router.push('/Blog/'+record.blogId),store().commit('SET_BLOG', record.blogId)]" style="font-weight: 500;margin-left: 10px;font-size: 1.2em;  ">{{ record.title }}</a>
          </timeline-item>
        </div>
      </Timeline>
    </div>
  </div>
</template>
<script setup>
import {ref, onMounted} from "vue";
import Title from "@/components/Common/Title.vue";
import CardTitle from "@/components/Common/CardTitle.vue";
import getRecords from "@/api/record.js";
import store from "@/store/index.js";
import { Timeline, TimelineItem,TimelineTitle } from 'vue3-cute-component'
import router from "@/router/index.js";


const records = ref([]);
const total=ref(0);
onMounted(async () => {
  await store().dispatch('getBlogList');
  await getRecords().then(res => {
    // console.log(res.data.data);
    records.value = res.data.data;
  });
  for(let i=0;i<records.value.length;i++){
    total.value+=records.value[i].recordList.length;
  }
});

</script>

<style scoped>
@media (max-width: 768px) {
   .cont {
    margin-top: 50px;
    padding: 20px;
  }
  .timeline-item {
    width: 100%;
  }
}
@media (min-width: 768px) {
  .cont {
    margin-top: 50px;
    padding: 20px;
  }
  .timeline-item {
    width: 45%;
  }
}
</style>