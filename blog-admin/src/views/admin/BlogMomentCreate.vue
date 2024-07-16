<template>
  <div class="top-20">
    <span>动态内容</span>
    <MdEditor v-model="momentForm.content" :preview-theme="'vuepress'"/>
  </div>
  <div class="top-20">
    <p>是否可见</p>
    <a-switch v-model:checked="momentForm.visible" @change="change()"/>
  </div>
  <div class="top-20">
    <a-button type="primary" @click="createMoment">发布</a-button>
  </div>
</template>

<script setup>
import {MdEditor} from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import {ref} from 'vue';
import {addMoment} from "@/api/moment.js";
import {message} from "ant-design-vue";
const momentForm = ref({
  content: '',
  visible: true
});
const createMoment = () => {
  console.log(momentForm.value);
  addMoment(momentForm.value).then(res => {
    if(res.data.code === 200) message.success('发布成功');
  })
}
const change=()=>{
  momentForm.visible =!momentForm.visible;
}
</script>