<template>
  <div class="container">
    <div class="header page-main-container">
      <button class="head-btn "
              v-for="(item, index) in menuItems"
              :key="index"
              :class="{ active: index === activeIndex }"
              @click="handleClick(item,index)"
      >
        {{ item }}
      </button>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue'
import {useRouter} from 'vue-router'

const router = useRouter()
const menuItems = ref(['Home', 'Blog', 'Records', 'Moments', 'Links', 'Guestbook', 'About', 'Apps'])
const activeIndex = ref(0)

function handleClick(name, index) {
  if (name === 'Home') router.push('/')
  else router.push('/' + name)
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
    font-size: 1.5em;
  }

}
.container {
  display: flex;
  flex-direction: row;
  width: 100%;
  height: 8vh;
  position: sticky;
  top: 6px;
  background: var(--background-color);
  z-index: 175;
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
}
.header {
  display: flex;
  flex-direction: row;
  margin-top: 20px;
}

.head-btn {
  cursor: pointer;
  border: none;
  margin-right: calc(2vw);
  border-radius: 3px;
  background: var(--background-color);
  color: var(--text-color);
  height: fit-content;
}

.head-btn:hover {
  background: var(--hover-background-color);
  color: var(--hover-color);
}

.head-btn:focus {
  background: var(--active-background-color);
  color: var(--active-color);
}

.head-btn.active {
  background: var(--active-background-color);
  color: var(--active-color);
}

</style>