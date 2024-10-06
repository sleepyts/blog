<script setup>
import {onMounted, ref} from 'vue';
import {deleteVisitor, getVisitor} from "@/api/visitor.js";
import {message} from "ant-design-vue";
import {getVisitorLog} from "@/api/operation.js";

const data = ref([]);
const total= ref();
const currentPage=ref()
const pageSize=ref()
const columns = [
  {
    title: 'ip地址',
    dataIndex: 'ip',
  },
  {
    title: '物理地址',
    dataIndex: 'address',
  },
  {
    title: '访问方法',
    dataIndex: 'methodName'
  },
  {
    title: '参数',
    dataIndex: 'argsAndValue'
  },
  {
    title: '操作系统',
    dataIndex: 'os',
  },
  {

    title: '浏览器',
    dataIndex: 'browser'
  },
  {
    title: '后端处理时间',
    dataIndex: 'processTime'
  },
  {
    title: '访问时间',
    dataIndex: 'firstVisitTime',
    width:180
  },
];
onMounted(async () => {
  await getData();
})
const getData = () => {
  getVisitorLog(1,10).then(res => {
    if(res.data.code === 200) {
      data.value = res.data.data.rows;
      total.value=res.data.data.total
    }
  })
}
const onPageChange = (page, size) =>{
  currentPage.value=page
  pageSize.value=size
  getVisitorLog(page,size).then(res => {
    if(res.data.code === 200) {

      data.value = res.data.data.rows;
      total.value=res.data.data.total
    }
  })
}
</script>

<template>
  <a-table :columns="columns"
           :data-source="data">
    <template #bodyCell="{column, record}">
      <template v-if="column.title === '访问时间'">
        <span>{{ new Date(record.createTime).toLocaleString() }}</span>
      </template>
      <template v-if="column.title === '后端处理时间'">
        <span>{{record.processTime+"ms"}}</span>
      </template>
    </template>

  </a-table>
  <a-pagination v-model:current=currentPage
                :total="total"
                @change="onPageChange"
                :page-size="pageSize"
                :show-total="total => `总共 ${total}`"
                style="padding: 20px"
  >
  </a-pagination>
</template>

<style scoped>

</style>