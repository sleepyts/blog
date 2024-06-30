import {createRouter, createWebHistory} from 'vue-router';
const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/adminlogin.vue'),
        meta: { title: 'Admin Login' },
    },
    {
        path: '/',
        name: 'Admin',
        component: () => import('../views/admin2.0-version.vue'),
        meta: { title: 'Admin' },
        beforeEnter: (to, from, next) => {
            const token=localStorage.getItem('token')||''
            if(token){
                next()
            }else{
                next('/login')
            }
        },
        breadcrumbName: 'Home',
        children: [
            {
                path: 'blog/list',
                name: 'AdminBlogList',
                component: () => import('../views/admin/adminbloglist.vue'),
                meta: {
                    title: '文章列表'
                },
                breadcrumbName: '文章列表'
            },
            {
                path:'blog/create'
                ,name:'AdminBlogCreate'
                ,component:()=>import('../views/admin/adminblogcreate.vue')
                ,meta: {
                    title: '创建文章'
                }
                ,breadcrumbName: '创建文章'
            },
            {
                path: 'comment/list'
                ,name: 'AdminCommentList'
                ,component: () => import('../views/admin/admincommentlist.vue')
                ,meta: {
                    title: '评论列表'
                }
                ,breadcrumbName: '评论列表'
            },
            {
                path: 'blog/category'
                ,name: 'AdminBlogCategory'
                ,component: () => import('../views/admin/adminblogcategory.vue')
                ,meta: {
                    title: '类别管理'
                }
                ,breadcrumbName: '类别管理'
            },
            {
                path: 'log/exception'
                ,name: 'AdminExceptionList'
                ,component: () => import('../views/admin/adminexceptionlist.vue')
                ,meta: {
                    title: '异常日志'
                }
                ,breadcrumbName: '异常日志'
            },
            {
              path: 'log/operation'
              ,name:'AdminOperationList',

              component: () => import('../views/admin/adminoperationlist.vue'),
              meta: {
                  title: '操作日志'
              }
            },
            {
                path: 'app'
                ,name: 'AdminApp'
                ,component: () => import('../views/admin/adminapp.vue')
                ,meta: {
                    title: '应用管理'
                }
                ,breadcrumbName: '应用管理'
            },
            {
                path: 'setting/about'

                ,name: 'AdminSettingAbout'
                ,component: () => import('../views/admin/adminsettingabout.vue')
                ,meta: {
                    title: '关于页面设置'
                }
                ,breadcrumbName: '关于页面设置'
            },
            {
                path: 'dashboard'

                ,name: 'AdminDashboard'
                ,component: () => import('../views/admin/admindashboard.vue')
                ,meta: {
                    title: '仪表盘'
                }
                ,breadcrumbName: '仪表盘'
            }
        ],
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    //注册博客页面滚动钩子，监听页面滚动
    document.title =to.name + " | " + import.meta.env.VITE_APP;
    next();
});
export default router;