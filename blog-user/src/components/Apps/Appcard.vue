<script setup>
import { defineProps } from 'vue'
const props = defineProps({
  app: {
    type: Object,
    required: true
  }
})
const handleClick = () => {
  window.open(props.app.url, '_blank')
}
</script>

<template>
   <div class="mt-5" @click="handleClick" style="display: flex;flex-direction: column;">
         <h4 style="font-weight: bold;font-size: 20px;">{{ app.title }}</h4>
         <p style=" font-weight: 300;">{{ app.content }}</p>
         <a style="margin-top:auto" :href="app.url" target="_blank" class="link">Go to →</a>
   </div>
</template>

<style scoped>
.mt-5:hover .link{
  cursor:url(/public/new-tab.png) 10 10,pointer;
  text-decoration: none;
  background: linear-gradient(to right, #49d7bb, #2ed9cb) no-repeat left bottom;
  background-size:100% 2px;
  transition: background-size 500ms;
  color: var(--link-color) ;
}
</style>