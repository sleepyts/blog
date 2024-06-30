import myAxios from "@/utils/myAxios.js";

export function getComment(id){
    return myAxios(({
        url: '/api/comment/'+id,
        method: 'get',
    }))
}

export function deleteComment(id){
    return myAxios(({
        url: '/api/admin/comment/'+id,
        method: 'delete',
    }))
}