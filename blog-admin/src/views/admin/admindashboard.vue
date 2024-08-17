<template>
  <div style="padding: 30px;display: flex;flex-direction: column;">
    <a-row >
      <a-col :span="12">
        <a-statistic title="当日PV" :value=dashBoardData.dailyPV >
          <template #prefix>
            <PieChartOutlined />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="12">
        <a-statistic title="当日UV" :value=dashBoardData.dailyUV >
          <template #prefix>
            <UserOutlined />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="12">
        <a-statistic title="总PV" :value=dashBoardData.totalPV >
          <template #prefix>
            <UserOutlined />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="12">
        <a-statistic title="总UV" :value=dashBoardData.totalUV >
          <template #prefix>
            <UserOutlined />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="12">
        <a-statistic title="文章总数" :value=dashBoardData.blogCount  >
          <template #prefix>
            <PieChartOutlined />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="12">
        <a-statistic title="评论总数" :value=dashBoardData.commentCount  >
          <template #prefix>
            <PieChartOutlined />
          </template>
        </a-statistic>
      </a-col>
    </a-row>
  </div>
</template>
<script setup>
import {onMounted, ref} from 'vue'
import {getDashboardData} from "@/api/dashboard.js";
import {PieChartOutlined, UserOutlined} from '@ant-design/icons-vue'
const dashBoardData = ref({})
onMounted(async () => {
  getDashboardData().then(res => {
    if(res.data.code === 200){
      dashBoardData.value = res.data.data
    }
  })
})
</script>