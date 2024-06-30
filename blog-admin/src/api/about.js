import myAxios from "@/utils/myAxios.js";

export function updateAboutData(data) {
    return myAxios({
        url: '/api/admin/about',
        method: 'put',
        data: {
            text: data,
        },
        headers: {
            'Content-Type': 'application/json'
        },
    });
}


export function getAboutData(){
    return myAxios({
        url: '/api/about',
        method: 'get',
    })
}