<script setup>
import {computed, nextTick, onMounted, ref, watch} from "vue";
import store from "@/store/index.js";
import CardTitle from "@/components/CardTitle.vue";
import {MdPreview, MdCatalog, MdEditor} from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import {getBlogContent, getBlogDetail} from "@/api/blog.js";
import {HighlightOutlined, EditOutlined, BookOutlined,LoadingOutlined} from "@ant-design/icons-vue";
import router from "@/router/index.js";
import axios from "axios";

const blog = ref({
  title: '',
});
// 需要显示的文本
const Abbreviation = ref('')
const trueAbbreviation=ref('')
const isLoadingAbbreviation=ref(true)
// 显示文本的容器

// 设置随机延迟范围（毫秒）
const minDelay = 10; // 最小延迟
const maxDelay = 40; // 最大延迟

// 获取随机延迟函数
function getRandomDelay(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min; // 生成 [min, max] 范围内的随机数
}
// 流式显示的函数
function streamText(text){
  let index = 0;
  // 使用递归和 setTimeout 实现流式效果
  function showNextChar() {
    if (index < text.value.length) {
      trueAbbreviation.value += text.value.charAt(index); // 添加下一个字符
      index++;
      const delay = getRandomDelay(minDelay, maxDelay); // 获取随机延迟
      setTimeout(showNextChar, delay); // 使用随机延迟，显示下一个字符
    }
  }
  showNextChar(); // 开始显示
}
const id = 'preview-only';
const text = ref('');
const scrollElement = document.documentElement;
onMounted( async () => {
  await getBlogDetail(router.currentRoute.value.params.id).then(res => {
    if (res.data.code === 200) {
      blog.value = res.data.data;
    }
  })
  await getBlogSummary(router.currentRoute.value.params.id);
});
watch(() => router.currentRoute.value.params.id, async () => {
  await getBlogDetail(router.currentRoute.value.params.id).then(res => {
    if (res.data.code === 200) {
      blog.value = res.data.data;
    }
  })
})
const getBlogSummary = (id) => {
  const eventSource = new EventSource(`/api/Blog/summary/${id}`);

  eventSource.addEventListener('conversation.message.delta', function(event) {
    trueAbbreviation.value += event.data;
    isLoadingAbbreviation.value = false;
  });

  eventSource.onerror = function(error) {
    console.error('SSE error:', error);
    streamText("获取文章AI摘要失败，可能是站长的API限额了或网络问题。");
  };

  // 返回 eventSource，以便可以在外部控制连接
  return eventSource;
};
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
  <div style="
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #ffffff;
    border-radius: 8px;
    margin: 20px auto;
    line-height: 1.6;
">
    <p style="
        font-size: 1.6em;
        font-weight: 600;
        color: #333;
        margin-bottom: 10px;
        text-align: center;
    ">AI文章摘要</p>
    <LoadingOutlined v-if="isLoadingAbbreviation"/>
    <span style="
        font-size: 1em;
        color: #555;
        text-align: left;
    ">
        {{trueAbbreviation}}
    </span>
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
