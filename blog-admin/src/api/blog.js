import myAxios from "@/utils/myAxios.js";

export function getBlog() {
    return myAxios({
        url: '/api/Blog',
        method: 'get'
    })
}

export function deleteBlog(id) {
    return myAxios({
        url: '/api/admin/Blog/'+id ,
        method: 'delete'
    })
}

export function addBlog(data) {
    return myAxios({
        url: '/api/admin/Blog',
        method: 'post',
        data: data,
    })
}

export function updateBlog(data) {
    return myAxios({
        url: '/api/admin/Blog',
        method: 'put',
        data: data,
    })
}

export function getBlogThumbnail(){
    return myAxios({
        url: '/api/admin/Blog/thumbnail',
        method: 'get'
    })
}

export function getBlogContent(id) {
    return myAxios({
        method: "GET",
        url: `/api/Blog/${id}`,
        headers: {
            "Content-Type": "application/json",
        },
    })
}
