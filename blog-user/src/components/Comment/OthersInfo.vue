<script setup>
import '../../assets/theam.css'
import {computed, inject, ref} from "vue";
import Vue3EmojiPicker from 'vue3-emoji-picker'
import 'vue3-emoji-picker/css'
import store from "@/store/index.js";
import {message} from "ant-design-vue";
import {onRequestPost} from "@/api/upload-2.0.js";
import {MdPreview} from "md-editor-v3";


const fileUploader = ref(null);
const img = inject("img")
const containImg = inject("containImg")
const showEmoji = ref(false)
const showPreview = ref(false)
const handleFileSelect = () => {
  fileUploader.value.click();
}
const handleFileUpload = () => {
  const file = fileUploader.value.files[0];
  const name = file.name;
  if (!(name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".webp"))) {
    message.error("请选择图片文件")
    return;
  }
  img.value = fileUploader.value.files[0];
  if (img.value.size > 4 * 1024 * 1024) {
    message.error("图片大小不能超过4M")
    img.value = null;
    return;
  }
  containImg.value = true;
  message.success("选择成功");
  const formData = new FormData();
  formData.append("file", img.value);
  onRequestPost(formData).then(res => {
    if (res.code === 200) {
      const imgTag = `<img src="${res.url}" alt="Image" style="max-width: 100%; height: auto; display: block; margin: 0 auto;"/>`;
      store().state.commentForm.content +=imgTag;
      img.value = null;
      containImg.value = false;
      message.success("上传成功");
    } else {
      message.error("上传失败");
    }
  })
  //console.log(state.commentForm.content);
}
const content = computed(() => {
  return store().state.commentForm.content;
})
const onEmojiSelect = (emoji) => {
  //console.log(emoji)
  store().commit('SET_COMMENT_CONTENT', content.value + emoji.i)
}
const disableGroups = [
  "flags",
  "travel_places",
  "animals_nature",
  "activities",
  "objects",
  "food_drink",
  "symbols",
]
// "smileys_people": "Smiles & People",
//     "animals_nature": "Animals & Nature",
//     "food_drink": "Food & Drink",
//     "activities": "Activities",
//     "travel_places": "Travel places",
//     "objects": "Objects",
//     "symbols": "Symbols",
//     "flags": "Flags"
</script>

<template>

  <div class="links">
    <div class="other-info" style="display: flex">
<!--      <button style="background: var(&#45;&#45;bg-color); border: none; position: relative; width:24px;"-->
<!--              @click="handleFileSelect">-->
<!--        <svg viewBox="0 0 1024 1024" width="24" height="24" class="img" style="position: absolute; top: 0;right:0">-->
<!--          <path-->
<!--              d="M784 112H240c-88 0-160 72-160 160v480c0 88 72 160 160 160h544c88 0 160-72 160-160V272c0-88-72-160-160-160zm96 640c0 52.8-43.2 96-96 96H240c-52.8 0-96-43.2-96-96V272c0-52.8 43.2-96 96-96h544c52.8 0 96 43.2 96 96v480z"-->
<!--          ></path>-->
<!--          <path-->
<!--              d="M352 480c52.8 0 96-43.2 96-96s-43.2-96-96-96-96 43.2-96 96 43.2 96 96 96zm0-128c17.6 0 32 14.4 32 32s-14.4 32-32 32-32-14.4-32-32 14.4-32 32-32zm462.4 379.2-3.2-3.2-177.6-177.6c-25.6-25.6-65.6-25.6-91.2 0l-80 80-36.8-36.8c-25.6-25.6-65.6-25.6-91.2 0L200 728c-4.8 6.4-8 14.4-8 24 0 17.6 14.4 32 32 32 9.6 0 16-3.2 22.4-9.6L380.8 640l134.4 134.4c6.4 6.4 14.4 9.6 24 9.6 17.6 0 32-14.4 32-32 0-9.6-4.8-17.6-9.6-24l-52.8-52.8 80-80L769.6 776c6.4 4.8 12.8 8 20.8 8 17.6 0 32-14.4 32-32 0-8-3.2-16-8-20.8z"-->
<!--          ></path>-->
<!--        </svg>-->
<!--      </button>-->
      <div>
        <svg viewBox="0 0 1024 1024" width="24" height="24" class="emoji" @click="showEmoji=!showEmoji">
          <path
              d="M563.2 463.3 677 540c1.7 1.2 3.7 1.8 5.8 1.8.7 0 1.4-.1 2-.2 2.7-.5 5.1-2.1 6.6-4.4l25.3-37.8c1.5-2.3 2.1-5.1 1.6-7.8s-2.1-5.1-4.4-6.6l-73.6-49.1 73.6-49.1c2.3-1.5 3.9-3.9 4.4-6.6.5-2.7 0-5.5-1.6-7.8l-25.3-37.8a10.1 10.1 0 0 0-6.6-4.4c-.7-.1-1.3-.2-2-.2-2.1 0-4.1.6-5.8 1.8l-113.8 76.6c-9.2 6.2-14.7 16.4-14.7 27.5.1 11 5.5 21.3 14.7 27.4zM387 348.8h-45.5c-5.7 0-10.4 4.7-10.4 10.4v153.3c0 5.7 4.7 10.4 10.4 10.4H387c5.7 0 10.4-4.7 10.4-10.4V359.2c0-5.7-4.7-10.4-10.4-10.4zm333.8 241.3-41-20a10.3 10.3 0 0 0-8.1-.5c-2.6.9-4.8 2.9-5.9 5.4-30.1 64.9-93.1 109.1-164.4 115.2-5.7.5-9.9 5.5-9.5 11.2l3.9 45.5c.5 5.3 5 9.5 10.3 9.5h.9c94.8-8 178.5-66.5 218.6-152.7 2.4-5 .3-11.2-4.8-13.6zm186-186.1c-11.9-42-30.5-81.4-55.2-117.1-24.1-34.9-53.5-65.6-87.5-91.2-33.9-25.6-71.5-45.5-111.6-59.2-41.2-14-84.1-21.1-127.8-21.1h-1.2c-75.4 0-148.8 21.4-212.5 61.7-63.7 40.3-114.3 97.6-146.5 165.8-32.2 68.1-44.3 143.6-35.1 218.4 9.3 74.8 39.4 145 87.3 203.3.1.2.3.3.4.5l36.2 38.4c1.1 1.2 2.5 2.1 3.9 2.6 73.3 66.7 168.2 103.5 267.5 103.5 73.3 0 145.2-20.3 207.7-58.7 37.3-22.9 70.3-51.5 98.1-85 27.1-32.7 48.7-69.5 64.2-109.1 15.5-39.7 24.4-81.3 26.6-123.8 2.4-43.6-2.5-87-14.5-129zm-60.5 181.1c-8.3 37-22.8 72-43 104-19.7 31.1-44.3 58.6-73.1 81.7-28.8 23.1-61 41-95.7 53.4-35.6 12.7-72.9 19.1-110.9 19.1-82.6 0-161.7-30.6-222.8-86.2l-34.1-35.8c-23.9-29.3-42.4-62.2-55.1-97.7-12.4-34.7-18.8-71-19.2-107.9-.4-36.9 5.4-73.3 17.1-108.2 12-35.8 30-69.2 53.4-99.1 31.7-40.4 71.1-72 117.2-94.1 44.5-21.3 94-32.6 143.4-32.6 49.3 0 97 10.8 141.8 32 34.3 16.3 65.3 38.1 92 64.8 26.1 26 47.5 56 63.6 89.2 16.2 33.2 26.6 68.5 31 105.1 4.6 37.5 2.7 75.3-5.6 112.3z"></path>
        </svg>
        <Vue3EmojiPicker v-model="content" @select="onEmojiSelect" v-show="showEmoji"
                         :disabled-groups=disableGroups :hide-search="true" :native="true" :theme=store().state.theme>
        </Vue3EmojiPicker>
      </div>
    </div>
<!--    <input type="file" style="display: none" ref="fileUploader" @change="handleFileUpload">-->
  </div>
</template>

<style scoped>

.img, .emoji, .preview {
  cursor: pointer;
  fill: #999;
}

.img:hover, .emoji:hover, .preview:hover {
  fill: yellowgreen
}

</style>