<script setup>

import {computed, defineProps, onMounted,ref} from "vue";
import CommentsCard from "@/components/Comment/CommentsCard.vue";
import store from "@/store/index.js";

const props = defineProps({
  blogId:{
    type: Number,
    required: true
  },
})
const pageEntity=computed(() => {
  return store().state.pageEntity;
})
const currentPage=ref(1)
const pageSize=ref(8)
const onPageChange= (page,pageSize) => {
  store().dispatch("getCommentsByPage",{page,pageSize})
  window.scrollTo({ top: 0, behavior: 'smooth' });
}

onMounted(async () => {
  if(props.blogId===-1) store().commit("SET_BLOG",-1);
  await store().dispatch("getCommentsByPage", {page: 1, pageSize: 8})
})


</script>

<template>
  <div class="comment-container page-main-container" >
    <div class="comment-num">
      <span >{{ pageEntity.total }}{{props.blogId!==-1?'评论':'留言'}}</span>
    </div>
    <div class="dynamic-line"></div>
    <div class="comment-list" style="margin-top: 20px;">
        <div v-if="pageEntity.total>0">
          <div v-for="comment in pageEntity.rows"  :key="comment.id">
            <CommentsCard :comment=comment v-if="comment.replyId===-1"></CommentsCard>
          </div>
        </div>
      <a-pagination  id="pagination"
              v-model:current=currentPage :total="pageEntity.total"
                     v-model:page-size="pageSize"
              @change="onPageChange"
                     simple
                     style="text-align: center; margin-top: 20px;"
      >
      </a-pagination>
    </div>
  </div>
</template>

<style>
@media (max-width: 768px) {
  .comment-container{
   margin-top : 90px; ; background-color: var(--colorBgContainer)
  }
}
@media (min-width: 768px) {
  .comment-container{
    margin-top : 90px;  background-color: var(--colorBgContainer)
  }
}
.comment-num{
  display: flex;
}
.comment-container:hover .dynamic-line {
  background-size: 100% 2px;
}
#pagination *{
  box-shadow: none;
}
</style>