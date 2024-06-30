<template>
    <a-space>
        <span style="font-size: 20px;font-weight: bold;">关于页面正文设置</span>
        
    </a-space>
    <MdEditor v-model="text" @onUploadImg="onUploadImg" :preview-theme="'vuepress'" />
    <a-button type="primary" @click="handleSubmit" style="margin-top: 20px;">保存</a-button>
    
</template>

<script setup>
import {ref,onMounted} from 'vue'
import { MdEditor } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { message } from 'ant-design-vue'
import {onRequestPost} from "@/api/upload-2.0.js";
import {getAboutData, updateAboutData} from "@/api/about.js";
const text=ref("")

onMounted(async () => {
  await getAboutData().then(res => {
    if(res.data.code===200) text.value=res.data.data
    else console.log(res.data.msg)
  })
})
const onUploadImg = async (files, callback) => {
  const res = await Promise.all(
      files.map((file) => {
        return new Promise((rev, rej) => {
          const form = new FormData();
          form.append('file', file);

          onRequestPost(form)
              .then((res) => rev(res))
              .catch((error) => rej(error));
        });
      })
  );
  // 方式一
  callback(res.map((item) => item.url));
};
const handleSubmit = () => {
    updateAboutData(text.value).then(res => {
        if(res.data.code===200) message.success("保存成功")
    })
}
</script>