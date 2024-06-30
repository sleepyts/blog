import myAxios from "@/utils/myAxios.js";

export function getUV(){
    return myAxios(({
        method: 'GET',
        url: '/api/admin/dashboard/uv'
    }))
}

export function getPV(){
    return myAxios(({
        method: 'GET',
        url: '/api/admin/dashboard/pv'
    }))
}

export function getTotalArticle(){
    return myAxios(({
        method: 'GET',
        url: '/api/admin/dashboard/totalArticle'
    }))
}

export function getTotalComment(){
    return myAxios(({
        method: 'GET',
        url: '/api/admin/dashboard/totalComment'
    }))
}