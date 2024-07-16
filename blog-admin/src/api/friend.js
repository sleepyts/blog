import myAxios from "@/utils/myAxios.js";

export function getFriend(){
    return myAxios({
        method: 'get',
        url: '/api/links',
    })
}
export function addFriend(friend){
    return myAxios({
        method: 'post',
        url: '/api/links',
        data: friend
    })
}

export function delFriend(id){
    return myAxios({
        method: 'delete',
        url: `api/links/${id}`
    })
}

export function updateFriend(friend){
    return myAxios({
        method: 'put',
        url: '/api/links',
        data: friend
    })
}