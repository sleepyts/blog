import myAxios from "@/utils/myAxios.js";

export function getCategories() {
    return myAxios({
        method: "GET",
        url: "/api/category"
    })
}

export function deleteCategory(id) {
    return myAxios({
        method: "DELETE",
        url: `/api/admin/category/${id}`
    })
}

export function addCategory(category) {
    return myAxios({
        method: "POST",
        url: "/api/admin/category",
        headers: {
            "Content-Type": "application/json"
        },
        data: category
    })
}

export function updateCategory( category) {
    return myAxios({
        method: "PUT",
        url: `/api/admin/category`,
        headers: {
            "Content-Type": "application/json"
        },
        data: category
    })
}
