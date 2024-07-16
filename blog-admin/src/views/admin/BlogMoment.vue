<template>
  <a-modal v-model:open="open" title="编辑动态" width="80%" @ok="handleSubmit()">
    <MdEditor v-model="form.content" :preview-theme="'vuepress'"></MdEditor>
  </a-modal>
  <h2>动态总数: {{ data.length }}</h2>
  <a-table :columns="columns"
           :data-source="data">
    <template #bodyCell="{column, record}">
      <template v-if="column.title === '创建时间'">
        <span>{{ new Date(record.createTime).toLocaleString() }}</span>
      </template>
      <template v-if="column.title === '更新时间'">
        <span>{{ new Date(record.updateTime).toLocaleString() }}</span>
      </template>
      <template v-if="column.title === '可见性'">
        <a-switch v-model:checked="record.visible" @change="handleChange(record.id)"/>
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

<script setup>
import { ref , onMounted } from 'vue';
import {changeVisible, deleteMoment, getMoments, updateMoment} from "@/api/moment.js";
import {message} from "ant-design-vue";
import {MdEditor} from "md-editor-v3";
import "md-editor-v3/lib/style.css";

const data = ref([]);
const open = ref(false);
const form = ref({
  id: '',
  content: '',
  visible: true,
  createTime: '',
  updateTime: ''
})
const columns = [
  {
    title: 'id',
    dataIndex: 'id',
  },
  {
    title: '内容',
    dataIndex: 'content',
    width: 600
  },
  {
    title: '可见性',
    dataIndex: 'visible'
  },
  {
    title: '创建时间',
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

const getData= async () => {
  await getMoments().then(res => {
    if (res.data.code === 200) data.value = res.data.data;
  })
}
onMounted(() => {
  getData();
})

const handleChange = (id) => {
  changeVisible(id).then(res => {
    if (res.data.code === 200) message.success('修改成功');
  });
}

const handleDelete = (id) => {
  deleteMoment(id).then(res => {
    if (res.data.code === 200) message.success('删除成功');
    getData();
  });
}
const handleEdit = (record) => {
  open.value = true;
  form.value = record;
}
const handleSubmit = () => {
  updateMoment(form.value).then(res => {
    if (res.data.code === 200) {
      message.success('修改成功');
      open.value = false;
      getData();
    }
  })
}
</script>