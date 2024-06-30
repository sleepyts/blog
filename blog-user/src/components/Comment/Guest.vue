<script setup>
import '../../assets/theam.css'
import {computed, provide, ref, watch, onMounted, defineProps} from "vue";
import {SyncOutlined} from '@ant-design/icons-vue';
import OthersInfo from "@/components/Comment/OthersInfo.vue";
import store from "@/store/index.js";
import 'md-editor-v3/lib/style.css';
import {MdEditor} from "md-editor-v3";
import {message} from "ant-design-vue";
import state from "@/store/state.js";
const img = ref('')
const containImg = ref(false)
const checked = ref(false)
const mdEditor = ref(null)
const verifyText = computed(() => {
  return store().state.verify.var1 + " * " + store().state.verify.var2 + "= ?"
})
const commentForm = computed(() => {
  return store().state.commentForm
})
const props = defineProps({
  blogId: {
    type: Number,
    required: true
  },
})
provide("containImg", containImg);
provide("img", img)
const submitComment = () => {
  if (commentForm.value.content.trim() === '' && !containImg.value) {
    message.error('评论内容不能为空')
    return
  }
  store().dispatch("postComment")
};
watch(checked, () => {
  if (checked.value) {
    localStorage.setItem("name", commentForm.value.name)
    localStorage.setItem("url", commentForm.value.url)
  } else {
    localStorage.removeItem("name")
    localStorage.removeItem("url")
  }
})
watch(commentForm.value, () => {
  if(checked.value){
    localStorage.setItem("name", commentForm.value.name)
    localStorage.setItem("url", commentForm.value.url)
  }
})
watch(() => state.theme, () => {
  console.log("theme change")
})
onMounted(() => {
  if (localStorage.getItem("name") || localStorage.getItem("url")) {
    commentForm.value.name = localStorage.getItem("name")
    commentForm.value.url = localStorage.getItem("url")
    checked.value = true
  }
})
</script>

<template>
  <div class="comments page-main-container">
    <div class="guestbook mt-5">
      <div class="commentsInputs">
        <MdEditor v-model="commentForm.content" :preview-theme="'vuepress'"
                  :toolbars="['bold','italic','strike','heading','link','codeRow','code','fullscreen','quote']"
                  :maxLength=500 style="margin-top: 10px;height: 200px;"
                  ref="mdEditor"/>
      </div>
      <div class="infoInputs">
        <div class="info">
          <span style="font-size: 1.2em;margin-left: 12px">Name</span>
          <a-input type="text" class="input" placeholder="选填" v-model:value="commentForm.name"/>
          <span style="font-size: 1.2em;margin-left: 1em ">Url</span>
          <a-input type="text" class="input" placeholder="选填(https://)" v-model:value="commentForm.url"/>
          <span style="font-size: 1.2em;margin-left: 1em">Verify</span>
          <a-input type="text" class="input" :placeholder=verifyText v-model:value="store().state.verify.verifyVar"/>
          <SyncOutlined class="reFresh" :rotate="180" style="font-size:1em;margin-left: 1em;cursor: pointer"
                        @click='store().dispatch("getVerify")'/>
        </div>
      </div>
      <div style="margin-left:12px;display: flex; float: left;margin-top: 10px;margin-bottom: 10px">
        <a-switch v-model:checked="checked" size="small"/>
        <span style="font-size:0.8em;margin-left: 1em">记住我(下次将自动填充个人信息)</span>
      </div>
      <div class="othersAndsubmitBtn"
           style="display: flex; justify-content: space-between; margin-top: 10px;margin-left:12px;">
        <div style="display: flex; align-items: center;">
          <others-info></others-info>
        </div>
        <div>
          <a-button @click="submitComment" size="small">提交</a-button>
        </div>
      </div>

    </div>
  </div>
</template>

<style scoped>
@media (max-width: 768px) {
  .comments {
    position: relative;
    margin-top: 3em;
    height: 100%;
  }

  .info {
    align-items: center;
    justify-content: center;
    margin-top: 10px;
  }

  .info * {
    margin-bottom: 10px
  }

  .input {
    margin-left: 10px;
    width: 95%
  }
}

@media (min-width: 768px) {
  .comments {
    position: relative;
    margin-top: 3em;
    height: 100%;
  }

  .info {
    display: flex;
    align-items: center;
  }

  .input {
    margin-left: 10px;
  }
}

.guestbook {
  border: 1px solid var(--border-color);
  border-radius: 5px;
}


.infoInputs * {
  font-size: 12px;
}

.commentsInputs {
  background-color: var(--bg-color);
  padding: 10px;
}

.othersAndsubmitBtn {
  width: 99%;
}

.guestbook * {
  box-shadow: none;
}

@keyframes border-change {
  0% {
    border: 1px solid var(--border-color);
  }
  100% {
    border: 1px solid steelblue;
  }
}

.reFresh:hover {
  color: var(--colorPrimaryHover);
  transition: color 0.1s ease;
}
</style>