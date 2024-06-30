<script setup>
import { defineProps, onMounted } from "vue"
const props = defineProps({
  id: {
    type: String,
    default: ""
  }
})
</script>

<template>
  <meting-js server="netease" type="song" :id="props.id" ></meting-js>
</template>

<style scoped>

</style>