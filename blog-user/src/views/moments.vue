<script setup>

import { ref } from "vue";
import CardTitle from "@/components/CardTitle.vue";
import Title from "@/components/Title.vue";
import BlogHeader from "@/components/BlogHeader.vue";
import { onMounted } from "vue";
import {getMoment} from "@/api/Moment.js";
import MomentCard from "@/components/MomentCard.vue";

const moments=ref([])
const currentPage=ref(1)
onMounted(async () => {
  currentPage.value=1
  await getMoment(1, 7).then(res => {
    if(res.data.code===200) moments.value=res.data.data
  })
})

const onPageChange = async (page) => {
  await getMoment(page, 7).then(res => {
    if (res.data.code === 200) moments.value = res.data.data
  });
  currentPage.value = page;
  window.scrollTo({ top: 0, behavior: 'smooth' });
}
</script>

<template>

  <div class="app">
    <blog-header></blog-header>
    <Title title="Moments"></Title>
    <div class="mt-5 page-main-container">
      <CardTitle text="动态"></CardTitle>
      <div style="padding: 30px">
        <MomentCard v-for="item in moments.rows" :moment="item" :key="item.id"></MomentCard>
        <a-pagination  id="pagination"
                       v-model:current=currentPage :total="moments.total"
                       @change="onPageChange"
                       simple
                       style="text-align: center; margin-top: 20px;"
        >
        </a-pagination>
      </div>
    </div>
  </div>
</template>

<style scoped>
.mt-5{
  margin-top:40px
}
</style>