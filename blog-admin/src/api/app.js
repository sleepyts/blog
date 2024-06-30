import axios from "axios";
import myAxios from "@/utils/myAxios.js";

export function getApps(){
    return myAxios({
        method: "GET",
        url: "/api/app"
    })
}

export function createApp(data){
    return myAxios({
        method: "POST",
        url: "/api/admin/app",
        headers: {
            "Content-Type": "application/json"
        },
        data: data
    })
}

export function deleteApp(id){
    return myAxios({
        method: "DELETE",
        url: `/api/admin/app/${id}`
    })
}

export function updateApp(data){
    return myAxios({
        method: "PUT",
        url: `/api/admin/app`,
        headers: {
            "Content-Type": "application/json"
        },
        data: data
    })
}