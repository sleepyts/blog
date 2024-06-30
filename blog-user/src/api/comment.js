import axios from "axios";
import config from "@/api/config.js";


/*
     * 根据文章id获取评论列表
 */
export function getComments(id) {
    return axios({
        method: "GET",
        url: `${config.url}/comment/${id}`,
        headers: {
            "Content-Type": "application/json",
        },
    });
}

/*
     * 发表评论
 */
export function postComment(comment) {
    return axios({
        method: "POST",
        url: `${config.url}/comment`,
        headers: {
            "Content-Type": "application/json",
        },
        data: comment,
    });
}

/*
     * 删除评论
 */
export function deleteComment(id) {
    return axios({
        method: "DELETE",
        url: `${config.url}/comment/${id}`,
        headers: {
            "Content-Type": "application/json",
        },
        params: {
            id: id,
        },
    });
}
/*
     * 根据文章id分页查询评论
 */
export function getCommentsByPage(id, page, size) {
    return axios({
        method: "GET",
        url: `${config.url}/comment/page/${id}`,
        headers: {
            "Content-Type": "application/json",
        },
        params: {
            page: page,
            size: size,
        },
    });
}