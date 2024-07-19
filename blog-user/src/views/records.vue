<template>
  <div class="app">
    <blog-header></blog-header>
    <Title title="Records"></Title>
    <div class="mt-5 cont page-main-container">
      <card-title text="文章归档"></card-title>
      <BarChartOutlined />
      <span>  共计 {{total}} 篇文章</span>
      <Timeline style="margin-top: 2em;">
        <div v-for="recordList in records" :key="recordList.year" >
          <timeline-title style="font-size: 2em;">{{recordList.year}}</timeline-title>
          <timeline-item v-for="record in recordList.recordList" :key="record.id" :date="record.date" class="timeline-item" >
            <div>
              {{ record.date.slice(5) }}
            </div>
            <div class="link-item">
              <a @click="[router.push('/Blog/'+record.blogId),store().commit('SET_BLOG', record.blogId)]" style="font-weight: 500;font-size: 1.2em;  ">{{ record.title }}</a>
            </div>
          </timeline-item>
        </div>
      </Timeline>
    </div>
  </div>
</template>
<script setup>
import {ref, onMounted} from "vue";
import Title from "@/components/Title.vue";
import CardTitle from "@/components/CardTitle.vue";
import getRecords from "@/api/record.js";
import store from "@/store/index.js";
import { Timeline, TimelineItem,TimelineTitle } from 'vue3-cute-component'
import router from "@/router/index.js";
import {BarChartOutlined} from "@ant-design/icons-vue"

const records = ref([]);
const total=ref(0);
onMounted(async () => {
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
  }
  .timeline-item {
    width: 100%;
  }
}
@media (min-width: 768px) {
  .cont {
    margin-top: 50px;
  }
  .timeline-item {
    width: 45%;
  }
}
.timeline-item{
  display: flex;
  flex-direction: row;
  align-items: center;
}
.link-item{
  margin-left: 10px;
  cursor: pointer;
  transition: 0.2s all ease-in-out;
  box-shadow: 0 0 #0000,0 0 #0000,-0.5rem 0.875rem 2.375rem #272c310f,-0.0625rem 0.1875rem 0.5rem #272c3108;;
  padding: 0.5em;
  display: inline-block;
  border-radius: 5px;
}
.link-item:hover{
  scale: 0.96;
  background-color: var(--colorBgContainer);
  color: red;
  box-shadow: 0 0 #0000;
  display: inline-block;
}
.link-item:active{
  scale: 1.00
}
</style>