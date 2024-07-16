<script setup>
import {ref, defineProps} from 'vue'

const props = defineProps({
  friend: {
    type: Object,
    required: true,
  }
})
const handleClick = () => {
  window.open(props.friend.url,'_blank')
}
</script>

<template>
  <div class="friend-card" @click="handleClick">
    <div style="height: 3.5em">
      <img :src="props.friend.avatar" style="height: 100%;width: auto" alt=""/>
    </div>
    <div style="padding: 0.5em;display: flex;flex-direction: column;">
      <span class="name">{{props.friend.name}}</span>
      <span style="font-size: 0.8em">{{props.friend.description}}</span>
    </div>
  </div>
</template>

<style scoped>
.friend-card {
  display: flex;
  flex-direction: row;
  margin-left: 10px;
  margin-bottom: 10px;
  cursor: pointer;
  margin-top: 1em;
  border : 1px solid var(--border-color);
  border-radius: 5px;
  background-color: var(--colorBgContainer);
  transition-property: all;
  transition-duration: 0.15s;
}
@media (max-width: 768px) {
  .friend-card {
    width: 100%;
  }
}
@media (min-width: 768px) {
  .friend-card {
    width: calc(50% - 10px);
  }
}
.friend-card:hover {
  backdrop-filter: blur(8px);
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.3); /* 设置阴影样式 */
  cursor: pointer;
}
.friend-card:active{
  scale: 0.95;
}
.friend-card:hover .name{
  color: red;
  transition: color 0.15s ease-in-out;
}
</style>