<script setup>
import '../../assets/theam.css'
import {defineProps, onMounted, ref} from 'vue';
import Guest from "@/components/Comment/Guest.vue";
import {convertToRelativeTime} from "@/util/timeUtil.js";
import {getRandomColor} from "@/util/colorUtil.js";
import {MdPreview} from "md-editor-v3";
import store from "@/store/index.js";
import Reply from "@/components/Comment/Reply.vue";

const props = defineProps({
  comment: {
    type: Object,
    required: true
  },
  replyName:{
    type:String,
    required:false,
  },
  isFirst:{
    type:Boolean,
    required:false,
  }
});
const time=ref("")
let isReplying = ref(false);
const handleReply = () => {
  isReplying.value = !isReplying.value
  store().commit("SET_REPLY_TO", isReplying.value === true ? props.comment.id : -1)
  //console.log(state.replyTo)
}
onMounted(async () => {
  const link = await document.getElementById('randomColorLink');
  time.value=convertToRelativeTime(props.comment.createTime)
  // console.log(time.value)
  if (!link) return;
  link.style.color = getRandomColor();
})
</script>

<template>
  <div class="reply-card">
    <div class="head">
      <a v-if="props.comment.url&&props.comment.url!==''"  :href=props.comment.url target="_blank" class="name link"  id="randomColorLink">{{props. comment.name}}</a>
      <span v-else class="name">{{ props.comment.name }}</span>
      <span class="date" style="margin-left: 10px; font-size: 0.7em; font-weight: 100;color:var(--second-text-color)">{{ time }}</span>
      <svg viewBox="0 0 1024 1024" class="icon-reply" @click="handleReply">
        <path
            d="M810.667 213.333a64 64 0 0 1 64 64V704a64 64 0 0 1-64 64H478.336l-146.645 96.107a21.333 21.333 0 0 1-33.024-17.856V768h-85.334a64 64 0 0 1-64-64V277.333a64 64 0 0 1 64-64h597.334zm0 64H213.333V704h149.334v63.296L459.243 704h351.424V277.333zm-271.36 213.334v64h-176.64v-64h176.64zm122.026-128v64H362.667v-64h298.666z"></path>
      </svg>
    </div>
    <div class="content" style="margin-top: 10px; display: flex; flex-direction: row;">
        <span v-if="props.replyName" style="margin-top:auto;margin-bottom:auto;font-size: 0.8em; color: var(--primary-color);margin-right: 5px;">{{"@" + props.replyName}}</span>
        <span>{{props.comment.content}}</span>
    </div>
    <a-image :src=props.comment.imgUrl v-if="props.comment.imgUrl"
         style="max-width: 30%; margin-top: 10px; margin-bottom: 10px; border-radius: 5px;"
         alt="undefined"/>
    <div class="reply" v-if="isReplying" >
        <Reply></Reply>
    </div>
    <div  v-for="reply in props.comment.replyList" class="reply-item">
      <ReplyCard :reply-name="props.comment.name" :comment="reply"></ReplyCard>
    </div>
    <div v-if="isFirst" class="line"></div>
  </div>
</template>

<style scoped>

.head {
  margin-top: 10px;
}
.head {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
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