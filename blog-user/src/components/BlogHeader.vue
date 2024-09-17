<template>
  <div class="container">
    <div class="page-main-container header ">
      <div v-for="(item, index) in menuItems" :key="index" class="head-btn" :class="{ active: index === activeIndex }"
        @click="handleClick(item, index)">
        <button class="btn" :class="{ active: index === activeIndex }" v-if="!isMobile()">
          {{ item }}
        </button>
      </div>
    </div>
    <div class="header-line"></div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { HomeOutlined } from '@ant-design/icons-vue'
const router = useRouter()
const menuItems = ref(['Home', 'Blog', 'Records', 'Moments', 'Links', 'Guestbook', 'About', 'Apps'])
const activeIndex = ref(0)

function handleClick(name, index) {
  if (name === 'Home') router.push('/')
  else router.push('/' + name)
}
const isMobile = () => {
  return /Mobi|Android|iPhone|iPad|iPod/.test(navigator.userAgent);
}
onMounted(() => {
  const url = window.location.pathname.split('/')[1]
  if (url === '') {
    activeIndex.value = 0
  } else activeIndex.value = menuItems.value.indexOf(url)
})
</script>

<style scoped>
@media (max-width: 768px) {
  .head-btn {
    font-size: 1em;
  }
}

@media (min-width: 769px) {
  .head-btn {
    font-size: 1.3em;
  }

  .page-main-container {
    width: 40%;
    margin-left: 20%;
  }

}
.header-line{
  width: 100%;
  height: 1px;
  background: var(--border-color);
  position: sticky;
}
.container {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 8vh;
  position: sticky;
  top: 6px;
  background: #fff;
  z-index: 175;

}

.header {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  height: 100%;
}

.btn {
  cursor: pointer;
  border: none;
  border-radius: 3px;
  background: var(--background-color);
  color: var(--text-color);
}

.btn:hover {
  background: var(--hover-background-color);
  color: var(--hover-color);
}

.btn:focus {
  background: var(--active-background-color);
  color: var(--active-color);
}

.btn.active {
  background: var(--active-background-color);
  color: var(--active-color);
}
</style>