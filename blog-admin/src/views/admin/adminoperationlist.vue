<script setup>
import { onMounted, ref } from 'vue';
import { message } from "ant-design-vue";
import { deleteOperation, getOperation } from "@/api/operation.js";
import dayjs from "dayjs";

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
    const start = new Date(dateStrings[0])
    const end = new Date(dateStrings[1])
    start.setHours(0, 0, 0, 0)
    end.setHours(23, 59, 59, 999)
    getOperation(start, end).then(res => {
      if (res.data.code === 200)
        data.value = res.data.data;
      else message.error(res.data.message);
    })
  } else {
    getOperation().then(res => {
      if (res.data.code === 200)
        data.value = res.data.data;
      else message.error(res.data.message);
    })
  }
};
const columns = [
  {
    title: '用户',
    dataIndex: 'userName',
    width: 80,
  },
  // {
  //   title: '操作方式',
  //   dataIndex: 'operationName',
  //   width: 100,
  // },
  // {
  //   title: 'ip',
  //   dataIndex: 'ip',
  // },
  // {
  //   title: 'user-Agent',
  //   dataIndex: 'userAgent',
  // },
  {
    title: '方法名',
    dataIndex: 'methodName',
  },
  {
    title: '操作时间',
    dataIndex: 'operationTime',
    width: 180,
  },
  {
    title: '操作',
    key: 'action',
  }
];
onMounted(() => {
  getOperation().then(res => {
    if (res.data.code === 200)
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
  <a-space direction="vertical" :size="12">
    <a-range-picker :presets="rangePresets" @change="onRangeChange" />
  </a-space>
  <a-table :columns="columns" :data-source="data">
    <template #bodyCell="{ column, record }">
      <template v-if="column.title === '操作时间'">
        <span>{{ new Date(record.operationTime).toLocaleString() }}</span>
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

<style scoped></style>