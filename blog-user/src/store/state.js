
export default {
    currentMdId: '',
    randomBlog : [
    ],
    siteSettings:JSON.parse(sessionStorage.getItem('sitSettings')) || {
    },
    siteInfo: '',
    // 是否正在加载
    isLoading: false,
    // 主题
    theme: 'light',
    // 当前博客id -1表示留言板
    blogId:sessionStorage.getItem('blogId') || -1,
    // 博客滚动位置
    blogScrollPosition: sessionStorage.getItem('blogScrollPosition') || 0,
    // 评论列表
    comments: [],
    //分页信息
    pageEntity:{},
    // 评论回复的id
    replyTo: -1,
    //评论排序方式 0:时间顺序 1:时间倒序
    // sortWay: 0,
    commentForm: {
        id:'',
        content: '',
        name: '',
        email: '',
        replyId:'',
        verify:'',
        verifyValue: '',
        url:''
    },
    blogForm: {
        title: '',
        content: '',
        img :'',
        categoryId: '',
        description: '',
    },
    blogList: JSON.parse(sessionStorage.getItem('blogList')) || [],
    blog:JSON.parse(sessionStorage.getItem('blog')) || {},
    verify:{
        var1: '',
        var2: '',
        key: '',
        verifyVar: ''
    }
}
// private Long id;
// private String name;
// private String content;
// private String imgUrl;
// private Long blogId;
// private Long replyId;
// private LocalDateTime createTime;