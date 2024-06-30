import axios from "axios";


export default function getRecords(){
    return axios({
        method: 'GET',
        url: '/api/record'
    })
}