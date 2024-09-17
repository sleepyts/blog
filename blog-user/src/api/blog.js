import axios from "axios";
import config from "@/api/config.js";


/*
     获取博客列表

 */
export function getBlogList(page){
   return axios({
        method: "GET",
        url: `/api/Blog/page/${page}`,
    })
}

/*
     新增博客
 */
export function postBlog(blog){
    return axios({
        method: "POST",
        url: `${config.url}/Blog`,
        headers: {
            "Content-Type": "application/json",
        },
        data: blog
    })
}
export function getBlogContent(id) {
    return axios({
        method: "GET",
        url: `${config.url}/Blog/${id}`,
        headers: {
            "Content-Type": "application/json",
        },
    })
}
/*
     分页获取博客列表
 */
export function getBlogByPage(page, size) {
    return axios({
        method: "GET",
        url: `${config.url}/Blog/page`,
        headers: {
            "Content-Type": "application/json",
        },
        params: {
            page: page,
            size: size,
        },
    });
}

export function getBlogThumbnail() {
    return axios({
        method: "GET",
        url: `${config.url}/Blog/thumbnail`,
        headers: {
            "Content-Type": "application/json",
        },
    })
}

export function getBlogByCategory(page,id) {
    return axios({
        method: "GET",
        url: `${config.url}/Blog/category/${id}`,
        headers: {
            "Content-Type": "application/json",
        },
        params: {
            page: page,
        },
    })
}

export function getBlogDetail(id) {
    return axios({
        method: "GET",
        url: `${config.url}/Blog/${id}`,
        headers: {
            "Content-Type": "application/json",
        },
    })
}
export function getRandomBlog() {
    return axios({
        method: "GET",
        url: `${config.url}/Blog/random`,
        headers: {
            "Content-Type": "application/json",
        }
    })
}

export function searchBlog(keyword) {
    return axios({
        method: "GET",
        url: `${config.url}/Blog/search`,
        headers: {
            "Content-Type": "application/json",
        },
        params: {
            keyword: keyword,
        },
    })
}