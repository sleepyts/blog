<script setup>
import { computed,ref,watch } from 'vue'
import store from "@/store/index.js";

const className = ref("loading")
watch(()=>store().state.isLoading,()=>{
  if(store().state.isLoading){
    className.value = "loading"
  }else{
    className.value = "ok"
  }
})
</script>

<template>
  <div>
    <div :class="className"></div>
  </div>
</template>

<style scoped>
.loading{
  height: 6px;
  width:100%;
  background: var(--dynamic-color);
  background-size: 20% 20px;
  z-index: 9999;
}
.ok{
  height: 6px;
  width:100%;
  background: var(--dynamic-color);
  transition: background-size 0.5s ease-in-out;
  background-size: 100% 20px;
  z-index: 9999;
}
</style>