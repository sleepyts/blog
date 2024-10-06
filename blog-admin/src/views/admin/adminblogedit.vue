<template>
  <a-form :model="editForm" :label-col="labelCol" :wrapper-col="wrapperCol">
    <a-form-item label="文章标题" required>
      <a-input v-model:value="editForm.title" placeholder="Please enter title" />
    </a-form-item>
    <a-form-item label="文章封面URL">
      <a-input v-model:value="editForm.img" placeholder="Please enter image URL" />
    </a-form-item>
    <a-form-item label="文章类别">
      <a-cascader v-model:value="value" :options="options" placeholder="类别" :allowClear="false" />
    </a-form-item>
    <a-form-item label="文章简介">
      <a-textarea v-model:value="editForm.description" placeholder="Please enter description" />
    </a-form-item>
    <MdEditor v-model="editForm.content" @onUploadImg="onUploadImg" :preview-theme="'vuepress'" />
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" @click="onSubmit">提交</a-button>
      <a-button style="margin-left: 8px;" href="https://image.tsukiyo.cn" target="_blank">去传图片</a-button>
    </a-form-item>
  </a-form>
</template>
<script setup>
import { onMounted, ref, toRaw, watch } from 'vue';
import { message } from "ant-design-vue";
import { MdEditor } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { getCategories } from "@/api/category.js";
import { onRequestPost } from "@/api/upload-2.0.js";
import { getBlogContent, updateBlog } from "@/api/blog.js";

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

  // 方式二
  // callback(
  //   res.map((item: any) => ({
  //     url: item.data.url,
  //     alt: 'alt',
  //     title: 'title'
  //   }))
  // );
};
const props = defineProps({
  editForm: {
    type: Object,
    default: () => ({})
  }
})
const text = ref('');
const onSubmit = () => {
  updateBlog(props.editForm).then(res => {
    if (res.data.code === 200) {
      message.success('文章编辑成功！');
    }
  })
};
const labelCol = {
  style: {
    width: '150px',
  },
};
const wrapperCol = {
  span: 14,
};
const options = ref([]);
const value = ref([]);
onMounted(async () => {
  await getCategories().then(res => {
    if (res.data.code === 200) {
      for (let i = 0; i < res.data.data.length; i++) {
        options.value.push({
          label: res.data.data[i].name,
          value: res.data.data[i].id
        })
      }
      // console.log(options.value)
    }
  });
  const p = options.value.find(item => item.label === props.editForm.categoryName);
  value.value = [p.value];
  //console.log(store().state.blogList)
});
watch(value, () => {
  props.editForm.categoryId = toRaw(value.value[0]);
});
</script>