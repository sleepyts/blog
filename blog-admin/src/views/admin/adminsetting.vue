<template>
  <div style="display: flex; flex-direction: column;">
    <div style="width: 20%;" class="settings-container">
      <span style="font-weight: bold;">关于页面歌曲网易云ID</span>
      <a-input v-model:value="settings.aboutMePageSongId"></a-input>
    </div>
    <div  class="settings-container">
      <span style="font-weight: bold;">ICP备案号</span>
      <a-input v-model:value="settings.icp"></a-input>
    </div>
    <div  class="settings-container">
      <span style="font-weight: bold;">关于页面正文设置</span>
      <MdEditor v-model="settings.aboutMe" @onUploadImg="onUploadImg" :preview-theme="'vuepress'" style="height: 700px;"/>
    </div>
    <a-button type="primary" @click="handleSubmit" style="margin-top: 20px; width: 100px;">保存</a-button>
  </div>

</template>

<script setup>
import {ref, onMounted} from 'vue'
import {MdEditor} from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import {message} from 'ant-design-vue'
import {onRequestPost} from "@/api/upload-2.0.js";
import {getSettings, updateSettings} from "@/api/settings.js";

const text = ref("")
const settings = ref({
  aboutMePageSongId: "",
  icp: "",
  aboutMe: ""
})
onMounted(async () => {
  await getSettings().then(res => {
    if (res.data.code === 200) settings.value = res.data.data
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
  updateSettings(settings.value).then(res => {
    if (res.data.code === 200) message.success("保存成功")
  })
}
</script>

<style scoped>
.settings-container {
  margin-top: 20px;
}
</style>