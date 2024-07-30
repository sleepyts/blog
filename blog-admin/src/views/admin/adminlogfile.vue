<template>
    <MdPreview v-model="log" :preview-theme="'vuepress'" class="md-editor-v3-preview" />
</template>

<script setup>
import { getLogFile } from '@/api/operation';
import { onMounted, ref } from 'vue';
import { MdPreview } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
const log = ref('')
onMounted(() => {
    getLogFile().then(res => {
        log.value = res.data.data
    })
})
</script>

<style scoped>
.md-editor-v3-preview * {
    font-size: 14px;
    line-height: 1.5;
}
</style>