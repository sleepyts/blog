<script setup>
import { defineProps } from 'vue'
import { MdPreview } from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import { LikeFilled, LikeOutlined } from '@ant-design/icons-vue';
import { likeMoment } from '@/api/Moment';
import { message } from 'ant-design-vue';
const props = defineProps({
  moment: {
    type: Object,
    required: true
  }
})
const like = (momentId) => {
  likeMoment(momentId).then(res => {
    if (res.data.code === 200) {
    }
  })
  
  // 前端简单地进行更新防止每次更新都要访问后端，降低压力
  props.moment.isLike = !props.moment.isLike
  if (props.moment.isLike) {
    props.moment.likeCount += 1
    message.success('点赞成功')
  } else {
    props.moment.likeCount -= 1
    message.success('取消点赞成功')
  }
}
</script>

<template>
  <div class="moment-card">
    <div class="in-container">
      <span>{{ new Date(props.moment.createTime).toLocaleString() }}</span>
      <span style="margin-left: auto;">{{ props.moment.likeCount }}</span>
      <LikeOutlined v-if="props.moment.isLike !== true" style="margin-left: 10px;align-self: start;"
        @click=" like(props.moment.id)" />
      <LikeFilled v-else style="margin-left: 10px; color:#FF0000; align-self: start;" @click=" like(props.moment.id)" />
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
  padding-left: 20px;
  padding-right: 20px;
  display: flex;
  align-items: center;
}

.line-break {
  height: 1px;
  border-bottom: 1px solid #ccc;
}
</style>