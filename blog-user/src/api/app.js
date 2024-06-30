import axios from "axios";

export function getApps(){
    return axios({
        method: "GET",
        url: "/api/app"
    })
}

export function createApp(data){
    return axios({
        method: "POST",
        url: "/api/app",
        headers: {
            "Content-Type": "application/json"
        },
        data: data
    })
}

export function deleteApp(id){
    return axios({
        method: "DELETE",
        url: `/api/app/${id}`
    })
}

export function updateApp(data){
    return axios({
        method: "PUT",
        url: `/api/app`,
        headers: {
            "Content-Type": "application/json"
        },
        data: data
    })
}