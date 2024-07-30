<script setup>
import '../../assets/theam.css'
import { computed, provide, ref, watch, nextTick, defineProps } from "vue";
import { SyncOutlined } from '@ant-design/icons-vue';
import OthersInfo from "@/components/Comment/OthersInfo.vue";
import store from "@/store/index.js";
import CardTitle from "@/components/CardTitle.vue";
import 'md-editor-v3/lib/style.css';
import { MdEditor } from "md-editor-v3";
import { message } from "ant-design-vue";
const img = ref('')
const containImg = ref(false)
const verifyText = computed(() => {
  return store().state.verify.var1 + " * " + store().state.verify.var2 + "= ?"
})
const commentForm = computed(() => {
  return store().state.commentForm
})
const props = defineProps({
  blogId: {
    type: Number,
    required: false
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
</script>
<template>
  <div class="comments">
    <div class="guestbook mt-5">
      <div class="commentsInputs">
        <MdEditor v-model="commentForm.content" :preview-theme="'vuepress'" :toolbars="[]" :maxLength=500
          style="margin-top: 10px;height: 200px;" :preview=false />
      </div>
      <div class="infoInputs">
        <div class="info">
          <span style="font-size: 12px; margin-left: 1em ">Name</span>
          <a-input type="text" class="input" placeholder="选填" v-model:value="commentForm.name" />
          <span style="font-size: 12px;margin-left: 1em ">Url</span>
          <a-input type="text" class="input" placeholder="选填(https://)" v-model:value="commentForm.url" />
          <span style="font-size: 12px;  margin-left: 1em">Verify</span>
          <a-input type="text" class="input" :placeholder=verifyText v-model:value="store().state.verify.verifyVar" />
          <SyncOutlined class="reFresh" :rotate="180" style="font-size:1em;margin-left: 1em;cursor: pointer"
            @click='store().dispatch("getVerify")' />
        </div>
      </div>
      <div class="othersAndsubmitBtn" style="display: flex; justify-content: space-between; margin-top: 10px">
        <div style="display: flex; align-items: center;">
          <others-info style="margin-left: 10px"></others-info>
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
    margin-left: 5%;
    width: 90%;
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

.mt-5 {
  animation: none;
}
</style>