import {createRouter, createWebHistory} from 'vue-router';
import getPageTitle from "@/util/getPageTitle.js";
import store from "@/store/index.js";

const routes = [
    {
        path: '/:catchAll(.*)',
        name: 'NotFound',
        redirect: { name: '404' },
    },
    {
        path:'/notfound',
        name: '404',
        component: () => import('../views/notfound.vue'),
        meta: { title: '404 Not Found' }
    },
    {
        path: '/',
        name: 'Home',
        component: () => import('../views/home.vue'),
        meta: { title: 'Home' }
    },
    {
        path: '/Blog',
        name: 'Blog',
        component: () => import('../views/blog.vue'),
        meta: { title: 'Blog' }
    },
    {
        path: '/records',
        name: 'Records',
        component: () => import('../views/records.vue'),
        meta: { title: 'Records' }
    },
    {
        path: '/Moments',
        name: 'Moments',
        component: () => import('../views/moments.vue'),
        meta: { title: 'Moments' }
    },
    {
        path: '/About',
        name: 'About',
        component: () => import('../views/about.vue'),
        meta: { title: 'About' }
    },
    {
        path: '/Guestbook',
        name: 'Guestbook',
        component: () => import('../views/guestbook.vue'),
        meta: { title: 'Guestbook' }
    },
    {
        path: '/Apps',
        name: 'Apps',
        component: () => import('../views/app.vue'),
        meta: { title: 'Apps' }
    },
    {
        path:'/Links',
        name: 'Links',
        component: () => import('../views/links.vue'),
        meta: { title: 'Links' }
    },
    // {
    //     path:'/Moments',
    //     name: 'Moments',
    //     component: () => import('../views/moments.vue'),
    //     meta: { title: 'Moments' }
    // },
    {
        path: "/Blog/:id",
        name: "BlogDetail",
        component: () => import("../views/blogdetail.vue"),
        meta: { title: 'Detail' }
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
    scrollBehavior(to, from, savedPosition) {
        if(to.name === 'Blog') return {top: store().state.blogScrollPosition}
        else return { top: 0 };
    }
});

router.beforeEach((to, from, next) => {
    //注册博客页面滚动钩子，监听页面滚动
    if(to.name==='Blog')   window.addEventListener('scroll', updateScrollPosition);
    else window.removeEventListener('scroll', updateScrollPosition);
    store().state.isLoading=true;
    document.title = getPageTitle(to.meta.title) + " | " + import.meta.env.VITE_APP;
    // console.log(to.path)
    next();
});

router.afterEach(() => {
    store().state.isLoading=false;
})
//更新博客滚动位置
const updateScrollPosition = () => {
    store().commit('SET_BLOG_SCROLL_POSITION', window.scrollY);
}

export default router;
