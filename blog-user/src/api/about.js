import axios from 'axios'
export function getAboutData(){
    return axios({
        method: 'get',
        url: '/api/about'
    })
}

export function updateAboutData(data){
    return axios({
        method: 'put',
        url: '/api/about',
        params:{
            text:data
        },
    })
}