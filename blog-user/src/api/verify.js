import axios from "axios";
import config from "@/api/config.js";


export function getVerify(){
    return axios(
        {
            method: "GET",
            url: config.url + "/verify"
        }
    )
}