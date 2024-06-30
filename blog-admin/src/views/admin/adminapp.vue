<template>
  <a-space>
    <a-button type="primary" @click=showNewModal>新增</a-button>
    <a-modal v-model:open="newOpen" title="新增应用" @ok="handleNew">
      <a-form-item label="应用名称">
        <a-input v-model:value="newApp.title" placeholder="请输入应用名称" />
      </a-form-item>

      <a-form-item label="应用url">
        <a-input v-model:value="newApp.url" placeholder="请输入应用url" />
      </a-form-item>

      <a-form-item label="应用简介">
        <a-input v-model:value="newApp.content" placeholder="请输入应用简介" />
      </a-form-item>
    </a-modal>

    <a-modal v-model:open="editOpen" title="编辑应用" @ok="handleEdit">
      <a-form-item label="应用名称">
        <a-input v-model:value="editApp.title" placeholder="请输入应用名称" />
      </a-form-item>

      <a-form-item label="应用url">
        <a-input v-model:value="editApp.url" placeholder="请输入应用url" />
      </a-form-item>

      <a-form-item label="应用简介">
        <a-input v-model:value="editApp.content" placeholder="请输入应用简介" />
      </a-form-item>
    </a-modal>
  </a-space>

  <a-table :columns="columns" :data-source="data">
    <template #bodyCell="{ column, record }">
      <template v-if="column.key === 'action'">
        <span>
          <a-divider type="vertical" />
          <a-button type="primary" @click="startEdit(record.id)">编辑</a-button>
          <a-divider type="vertical" />
          <a-button type="primary" danger @click="handleDelete(record.id)">删除</a-button>
        </span>
      </template>
    </template>
  </a-table>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { createApp, deleteApp, getApps, updateApp } from "@/api/app";
import { message } from "ant-design-vue";
const data = ref([]);
const newOpen = ref(false);
const editOpen = ref(false);
const newApp = ref({
  title: '',
  content: '',
  url: '',
});
const editApp = ref({
  id: '',
  title: '',
  content: '',
  url: '',
});
const columns = [
  {
    title: 'Id',
    dataIndex: 'id',
  },
  {
    title: '标题',
    dataIndex: 'title',
  },
  {
    title: '简介',
    dataIndex: 'content',
  },
  {
    title: 'url',
    dataIndex: 'url',
  },
  {
    title: '操作',
    key: 'action',
  }
];
onMounted(async () => {
  await getDate();
})
const startEdit = (id) => {
  editOpen.value = true;
  editApp.value = data.value.find((item) => item.id === id);
}
const handleEdit = () => {
  editOpen.value = false;
  updateApp(editApp.value).then((res) => {
    if (res.data.code === 200) message.success('编辑成功');
    else message.error(res.data.message);
    getDate();
  })
}
const handleDelete = (id) => {
  deleteApp(id).then((res) => {
    if (res.data.code === 200) message.success('删除成功');
    else message.error(res.data.message);
    getDate();
  })
}
const handleNew = () => {
  createApp(newApp.value).then((res) => {
    if (res.data.code === 200) message.success('新增成功');
    else message.error(res.data.message);
    newOpen.value = false;
    newApp.value = {
      title: '',
      content: '',
      url: '',
    };
    getDate();
  })
}
const showNewModal = () => {
  newOpen.value = true;
}
const getDate = async () => {
  await getApps().then((res) => {
    if (res.data.code === 200) data.value = res.data.data;
    else console.log(res.data.message);
  })
}
</script>

<style scoped></style>
