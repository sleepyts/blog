import axios from "axios";
import config from "@/api/config.js";

export function uploadImg(formData){
    return axios({
        method: "post",
        url: config.url+"/upload/img",
        // url: "http://localhost:8080/upload/img",
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        data: formData
    })
}
