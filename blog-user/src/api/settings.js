import axios from "axios";

export function getSettings() {
    return axios({
        url: '/api/settings',
        method: 'get'
    })
}

export function getVisitorCount() {
    return axios({
        url: '/api/visitor/count',
        method: 'get'
    })
}