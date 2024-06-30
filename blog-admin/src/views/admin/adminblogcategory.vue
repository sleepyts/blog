<template>
  <a-space style="margin-bottom: 16px">
    <a-button type="primary" @click="showModal">新增</a-button>
    <a-modal v-model:open="open" title="新增分类" @ok="handleOk">
      <a-input v-model:value="newCategory.name" placeholder="请输入分类名称"/>
    </a-modal>
    <a-modal v-model:open="open1" title="编辑分类" @ok="handleOk1">
      <a-input v-model:value="newCategory.name" placeholder="请输入分类名称"/>
    </a-modal>
  </a-space>

  <a-table
      :columns="columns"
      :data-source="data"
  >
    <template #bodyCell="{column, record}">
      <template v-if="column.key === 'action'">
          <span>
            <a-divider type="vertical"/>
            <a-button type="primary" @click="handleEdit(record.id)">编辑</a-button>
            <a-divider type="vertical"/>
            <a-button type="primary" danger @click="handleDelete(record.id)">删除</a-button>
          </span>
      </template>
    </template>
  </a-table>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {addCategory, deleteCategory, getCategories, updateCategory} from "@/api/category.js";
import {message} from "ant-design-vue";

const data = ref([]);
const columns = [
  {
    title: 'Id',
    dataIndex: 'id',
  },
  {
    title: '名称',
    dataIndex: 'name',
  },
  {
    title: '操作',
    key: 'action',
  }
];
const open = ref(false);
const open1=ref(false)
const newCategory = ref({
  id: '',
  name: '',
});
onMounted(() => {
  getCategories().then(res => {
    if (res.data.code === 200) data.value = res.data.data;
  })
})

const showModal = () => {
  open.value = true;
};
const handleOk = async e => {
  await addCategory(newCategory.value).then(res => {
    if (res.data.code === 200) message.success('新增成功');
  })
  getCategories().then(res => {
    if (res.data.code === 200) data.value = res.data.data;
  })
  newCategory.value = {
    name: '',
  };
  open.value = false;
};
const handleDelete = async (id) => {
  await deleteCategory(id).then(res => {
    if (res.data.code === 200) message.success('删除成功');
  })
  getCategories().then(res => {
    if (res.data.code === 200) data.value = res.data.data;
  })
}
const handleEdit = (id) => {
  open1.value=true
  newCategory.value=data.value.find(item=>item.id===id)
}
const handleOk1 = async e => {
  await updateCategory(newCategory.value).then(res => {
    if (res.data.code === 200) message.success('编辑成功');
  })
  getCategories().then(res => {
    if (res.data.code === 200) data.value = res.data.data;
  })
  newCategory.value = {
    name: '',
  };
  open1.value = false;
}
</script>