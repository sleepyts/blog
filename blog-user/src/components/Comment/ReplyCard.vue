<script setup>
import '../../assets/theam.css'
import { defineProps, onMounted, ref } from 'vue';
import Guest from "@/components/Comment/Guest.vue";
import { convertToRelativeTime } from "@/util/timeUtil.js";
import { getRandomColor } from "@/util/colorUtil.js";
import { MdPreview } from "md-editor-v3";
import store from "@/store/index.js";
import Reply from "@/components/Comment/Reply.vue";

const props = defineProps({
  comment: {
    type: Object,
    required: true
  },
  replyName: {
    type: String,
    required: false,
  },
  isFirst: {
    type: Boolean,
    required: false,
  }
});
const time = ref("")
let isReplying = ref(false);
const handleReply = () => {
  isReplying.value = !isReplying.value
  store().commit("SET_REPLY_TO", isReplying.value === true ? props.comment.id : -1)
  //console.log(state.replyTo)
}
onMounted(async () => {
  const link = await document.getElementById('randomColorLink');
  time.value = convertToRelativeTime(props.comment.createTime)
  // console.log(time.value)
  if (!link) return;
  link.style.color = getRandomColor();
})
</script>

<template>
  <div class="reply-card">
    <div class="header">
      <a v-if="props.comment.url && props.comment.url !== ''" :href=props.comment.url target="_blank" class="name link"
        id="randomColorLink">{{ props.comment.name }}</a>
      <span v-else class="name">{{ props.comment.name }}</span>
      <span style="margin-left: 10px;">{{ time }}</span>
      <span v-if="props.comment.isAdmin " style="margin-left: 10px;border-radius: 5px;padding: 2px 5px;background-color: darkseagreen;color: #fff;font-size: 0.8em;"> (站长)</span>
      <a-button size="small" style="margin-left: auto;" @click="handleReply">回复</a-button>
    </div>
    <div class="content">
      <div style="padding-left: 20px;">
        <span style="font-size: 0.8em;">{{ "回复 @" + props.replyName + "：" }}</span>
      </div>
      <MdPreview :model-value="props.comment.content" :preview-theme="'github'"></MdPreview>
    </div>
    <div class="reply" v-if="isReplying">
      <Reply></Reply>
    </div>
    <div v-for="reply in props.comment.replyList" class="reply-item">
      <ReplyCard :reply-name="props.comment.name" :comment="reply"></ReplyCard>
    </div>
  </div>
</template>

<style scoped>
.header {
  padding-top: 10px;
  padding-left: 20px;
  padding-right: 20px;
  font-size: 1.2em;
  display: flex;
  flex-direction: row;

  align-items: center;
}

.name {
  font-weight: bold;
}

.line {
  margin-top: 10px;
  margin-bottom: 10px;
  border-bottom: 3px dashed var(--dash-border-color);
}

.icon-reply {
  cursor: pointer;
  fill: #999;
}

.content {
  display: flex;
  flex-direction: column;
  padding-left: 20px;
}

.icon-reply {
  margin-left: auto;
  width: 24px;
  height: 24px;
  cursor: pointer;
}

.icon-reply:hover {
  fill: yellowgreen
}
</style>