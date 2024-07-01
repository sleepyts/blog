<template>
  <div v-if="isEdit">
    <adminblogedit :edit-form="editBlog"></adminblogedit>
    <a-button type="primary" @click="isEdit=false">取消</a-button>
  </div>
  <div v-else>
    <a-space>
      <span style="font-size: 32px;">文章总数: {{ data.length }}</span>
    </a-space>
    <a-table
        :columns="columns" `
        :data-source="data"
    >


      <template #bodyCell="{column, record}">
        <template v-if="column.title === '发表日期'">
          <span>{{new Date(record.createTime).toLocaleString()}}</span>
        </template>
        <template v-if="column.title === '封面'">
          <a-image v-if="record.img" :src="record.img" :width=100 alt="cover"/>
          <span v-else>无</span>
        </template>
        <template v-if="column.key === 'action'">
          <span>
            <a-divider type="vertical"/>
            <a-button type="primary" @click="handleEdit(record)">编辑</a-button>
            <a-divider type="vertical"/>
            <a-button type="primary" danger @click="handleDelete(record.id)">删除</a-button>
            <a-divider type="vertical"/>
          </span>
        </template>
      </template>
    </a-table>
  </div>
</template>
<script setup>
import { provide, onMounted, ref} from 'vue';
import {message} from "ant-design-vue";
import {deleteBlog, getBlog} from "@/api/blog.js";
import Adminblogedit from "@/views/admin/adminblogedit.vue";

const columns = [
  {
    title: 'Id',
    dataIndex: 'id',
    width: 20
  },
  {
    title: '文章标题',
    dataIndex: 'title',
  },
  {
    title: '简介',
    dataIndex: 'description',
  },
  {
    title: '类别',
    dataIndex: 'categoryName',
  },
  {
    title: '封面',
    dataIndex: 'img',
  },
  {
    title: '发表日期',
    dataIndex: 'createTime',
    width: 170,
  },
  {
    title: '操作',
    key: 'action',
    width: 200,
  }
];
const editBlog = ref({})
const data = ref([]);
const isEdit = ref(false)
provide("editBlog", editBlog)

onMounted(() => {
  getData();
  // console.log(store().state.blogList);
})
const getData = async () => {
  await getBlog().then((res) => {
    if (res.data.code === 200) {
      data.value = res.data.data;
    }
  })
}
const handleEdit = (record) => {
  editBlog.value = record;
  isEdit.value = true;

};
const handelOk = () => {
  open.value = false;
}
const handleDelete = (id) => {
  deleteBlog(id).then((res) => {
    if (res.data.code === 200) {
      getData();
      message.success("删除成功");
    }
  })
};
</script>