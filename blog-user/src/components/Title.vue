<script setup>
import {defineProps} from 'vue';
import '../assets/theam.css'

const props = defineProps({
  title: {
    type: String,
    required: true
  },
})
</script>

<template>
  <span class="blogTitle page-main-container">{{props.title}}
  </span>
</template>

<style scoped>
@media (max-width: 768px) {
  .blogTitle {
    width: 90%;
    position: relative;
    font-size: 50px;
    font-weight: bold;
    margin-left: 5%;
    top:20px;
    margin-bottom: 50px;
    background: var(--active-background-color);
    background-clip: text;
    transition: all 0.3s ease-in-out;
    animation: slidIn 0.7s ease-in-out;
    color: var(--active-color);
  }
}
@media (min-width: 768px) {
  .blogTitle {
    position: relative;
    font-size: 50px;
    font-weight: bold;
    top: 20px;
    margin-bottom: 50px;
    background: var(--active-background-color);
    background-clip: text;
    transition: all 0.3s ease-in-out;
    animation: slidIn 0.7s ease-in-out;
    color: var(--active-color);
  }
}
@keyframes slidIn {
  from {
    transform: translateY(20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}
</style>