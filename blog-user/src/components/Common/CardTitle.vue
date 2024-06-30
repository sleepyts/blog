<script setup>

const props = defineProps({
    text:{
        type:String,
        required:true
  },
  fontSize:{
      type:String,
      default:'24px'
  }
})
</script>

<template>
  <div style="display: flex">
    <div class="front-title" style="margin-left: -20px;"></div>
    <p style="font-size: 3em; font-weight: 500; margin-left: 20px">{{text}}</p>
  </div>
</template>

<style scoped>

</style>