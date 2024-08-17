import myAxios from "@/utils/myAxios.js";

export function getDashboardData() {
    return myAxios({
        url: "/api/admin/dashboard",
        method: "GET"
    });
}