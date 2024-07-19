export default {
    SET_COMMENTS(state, res) {
        state.comments = res
    },

    SET_THEME(state, res){
        state.theme = res
    },
    SET_REPLY_TO(state, res) {
        state.replyTo = res
    },

    SET_SORT_WAY(state, res) {
        state.sortWay = res
    },

    async SET_BLOG_LIST(state, res) {
        state.blogList = res
        await sessionStorage.setItem('blogList', JSON.stringify(res))
    },
    SET_BLOG(state, res) {
        state.blogId = res
        sessionStorage.setItem('blogId', res)
    },
    SET_COMMENT_CONTENT(state, res) {
        state.commentForm.content = res
    },

    SET_VERIFY(state, res) {
        state.verify = res
    },
    SET_PAGE_ENTITY(state, res) {
        state.pageEntity = res
    },
    SET_BLOG_SCROLL_POSITION(state, res) {
        state.blogScrollPosition = res
        sessionStorage.setItem('blogScrollPosition', res)
    },

    SET_SETTINGS(state, res) {
        state.siteSettings = res
        localStorage.setItem('siteSettings', JSON.stringify(res))
    },

    SET_RANDOM_BLOG(state, res) {
        state.randomBlog = res
    },
    SET_RECENT_MOMENT(state, res) {
        state.recentMoment = res
    }
}