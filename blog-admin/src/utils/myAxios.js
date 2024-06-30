import axios from 'axios'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {message} from "ant-design-vue";

const myAxios = axios.create({
    timeout: 10000
})

myAxios.interceptors.request.use(config => {
        NProgress.start()
        const token = localStorage.getItem('token')
        if (token) {
            config.headers.Authorization = token
        }
        return config
    },

    error => {
        console.log(error)
        return Promise.reject(error)
    })

myAxios.interceptors.response.use(response => {
        NProgress.done()
        const res = response.data
        if (res.code !== 200) {
            message.error(res.message || '未知错误')
        }
        return response
    },
    error => {
        console.info(error)
        message.error(error.message)
        if(error.response.status === 401){
            localStorage.removeItem('token')
            window.location.href = '/login'
        }
        return Promise.reject(error)
    })

export default myAxios