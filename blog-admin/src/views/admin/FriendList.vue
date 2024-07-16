<script setup>
import {ref, onMounted} from 'vue'
import {addFriend, delFriend, getFriend, updateFriend} from "@/api/friend.js";
import {message} from "ant-design-vue";

const data = ref([]);
const openNew = ref(false);
const openEdit = ref(false);
onMounted(() => {
  getData()
})
const form = ref({
  name: '',
  url: '',
  avatar: '',
  description: '',
})
const getData = () => {
  getFriend().then(res => {
    if (res.data.code === 200) data.value = res.data.data;
  })
}

const columns = [
  {
    title: 'id',
    dataIndex: 'id',
  },
  {
    title: '头像',
    dataIndex: 'avatar',
    width: 100
  },
  {
    title: '名称',
    dataIndex: 'name',
  },
  {
    title: '链接',
    dataIndex: 'url',
  },
  {
    title: '描述',
    dataIndex: 'description',
  },

  {
    title: '收录时间',
    dataIndex: 'createTime'
  },
  {
    title: '更新时间',
    dataIndex: 'updateTime'
  },
  {
    title: '操作',
    key: 'action',
  }
];

const handleDelete = (id) => {
  delFriend(id).then(res => {
    if (res.data.code === 200) message.success("删除成功！");
    getData()
  })
}
const handleEdit = (record) => {
  openEdit.value=true
  form.value = record

}
const handleOkEdit = () => {
  openEdit.value = false;
  updateFriend(form.value).then(res =>{
    if (res.data.code===200) message.success("编辑成功");
    getData()
  })
};
const handleNew = () => {
  openNew.value = true;
}
const handleOkNew = () => {
  openNew.value = false;
  addFriend(form.value).then(res => {
    if (res.data.code === 200) message.success("添加成功！");
    getData()
  })
};
</script>

<template>
  <a-modal v-model:open="openNew" title="新增友链" @ok="handleOkNew">
    <a-form-item label="名称">
      <a-input v-model:value="form.name"></a-input>
    </a-form-item>
    <a-form-item label="链接">
      <a-input v-model:value="form.url"></a-input>
    </a-form-item>
    <a-form-item label="头像链接">
      <a-input v-model:value="form.avatar"></a-input>
    </a-form-item>
    <a-form-item label="描述">
      <a-input v-model:value="form.description"></a-input>
    </a-form-item>
  </a-modal>
  <a-modal v-model:open="openEdit" title="友链编辑" @ok="handleOkEdit">
    <a-form-item label="名称">
      <a-input v-model:value="form.name"></a-input>
    </a-form-item>
    <a-form-item label="链接">
      <a-input v-model:value="form.url"></a-input>
    </a-form-item>
    <a-form-item label="头像链接">
      <a-input v-model:value="form.avatar"></a-input>
    </a-form-item>
    <a-form-item label="描述">
      <a-input v-model:value="form.description"></a-input>
    </a-form-item>
  </a-modal>
  <a-space>
    <a-button type="primary" @click="handleNew()">新增</a-button>
  </a-space>

  <a-table :columns="columns"
           :data-source="data">
    <template #bodyCell="{column, record}">
      <template v-if="column.title === '收录时间'">
        <span>{{ new Date(record.createTime).toLocaleString() }}</span>
      </template>
      <template v-if="column.title === '更新时间'">
        <span>{{ new Date(record.updateTime).toLocaleString() }}</span>
      </template>
      <template v-if="column.title === '头像'">
        <div>
          <img width="100%" :src="record.avatar"/>
        </div>

      </template>

      <template v-if="column.key === 'action'">
          <span>
            <a-divider type="vertical"/>
            <a-button type="primary" @click="handleEdit(record)">编辑</a-button>
            <a-divider type="vertical"/>
            <a-button type="primary" danger @click="handleDelete(record.id)">删除</a-button>
          </span>
      </template>
    </template>
  </a-table>
</template>

<style scoped>

</style>