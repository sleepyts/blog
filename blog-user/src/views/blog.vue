<script setup>
import '../assets/theam.css'
import BlogCard from "@/components/Blog/BlogCard.vue";
import {provide, ref, onMounted, watch} from 'vue';
import Title from "@/components/Common/Title.vue";
import BlogHeader from "@/components/Common/BlogHeader.vue";
import {getCategories} from "@/api/category.js";
import store from "@/store/index.js";
import {BarsOutlined} from "@ant-design/icons-vue";

const searchText = ref('');
const options = ref([]);
const value = ref([0]);
const blogList = ref([]);
const filteredBlogs =ref([]);
const categoryName = ref('');
provide(blogList,"blogList");
onMounted(async () => {
  await store().dispatch("getBlogList")
  await getCategories().then(res => {
    if (res.data.code === 200) {
      options.value.push({
        label: '全部',
        value: 0
      })
      for (let i = 0; i < res.data.data.length; i++) {
        options.value.push({
          label: res.data.data[i].name,
          value: res.data.data[i].id
        })
      }
      // console.log(options.value)
    }
  });
  blogList.value = await store().state.blogList;
  categoryName.value = '全部';
  await filterBlogsByCategory()
  //console.log(store().state.blogList)
});

//
// const filteredBlogs = computed(() => {
//   const blogList = store().state.blogList; // 获取 store 中的 blogList
//   return blogList.filter(blog => {
//     return blog.title.toLowerCase().includes(searchText.value.toLowerCase());
//   });
// });
watch(value, async () => {
  const selectedCategory = options.value.find(option => option.value === value.value[0]);
  categoryName.value = selectedCategory ? selectedCategory.label : '';
  await filterBlogsByCategory();
});
watch(searchText, () => {
   filteredBlogs.value = blogList.value.filter(blog => {
    return blog.title.toLowerCase().includes(searchText.value.toLowerCase());
  });
});
async function filterBlogsByCategory() {
  if(categoryName.value === '全部') filteredBlogs.value = blogList.value;
  else if (categoryName.value!=='') {
    filteredBlogs.value = blogList.value.filter(blog => {
      return blog.categoryName === categoryName.value;
    });
  } else {
    filteredBlogs.value = blogList.value;
  }
}
</script>
<template>
  <div class="app">
    <blog-header ></blog-header>
    <Title title="Blog"></Title>
    <div class="blogContent page-main-container">
      <input class="searchInput" type="text" placeholder="Search..." v-model="searchText">
      <a-cascader v-model:value="value"
                  :options="options"
                  style="width: 250px;margin-top:1em;"
                  :allowClear="false"
                  :expandTrigger="'hover'"
      />
      <span style="margin-left: 1em">({{filteredBlogs.length}})</span>
      <div class="blogCards">
        <blog-card v-for="blog in filteredBlogs" :blog="blog" class="blogCard"/>
      </div>
    </div>
  </div>
</template>

<style scoped>
@media (max-width: 768px) {
  .blogContent{
    position: relative;
    margin-top: 3em;
  }
}
@media (min-width: 768px) {
  .blogContent{
    position: relative;
    margin-top: 3em;
  }

}
.searchInput {
  padding: 0.7em;
  position: relative;
  width:100%;
  border-radius: 5px;
  border: 1px solid var(--border-color);
  background-color: var(--colorBgContainer);
  color: var(--text-color);
  transition: border-color 0.2s ease-in-out;
}
.searchInput:focus,.searchInput:active {
  outline: none;
  border: 1px solid teal;
}
.blogCards {
   padding: 0.7em;
}
.blogCard {
  margin-top: 1em;
  padding: 0.4em;
  border : 1px solid var(--border-color);
  border-radius: 5px;
  background-color: var(--colorBgContainer);
  transition-property: transform;
  transition-duration: 0.1s;
  animation: blogCardsSlideInFromBottom 0.3s ease-in-out;
}
.blogCard:hover {
  transform: scale(1.010);
  backdrop-filter: blur(8px);
  box-shadow: var(--card-border-shadow); /* 设置阴影样式 */
  cursor: pointer;
}
.blogCard:active {
  transform: scale(0.995);
}
</style>

