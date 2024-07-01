import myAxios from "@/utils/myAxios.js";

export function getException(){
    return myAxios({
        url: "/api/admin/exception",
        method: "GET"
    })
}

export function deleteException(id){
    return myAxios({
        url: `/api/admin/exception/${id}`,
        method: "DELETE"
    })
}