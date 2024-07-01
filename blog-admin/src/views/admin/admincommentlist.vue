<template>
  <a-space>
    <a-cascader v-model:value="value"
                :options="options"
                placeholder="选择文章查看评论"
                style="width: 300px"
    />
    <span>评论总数：{{ data.length }}</span>
  </a-space>
  <a-table
      :columns="columns"
      :data-source="data"
  >
    <template #bodyCell="{column, record}">
      <template v-if="column.title === '创建时间'">
        <span>{{new Date(record.createTime).toLocaleString()}}</span>
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

<script setup>
import {onMounted, ref, watch} from 'vue';
import {message} from "ant-design-vue";
import {deleteComment, getComment} from "@/api/comment.js";
import {getBlogThumbnail} from "@/api/blog.js";

const blogId = ref(-1);
onMounted(async () => {
  await getComment(blogId.value).then((res) => {
    if (res.data.code === 200) data.value = res.data.data;
  });
  await getBlogThumbnail().then((res) => {
    if (res.data.code === 200) options.value = res.data.data;
  });
  options.value.unshift({
    label: '留言版',
    value: -1
  })
});
const options = ref([]);
const value = ref([-1]);
const data = ref([]);
watch(value, async () => {
  blogId.value = value.value[0];
  await getComment(blogId.value).then((res) => {
    if (res.data.code === 200) data.value = res.data.data;
  })
})
const columns = [
  {
    title: 'Id',
    dataIndex: 'id',
  },
  {
    title: '昵称',
    dataIndex: 'name',
    width: 120,
  },
  {
    title: '内容',
    dataIndex: 'content',
  },
  {
    title: '回复ID',
    dataIndex: 'replyId',
  },
  {
    title: 'url',
    dataIndex: 'url',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    width: 180,
  },
  {
    title: '操作',
    key: 'action',
  }
];
const handleDelete = (id) => {
  deleteComment(id).then((res) => {
    if (res.data.code === 200){
      getComment(blogId.value).then((res) => {
        if (res.data.code === 200) data.value = res.data.data;
      });
      message.success('删除成功');
    }
  });
}

</script>