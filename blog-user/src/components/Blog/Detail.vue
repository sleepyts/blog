<script setup>
import {computed, nextTick, onMounted, ref, watch} from "vue";
import store from "@/store/index.js";
import CardTitle from "@/components/CardTitle.vue";
import {MdPreview, MdCatalog, MdEditor} from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import {getBlogContent, getBlogDetail} from "@/api/blog.js";
import {HighlightOutlined, EditOutlined, BookOutlined} from "@ant-design/icons-vue";
import router from "@/router/index.js";

const blog = ref({
  title: '',
});
const id = 'preview-only';
const text = ref('');
const scrollElement = document.documentElement;
onMounted(async () => {
  await getBlogDetail(router.currentRoute.value.params.id).then(res => {
    if (res.data.code === 200) {
      blog.value = res.data.data;
    }
  })
});
watch(() => router.currentRoute.value.params.id, async () => {
  await getBlogDetail(router.currentRoute.value.params.id).then(res => {
    if (res.data.code === 200) {
      blog.value = res.data.data;
    }
  })
})
</script>
<template>
  <img v-if="blog.img" alt="img" :src="blog.img" style="width: 100%;object-fit: cover"/>
  <div class="short-info" style="margin-top: 15px;">
    <card-title :text="blog.title"></card-title>
    <HighlightOutlined/>
    <a-tooltip>
      <template #title>创建时间</template>
      <span style="margin-left: 10px;margin-right: 10px">{{ new Date(blog.createTime).toLocaleString() }}</span>
    </a-tooltip>
    <EditOutlined/>
    <a-tooltip :overlay-style="{ display: 'block' }">
      <template #title>更新时间</template>
      <span style="margin-left: 10px;margin-right: 10px">{{ new Date(blog.updateTime).toLocaleString() }}</span>
    </a-tooltip>
    <BookOutlined/>
    <span style="margin-left: 10px;margin-right: 10px">{{ blog.categoryName }}</span>

  </div>
  <div class="line"></div>

  <div class="main-content" style="display: flex;flex-direction: column;">
    <div style="display: flex;flex-direction: row;">
      <MdPreview :editorId=id :modelValue="blog.content" class="md-editor-preview" :previewTheme="'github'"
                 :showCodeRowNumber=false :codeFoldable=false />
    </div>
  </div>
</template>

<style scoped>

.short-info:hover .dynamic-line {
  background-size: 100% 2px;
}

.toc * {
  font-size: 16px;
}

@media (min-width: 768px) {
  .toc {
    position: sticky;
    top: 80px;
    height: 100%;
    overflow-y: auto;
    width: 30%;
  }

  .actions-buttons {
    position: fixed;
    top: 40%;
    left: 15%;
    display: flex;
    flex-direction: column;
  }
}

@media (max-width: 768px) {
  .md-editor-preview {
    font-size: 10px;
  }
}
</style>
