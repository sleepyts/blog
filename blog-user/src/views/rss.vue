<template>
    <div>

        <pre>{{ rss }}</pre>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const title = ref('');
const items = ref([]);
const rss = ref('');

onMounted(async () => {
    try {
        const response = await axios.get('/api/rss'); // 代理服务器URL
        rss.value = response.data;

        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(response.data, 'text/xml');

        title.value = xmlDoc.querySelector('title').textContent;

        const rssItems = xmlDoc.querySelectorAll('item');
        items.value = Array.from(rssItems).map(item => ({
            title: item.querySelector('title').textContent,
            link: item.querySelector('link').textContent,
            pubDate: item.querySelector('pubDate').textContent,
            description: item.querySelector('description').textContent
        }));
    } catch (error) {
        console.error('Error fetching RSS feed:', error);
    }
});
</script>

<style scoped>
ul {
    list-style-type: none;
    padding: 0;
}

li {
    margin-bottom: 20px;
}
</style>