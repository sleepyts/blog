<script setup>
import '../assets/theam.css'
import BlogCard from "@/components/Blog/BlogCard.vue";
import { provide, ref, onMounted, watch } from 'vue';
import Title from "@/components/Title.vue";
import BlogHeader from "@/components/BlogHeader.vue";
import { getCategories } from "@/api/category.js";
import store from "@/store/index.js";
import { BarsOutlined } from "@ant-design/icons-vue";
import { getBlogList } from "@/api/blog.js";
const searchText = ref('');
const options = ref([]);
const value = ref([0]);
const blogList = ref([]);
const filteredBlogs = ref([]);
const categoryName = ref('');
const currentPage = ref(1);
const total = ref(0);
const currentTotal = ref(0);
onMounted(async () => {
  await getBlogList(currentPage.value).then(res => {
    if (res.data.code === 200) {
      blogList.value = res.data.data.rows
      total.value = res.data.data.total
    }
  })

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
    }
  });
  categoryName.value = '全部';
  await filterBlogsByCategory()
});

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
  currentPage.value = 1;
  if (categoryName.value === '全部') {
    currentTotal.value = total.value;
    filteredBlogs.value = blogList.value;
  }
  else if (categoryName.value !== '') {
    filteredBlogs.value = blogList.value.filter(blog => {
      return blog.categoryName === categoryName.value;
    });
    currentTotal.value = filteredBlogs.value.length;
  } else {
    filteredBlogs.value = blogList.value;
    currentTotal.value = total.value;
  }

}

const onPageChange = async (page) => {
  await getBlogList(page).then(res => {
    if (res.data.code === 200) blogList.value = res.data.data.rows
  });
  await filterBlogsByCategory();
  currentPage.value = page;
  window.scrollTo({ top: 0, behavior: 'smooth' });
}
</script>
<template>
  <div class="app">
    <blog-header></blog-header>
    <Title title="Blog"></Title>
    <div class="blogContent page-main-container">
      <input class="searchInput" type="text" placeholder="Search..." v-model="searchText">
      <a-cascader v-model:value="value" :options="options" style="width: 250px;margin-top:1em;" :allowClear="false"
        :expandTrigger="'hover'" />
      <span style="margin-left: 1em">({{ filteredBlogs.length }})</span>
      <div class="blogCards">
        <blog-card v-for="blog in filteredBlogs" :blog="blog" class="blogCard" />
      </div>
      <a-pagination v-model:current=currentPage :total="currentTotal" @change="onPageChange" simple
        style="text-align: center; margin-top: 20px;">
      </a-pagination>

    </div>
  </div>
</template>

<style scoped>
@media (max-width: 768px) {
  .blogContent {
    position: relative;
    margin-top: 3em;
  }
}

@media (min-width: 768px) {
  .blogContent {
    position: relative;
    margin-top: 3em;
  }

}

.searchInput {
  padding: 0.7em;
  position: relative;
  width: 100%;
  border-radius: 5px;
  border: 1px solid var(--border-color);
  background-color: var(--colorBgContainer);
  color: var(--text-color);
  transition: border-color 0.2s ease-in-out;
}

.searchInput:focus,
.searchInput:active {
  outline: none;
  border: 1px solid teal;
}

.blogCards {
  padding: 0.7em;
}

.blogCard {
  margin-top: 1em;
  padding: 0.4em;
  border: 1px solid var(--border-color);
  border-radius: 5px;
  background-color: var(--colorBgContainer);
  transition-property: transform;
  transition-duration: 0.1s;
  animation: blogCardsSlideInFromBottom 0.3s ease-in-out;
}

.blogCard:hover {
  transform: scale(1.010);
  box-shadow: var(--card-border-shadow);
  cursor: pointer;
}

.blogCard:active {
  transform: scale(0.995);
}
</style>
