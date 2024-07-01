<script setup>
import {onMounted, ref} from 'vue';
import {deleteVisitor, getVisitor} from "@/api/visitor.js";
import {message} from "ant-design-vue";

const data = ref([]);
const columns = [
  {
    title: 'ip地址',
    dataIndex: 'ip',
  },
  {
    title: '物理地址',
    dataIndex: 'address',
    width:220
  },
  {
    title: 'user-Agent',
    dataIndex: 'userAgent',
    ellipsis: true,
  },
  {
    title: '首次访问',
    dataIndex: 'firstVisitTime',
    width:180
  },
  {
    title: '最后访问',
    dataIndex: 'lastVisitTime',
    width:180
  },
  {
    title: '操作',
    key: 'action',
    width: 100,
  }
];
onMounted(async () => {
  await getData();
})
const getData = () => {
  getVisitor().then(res => {
    if(res.data.code === 200) data.value = res.data.data;
  })
}
const handleDelete = (id) => {
  deleteVisitor(id).then(res => {
    if(res.data.code === 200) {
      getData();
      message.success('删除成功');
    }
  })
}
</script>

<template>
  <a-table :columns="columns"
           :data-source="data">
    <template #bodyCell="{column, record}">
      <template v-if="column.title === '首次访问'">
        <span>{{ new Date(record.firstVisitTime).toLocaleString() }}</span>
      </template>
      <template v-if="column.title === '最后访问'">
        <span>{{ new Date(record.lastVisitTime).toLocaleString() }}</span>
      </template>
      <template v-if="column.key === 'action'">
          <span>
            <a-divider type="vertical"/>
            <a-button type="primary" danger @click="handleDelete(record.id)">删除</a-button>
          </span>
      </template>
    </template>
  </a-table>
</template>

<style scoped>

</style>