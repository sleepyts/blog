<template>
  <div class="container">
    <div class="header page-main-container" >
      <div class="button-container">
        <button class="head-btn head-btn-mobile "
                v-for="(item, index) in menuItems"
                :key="index"
                :class="{ active: index === activeIndex }"
                @click="handleClick(item,index)"
        >
          {{ item }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref,onMounted } from 'vue'
import { useRouter } from 'vue-router'
import BlackAndWhiteButton from "@/components/Common/BlackAndWhiteButton.vue";

const router = useRouter()
const menuItems = ref(['Home', 'Blog','Records','Guestbook' ,'About','Apps'])
const activeIndex = ref(0)

function handleClick(name,index) {
  if(name==='Home') router.push('/')
  else router.push('/'+name)
}

onMounted(() => {
  const url = window.location.pathname.split('/')[1]
  if (url === '') {
    activeIndex.value = 0
  } else activeIndex.value = menuItems.value.indexOf(url)
})
</script>

<style scoped>

.container {
  display: flex;
  flex-direction: row;
  width: 100%;
  height: 4em;
  position: sticky;
  top: 6px;
  background: var(--background-color);
  z-index: 175;
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
}

@media (max-width: 768px) {
  .header {
    position: relative;
    margin-left: 5%;
    width: 95%;
    top:20px;
    display: flex;
  }

}

@media (min-width: 769px) {
  .header {
    position: relative;
    top:20px;
    display: flex;
  }
}
.button-container {
  width: 100%;
}


.head-btn {
  cursor: pointer;
  border: none;
  margin-right: 1em;
  font-size: 1.5em;
  border-radius: 3px;
  background: var(--background-color);
  color: var(--text-color);
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

@media (max-width: 768px){
  .head-btn-mobile {
    font-size: 0.9em;
  }

}
</style>