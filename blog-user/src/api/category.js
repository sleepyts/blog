import axios from "axios";

export function getCategories() {
    return axios({
        method: "GET",
        url: "/api/category"
    })
}

export function deleteCategory(id) {
    return axios({
        method: "DELETE",
        url: `/api/category/${id}`
    })
}

export function addCategory(category) {
    return axios({
        method: "POST",
        url: "/api/category",
        headers: {
            "Content-Type": "application/json"
        },
        data: category
    })
}

export function updateCategory( category) {
    return axios({
        method: "PUT",
        url: `/api/category`,
        headers: {
            "Content-Type": "application/json"
        },
        data: category
    })
}
