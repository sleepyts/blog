<script setup>
import '../../assets/theam.css'
import {defineProps, onMounted, ref} from 'vue';
import Guest from "@/components/Comment/Guest.vue";
import {convertToRelativeTime} from "@/util/timeUtil.js";
import {getRandomColor} from "@/util/colorUtil.js";
import {MdPreview} from "md-editor-v3";

const props = defineProps({
  comment: {
    type: Object,
    required: true
  }
});
const time=ref("")
let isReplying = ref(false);
onMounted(async () => {
  const link = await document.getElementById('randomColorLink');
  time.value=convertToRelativeTime(props.comment.createTime)
  // console.log(time.value)
  if (!link) return;
  link.style.color = getRandomColor();
})
</script>

<template>
  <div class="reply-card" style="padding: 10px;">
    <div class="head">
      <a v-if="props.comment.url&&props.comment.url!==''"  :href=props.comment.url target="_blank" class="name link"  id="randomColorLink">{{props. comment.name}}</a>
      <span v-else class="name">{{ props.comment.name }}</span>
      <span class="date" style="margin-left: 10px; font-size: 12px; font-weight: 100;">{{ time }}</span>
    </div>
    <div class="content" style="margin-top: 10px;">
      <MdPreview :model-value="props.comment.content"></MdPreview>
    </div>
    <a-image :src=props.comment.imgUrl v-if="props.comment.imgUrl"
         style="max-width: 30%; margin-top: 10px; margin-bottom: 10px; border-radius: 5px;"
         alt="undefined"/>
    <div class="reply" v-if="isReplying" >
      <Guest :blog-id=-1 :reply-id=props.comment.id></Guest>
    </div>
    <div class="line"></div>
  </div>
</template>

<style scoped>
.head {
  margin-top: 10px;
}

.line {
  margin-top: 10px;
  margin-bottom: 10px;
  border-bottom: 3px dashed var(--dash-border-color);
}
</style>