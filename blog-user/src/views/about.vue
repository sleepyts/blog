<script setup>
import Title from "@/components/Title.vue";
import AboutMe from "@/components/About/AboutMe.vue";
</script>
<template>
  <div class="app">
    <BlogHeader />
    <Title title="About"></Title>
    <about-me></about-me>
  </div>
</template>


<script>
import BlogHeader from '@/components/BlogHeader.vue';
export default {
  components: {
    BlogHeader
  }
};
</script>

<style scoped>
</style>