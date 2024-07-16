import myAxios from "@/utils/myAxios.js";

export function getException(start,end){
    return myAxios({
        url: "/api/admin/exception",
        method: "GET",
        params: {
            start: start,
            end: end
        }
    })
}

export function deleteException(id){
    return myAxios({
        url: `/api/admin/exception/${id}`,
        method: "DELETE"
    })
}