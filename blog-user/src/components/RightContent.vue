<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
// 定义一个 ref 变量来存储当前时间
const now = ref(new Date().toLocaleString())
// 定义一个更新时间的函数
const updateTime = () => {
  now.value = new Date().toLocaleString()
}
// 在组件挂载时设置一个定时器来更新时间
onMounted(() => {
  const interval = setInterval(updateTime, 1000)
  // 在组件卸载时清除定时器
  onUnmounted(() => {
    clearInterval(interval)
  })
})

</script>

<template>
  <div class="content">
   <div class="header">
     <h2>未月拾叁</h2>
   </div>
  </div>
</template>

<style scoped>
.content{
  width: 13%;
  height: 100vh;
  display : flex;
  flex-direction: column;
  padding: 20px;
}

</style>