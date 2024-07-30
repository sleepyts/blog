<script setup>
import { defineProps } from 'vue'
import { MdPreview } from "md-editor-v3";
import "md-editor-v3/lib/style.css";
const props = defineProps({
  moment: {
    type: Object,
    required: true
  }
})
</script>

<template>
  <div class="moment-card">
    <div class="in-container">
      <span>{{ new Date(props.moment.createTime).toLocaleString() }}</span>
    </div>
    <div class="line-break"></div>
    <div>
      <MdPreview :modelValue="props.moment.content" :preview-theme="'vuepress'"></MdPreview>
    </div>
  </div>
</template>

<style scoped>
.moment-card {
  border: 1px solid #ccc;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}

.in-container {
  padding: 10px;
}

.line-break {
  height: 1px;
  border-bottom: 1px solid #ccc;
}
</style>