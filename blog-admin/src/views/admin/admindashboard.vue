<template>
  <div style="padding: 30px;display: flex;flex-direction: column;">
    <a-row >
      <a-col :span="12">
        <a-statistic title="后端请求数" :value=pv >
          <template #prefix>
            <PieChartOutlined />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="12">
        <a-statistic title="独立用户访问量" :value=uv >
          <template #prefix>
            <UserOutlined />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="12">
        <a-statistic title="文章总数" :value=totalArticle  >
          <template #prefix>
            <PieChartOutlined />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="12">
        <a-statistic title="评论总数" :value=totalComment  >
          <template #prefix>
            <PieChartOutlined />
          </template>
        </a-statistic>
      </a-col>
    </a-row>
  </div>
</template>
<script setup>
import {ref, onMounted} from 'vue'
import {getPV, getTotalArticle, getTotalComment, getUV} from "@/api/dashboard.js";
import {PieChartOutlined, UserOutlined} from '@ant-design/icons-vue'
const uv = ref()
const pv = ref()
const totalArticle = ref()
const totalComment = ref()
onMounted(async () => {
  await getUV().then(res => {
    if (res.data.code === 200) uv.value = res.data.data
  })
  await getPV().then(res => {
    if (res.data.code === 200) pv.value = res.data.data
  })
  await  getTotalArticle().then(res => {
    if (res.data.code === 200) totalArticle.value = res.data.data
  })
  await  getTotalComment().then(res => {
    if (res.data.code === 200) totalComment.value = res.data.data
  })
})
</script>