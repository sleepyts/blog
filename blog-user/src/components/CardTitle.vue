<script setup>

const props = defineProps({
  text: {
    type: String,
    required: true
  },
  fontSize: {
    type: String,
    default: '24px'
  }
})
</script>

<template>
  <div style="display: flex">
    <div class="front-title" style="margin-left: -10px;"></div>
    <p style="font-size: 2.5em; font-weight: 500; margin-left: 20px">{{ text }}</p>
  </div>
</template>

<style scoped>
.front-title {
  width: 2px;
  height: 3em;
  border: 2px solid black;
}
</style>