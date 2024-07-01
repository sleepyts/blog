import MyAxios from "@/utils/myAxios.js";

export function getVisitor() {
    return MyAxios({
        url: '/api/admin/visitor',
        method: 'GET'
    })
}

export function deleteVisitor(id) {
    return MyAxios({
        url: `/api/admin/visitor/${id}`,
        method: 'DELETE'
    })
}