<script setup>
import {onMounted, ref} from 'vue';
import {message} from "ant-design-vue";
import {deleteException, getException} from "@/api/exception.js";
import dayjs from "dayjs";
import {getOperation} from "@/api/operation.js";

const data = ref([]);
const rangePresets = ref([
  {
    label: '最近7天',
    value: [dayjs().add(-7, 'd'), dayjs()],
  },
  {
    label: '最近14天',
    value: [dayjs().add(-14, 'd'), dayjs()],
  },
  {
    label: '最近30天',
    value: [dayjs().add(-30, 'd'), dayjs()],
  },
  {
    label: '最近90天',
    value: [dayjs().add(-90, 'd'), dayjs()],
  },
]);
const onRangeChange = (dates, dateStrings) => {
  if (dates) {
    const start=new Date(dateStrings[0])
    const end=new Date(dateStrings[1])
    start.setHours(0,0,0,0)
    end.setHours(23,59,59,999)
    getException(start, end).then(res => {
      if(res.data.code === 200)
        data.value = res.data.data;
      else message.error(res.data.message);
    })
  }else{
    getException().then(res => {
      if(res.data.code === 200)
        data.value = res.data.data;
      else message.error(res.data.message);
    })
  }
};
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
onMounted(async () => {
  await getException().then(res => {
    if (res.data.code === 200) data.value = res.data.data
  })
})
const handleDelete = (id) => {
  deleteException(id).then(res => {
    if (res.data.code === 200) {
      message.success('删除成功');
      getException().then(res => {
        if (res.data.code === 200)
          data.value = res.data.data;
      })
    }
  })
}
</script>

<template>
  <a-space direction="vertical" :size="12">
    <a-range-picker :presets="rangePresets" @change="onRangeChange" />
  </a-space>
  <a-table
      :columns="columns"
      :data-source="data"
  >
    <template #bodyCell="{column, record}">
      <template v-if="column.title === '发生时间'">
        <span>{{ new Date(record.createTime).toLocaleString() }}</span>
      </template>
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