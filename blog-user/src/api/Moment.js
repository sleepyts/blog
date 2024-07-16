import axios from "axios";

export function getMoment(currentPage,size){
    return axios({
        method: "GET",
        url: `/api/moment`,
        params:{
            page:currentPage,
            size:size
        }
    })
}