import myAxios from "@/utils/myAxios.js";

export function getOperation() {
    return myAxios({
        url: "/api/admin/operation",

        method: "GET"
    })
}

export function deleteOperation(id) {
    return myAxios({
        url: `/api/admin/operation/${id}`,
        method: "DELETE"
    })
}