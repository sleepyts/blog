import axios from "axios";

export function getFriendList(){
    return axios({
        method: 'get',
        url: '/api/links'
    })
}
