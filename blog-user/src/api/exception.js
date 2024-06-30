import axios from "axios";

export function getException(){
    return axios({
        url: "/api/exception",
        method: "GET"
    })
}

export function deleteException(id){
    return axios({
        url: `/api/exception/${id}`,
        method: "DELETE"
    })
}