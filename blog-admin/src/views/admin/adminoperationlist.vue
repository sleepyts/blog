<script setup>
import { ref, onMounted } from 'vue';
import {message} from "ant-design-vue";
import {deleteException, getException} from "@/api/exception.js";
import {deleteOperation, getOperation} from "@/api/operation.js";
const data = ref([]);
const columns = [
  {
    title: 'Id',
    dataIndex: 'id',
  },
  {
    title: '操作用户',
    dataIndex: 'username',
  },
  {
    title: '操作方式',
    dataIndex: 'operationName',
  },
  {
    title: 'ip',
    dataIndex: 'ip',
  },
  {
    title: 'user-Agent',
    dataIndex: 'userAgent',
  },
  {
    title: '方法名',
    dataIndex: 'methodName',
  },
  {
    title: '操作时间',
    dataIndex: 'operationTime',
  },
  {
    title: '操作',
    key: 'action',
  }
];
onMounted(() => {
  getOperation().then(res => {
    if(res.data.code === 200)
      data.value = res.data.data;
    else message.error(res.data.message);
  })
})
const handleDelete = (id) => {
  deleteOperation(id).then(res => {
    if (res.data.code === 200) {
      message.success('删除成功');
      getOperation().then(res => {
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