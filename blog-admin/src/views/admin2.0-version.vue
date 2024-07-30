<template>
  <a-layout has-sider>
    <a-layout-sider :style="{ overflow: 'auto', height: '100vh', position: 'fixed', left: 0, top: 0, bottom: 0 }">
      <a-menu v-model:selectedKeys="selectedKeys" theme="dark" mode="inline">
        <a-menu-item key="0" @click="$router.push('/dashboard')">
          <span>仪表盘</span>
        </a-menu-item>
        <a-sub-menu key="1">
          <template #title>
            <span>
              <SwitcherOutlined />
              <span>内容管理</span>
            </span>
          </template>
          <a-menu-item key="1-1" @click="$router.push('/blog/list')">
            <ReadOutlined />
            <span>文章列表</span>
          </a-menu-item>
          <a-menu-item key="1-2" @click="$router.push('/blog/create')">
            <EditOutlined />
            <span>写文章</span>
          </a-menu-item>
          <a-menu-item key="1-3" @click="$router.push('/blog/category')">
            <FormOutlined />
            <span>类别管理</span>
          </a-menu-item>
          <a-menu-item key="1-4" @click="$router.push('/blog/moment/list')">
            <CalendarOutlined />
            <span>动态管理</span>
          </a-menu-item>
          <a-menu-item key="1-5" @click="$router.push('/blog/moment/create')">
            <EditOutlined />
            <span>写动态</span>
          </a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="2">
          <template #title>
            <span>
              <SwitcherOutlined />
              <span>评论管理</span>
            </span>
          </template>
          <a-menu-item key="2-1" @click="$router.push('/comment/list')">
            <ReadOutlined />
            <span>评论列表</span>
          </a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="3">
          <template #title>
            <span>
              <SwitcherOutlined />
              <span>日志</span>
            </span>
          </template>
          <a-menu-item key="3-1" @click="$router.push('/log/exception')">
            <ReadOutlined />
            <span>异常日志</span>
          </a-menu-item>
          <a-menu-item key="3-2" @click="$router.push('/log/operation')">
            <EditOutlined />
            <span>操作日志</span>
          </a-menu-item>
          <a-menu-item key="3-3" @click="$router.push('/log/logfile')">
            <FormOutlined />
            <span>日志文件</span>
          </a-menu-item>
        </a-sub-menu>
        <a-menu-item key="4" @click="$router.push('/app')">
          <span>应用管理</span>
        </a-menu-item>
        <a-menu-item key="5" @click="$router.push('/setting')">
          <span>页面设置</span>
        </a-menu-item>
        <a-sub-menu key="6">
          <template #title>
            <span>
              <span>访客管理</span>
            </span>
          </template>
          <a-menu-item key="6-1" @click="$router.push('/visitor/list')">
            <ReadOutlined />
            <span>访客列表</span>
          </a-menu-item>
        </a-sub-menu>
        <a-menu-item key="7" @click="$router.push('/friend')">
          <span>友链管理</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout :style="{ marginLeft: '200px' }">
      <a-layout-content :style="{ margin: '24px 16px 0', overflow: 'initial' }">
        <router-view />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>
<script setup>
import { ref } from 'vue';
import { EditOutlined, FormOutlined, ReadOutlined, SwitcherOutlined, CalendarOutlined } from '@ant-design/icons-vue';

const selectedKeys = ref(['1']);
</script>
<style scoped>
#components-layout-demo-fixed-sider .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}

.site-layout .site-layout-background {
  background: #fff;
}

[data-theme='dark'] .site-layout .site-layout-background {
  background: #141414;
}
</style>