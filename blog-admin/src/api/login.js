import axios from "axios";

export function login(data) {
    return axios({
        url: "/api/admin/login",
        method: "post",
        data: data,
        headers: {
            "Content-Type": "application/json"
        }
    })
}