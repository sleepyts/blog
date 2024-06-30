import state from "@/store/state.js";
import {getComments, getCommentsByPage, postComment} from "@/api/comment.js";
import {getBlogByPage, getBlogList, postBlog} from "@/api/blog.js";
import {getVerify} from "@/api/verify.js";
import {message} from "ant-design-vue";
import '../assets/message.css'
import store from "@/store/index.js";
import {onRequestPost} from "@/api/upload-2.0.js";

export default {
    changeTheme() {
        store().commit('SET_THEME', state.theme === 'light'? 'dark' : 'light')
        localStorage.setItem('theme', state.theme);
        document.documentElement.setAttribute('data-theme', state.theme)
    },

    getCommentList({commit, rootState}) {
        getComments(state.blogId).then(res => {
            if (res.data.code === 200) {
                res.data.data.sort((a, b) => {
                    return new Date(b.createTime) - new Date(a.createTime)
                })
                commit('SET_COMMENTS', res.data.data)
                //console.log(res.data.data)
            }
        })
    },

    /*
    * 新增评论
     */
    postComment({commit, rootState}) {

        /*
        * 准备参数
         */
        let form = {...rootState.commentForm}
        form.blogId = rootState.blogId
        form.replyId = rootState.replyTo
        form.verify = rootState.verify
        form.url = rootState.commentForm.url
        if (form.name === '') form.name = '匿名'
        // console.log(form)
        postComment(form).then(res => {
            if (res.data.code === 200) {
                getCommentsByPage(state.blogId, 1, 8).then(res => {
                    if (res.data.code === 200) {

                        getVerify().then(res => {
                            if (res.data.code === 200) {

                                commit('SET_VERIFY', res.data.data)
                                //console.log(res.data.data)
                            } else {
                                console.log("获取验证码失败：" + res.data.message)
                            }
                        })
                        commit('SET_PAGE_ENTITY', res.data.data)
                        //console.log(res.data.data)
                    } else {
                        console.log("获取评论失败：" + res.data.message)
                    }
                })
                state.commentForm.content = ''
                state.commentForm.imgUrl = ''
                message.success({
                    content: () => '评论发表成功',
                });
            } else {
                message.error({
                    content: () => res.data.message,
                });
                //console.log("发表评论失败："+res.data.message)
            }
        })

    },
    postBlog({commit, rootState}) {
        let form = {...rootState.blogForm}
        postBlog(form).then(res => {
            if (res.data.code === 200) {
                getBlogList().then(res => {
                    if (res.data.code === 200) {
                        commit('SET_BLOG_LIST', res.data.data)
                        //console.log(res.data.data)
                    } else {
                        console.log("获取博客列表失败：" + res.data.message)
                    }
                })
            } else {
                console.log("发表博客失败：" + res.data.message)
            }
        })
    },
    async getBlogList({commit, rootState}) {
        await getBlogList().then(res => {
            if (res.data.code === 200) {
                commit('SET_BLOG_LIST', res.data.data)
                //console.log(res.data.data)
            } else {
                console.log("获取博客列表失败：" + res.data.message)
            }
        })
    },
    getVerify({commit, rootState}) {
        getVerify().then(res => {
            if (res.data.code === 200) {
                commit('SET_VERIFY', res.data.data)
                //console.log(res.data.data)
            } else {
                console.log("获取验证码失败：" + res.data.message)
            }
        })
    },
    getCommentsByPage({commit, rootState}, page) {
        getCommentsByPage(state.blogId, page.page, page.pageSize).then(res => {
            if (res.data.code === 200) {
                commit('SET_PAGE_ENTITY', res.data.data)
                //console.log(res.data.data)
            }
        })
    },
    async getBlogByPage({commit, rootState}, page) {
        getBlogByPage(page.page, page.pageSize).then(res => {
            if (res.data.code === 200) {
                commit('SET_PAGE_ENTITY', res.data.data)
                //console.log(res.data.data)
            }
        })
    }
}