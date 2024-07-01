import MyAxios from "@/utils/myAxios.js";

export function getSettings() {
    return MyAxios({
        url: '/api/settings',
        method: 'get'
    })
}

export function updateSettings(settings) {
    return MyAxios({
        url: '/api/admin/settings',
        method: 'put',
        data: settings
    })
}