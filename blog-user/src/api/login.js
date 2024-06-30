import axios from "axios";


export function adminLogin(form){
    return axios({
        method: "POST",
            url: `/api/admin/login`,
            data: form,
            headers: {
                "Content-Type": "application/json"
            }
    })
}