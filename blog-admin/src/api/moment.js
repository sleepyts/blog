import myAxios from "@/utils/myAxios.js";

export function getMoments(){
    return myAxios({
        method: 'GET',
        url: '/api/admin/moment',
    })
}

export function deleteMoment(id){
    return myAxios({
        method: 'DELETE',
        url: `/api/admin/moment`,
        params: {
            id: id
        }
    })
}

export function addMoment(data){
    return myAxios({
        method: 'POST',
        url: '/api/admin/moment',
        data: data
    })
}

export function updateMoment(data){
    return myAxios({
        method: 'PUT',
        url: '/api/admin/moment',
        data: data
    })
}

export function changeVisible(id){
    return myAxios({
        method: 'PUT',
        url: `/api/admin/moment/visible/${id}`,
    })
}