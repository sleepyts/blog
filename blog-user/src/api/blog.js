import axios from "axios";
import config from "@/api/config.js";


/*
     获取博客列表

 */
export function getBlogList(page){
    return axios({
        method: "GET",
        url: config.url + '/Blog',
        params:{
            page: page,
        }
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
     删除博客
 */
export function deleteBlog(id){
    return axios({
        method: "DELETE",
        url: `${config.url}/Blog/${id}`,
        params: {
            id: id
        }
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

export function updateBlog(blog) {
    return axios({
        method: "PUT",
        url: `${config.url}/Blog`,
        headers: {
            "Content-Type": "application/json",
        },
        data: blog
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