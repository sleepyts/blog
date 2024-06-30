<script setup>
import { ref, onMounted } from 'vue';
import {message} from "ant-design-vue";
import {deleteException, getException} from "@/api/exception.js";
const data = ref([]);
const columns = [
  {
    title: 'Id',
    dataIndex: 'id',
  },
  {
    title: '方法名',
    dataIndex: 'methodName',
  },
  {
    title: '异常信息',
    dataIndex: 'errorMessage',
  },
  {
    title: '异常类型',
    dataIndex: 'errorType',
  },
  {
    title: '发生时间',
    dataIndex: 'createTime',
  },
  {
    title: '操作',
    key: 'action',
  }
];
onMounted(() => {
  getException().then(res => {
    if(res.data.code === 200)
      data.value = res.data.data;
    else message.error(res.data.message);
  })
})
const handleDelete = (id) => {
  deleteException(id).then(res => {
    if (res.data.code === 200) {
      message.success('删除成功');
      getException().then(res => {
        if (res.data.code === 200)
          data.value = res.data.data;
        else message.error(res.data.message);
      })
    } else message.error(res.data.message);
  })
}
</script>

<template>
  <a-table
      :columns="columns"
      :data-source="data"
  >
    <template #bodyCell="{column, record}">
      <template v-if="column.key === 'action'">
          <span>
            <a-divider type="vertical" />
            <a-button type="primary" danger @click="handleDelete(record.id)">删除</a-button>
          </span>
      </template>
    </template>
  </a-table>
</template>

<style scoped>

</style>