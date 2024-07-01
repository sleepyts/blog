<script setup>
import '../../assets/theam.css'
import {defineProps, ref, onBeforeMount, onMounted} from 'vue';
import Guest from "@/components/Comment/Guest.vue";
import ReplyCard from "@/components/Comment/ReplyCard.vue";
import store from "@/store/index.js";
import {convertToRelativeTime} from "@/util/timeUtil.js";
import {getRandomColor} from "@/util/colorUtil.js";
import {MdPreview} from "md-editor-v3";
import Reply from "@/components/Comment/Reply.vue";

const props = defineProps({
  comment: {
    type: Object,
    required: true
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
  const link = document.getElementById('randomColorLink');
  time.value = convertToRelativeTime(props.comment.createTime)
  // console.log(time.value)
  if (!link) return;
  link.style.color = getRandomColor();
})
</script>

<template>
  <div class="comments-card">
    <div class="comment-body">
      <div class="head">
        <a v-if="props.comment.url&&props.comment.url!==''" :href=props.comment.url target="_blank" class="name link"
           id="randomColorLink" >{{ props.comment.name }}</a>
        <span v-else class="name" >{{ props.comment.name }}</span>
        <span class="date" style="font-weight: 400;font-size:0.7em;color: var(--second-text-color);">{{ time }}</span>
        <svg viewBox="0 0 1024 1024" class="icon-reply" @click="handleReply">
          <path
              d="M810.667 213.333a64 64 0 0 1 64 64V704a64 64 0 0 1-64 64H478.336l-146.645 96.107a21.333 21.333 0 0 1-33.024-17.856V768h-85.334a64 64 0 0 1-64-64V277.333a64 64 0 0 1 64-64h597.334zm0 64H213.333V704h149.334v63.296L459.243 704h351.424V277.333zm-271.36 213.334v64h-176.64v-64h176.64zm122.026-128v64H362.667v-64h298.666z"></path>
        </svg>
      </div>
      <div class="content" style="display: flex; flex-direction: column;">
        <MdPreview :model-value="props.comment.content" ></MdPreview>
      </div>
      <div class="reply" v-if="isReplying">
        <Reply :blog-id="-1"></Reply>
      </div>
      <div class="line"></div>
      <div class="replylist"
           style="display: flex;flex-direction: row;position: relative;height:auto;margin-left: 10px;">
        <div style=" position: absolute;
  left: 0;
  top: 10%;
    width: 2px;
  height: 90%;
  border-left:2px dashed var(--dash-border-color); ">
        </div>
        <div style="width: 100%;">
          <div v-for="reply in props.comment.replyList" class="reply-item">
            <ReplyCard :is-first=true :reply-name="props.comment.name" :comment="reply"></ReplyCard>
          </div>
        </div>
      </div>
    </div>
  </div>


</template>

<style scoped>
.head {
  margin-top: 10px;
}

.line {
  margin-top: 10px;
  margin-bottom: 10px;
  border-bottom: 2px dashed var(--dash-border-color);
}

.icon-reply {
  cursor: pointer;
  fill: #999;
}

.icon-reply:hover {
  fill: yellowgreen
}

.comments-card {
  display: flex;
  padding: 10px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.comment-body {
  flex: 1;
}

.head {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.name {
  font-size: 18px;
  font-weight: 600;
}

.date {
  margin-left: 10px;
  font-size: 12px;
  font-weight: 100;
}

.icon-reply {
  margin-left: auto;
  width: 24px;
  height: 24px;
  cursor: pointer;
}

.content {
  margin-top: 10px;
  word-wrap: break-word;
}
.content *{
  font-size: 1em;
  margin: 0;
}

.reply {
  margin-top: -2em;
}

.line {
  border-top: 1px solid #e1e1e1;
  margin-top: 10px;
  margin-bottom: 10px;
}


.reply-item {
  margin-top: 10px;
  margin-left: 10px;
}


</style>