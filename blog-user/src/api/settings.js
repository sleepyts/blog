import axios from "axios";

export function getSettings() {
    return axios({
        url: '/api/settings',
        method: 'get'
    })
}