import myAxios from "@/utils/myAxios.js";

export function getOperation(start, end) {
  return myAxios({
    url: "/api/admin/operation",
    method: "GET",
    params: {
      start: start,
      end: end,
    },
  });
}

export function deleteOperation(id) {
  return myAxios({
    url: `/api/admin/operation/${id}`,
    method: "DELETE",
  });
}

export function getLogFile() {
  return myAxios({
    url: "/api/admin/operation/log",
    method: "GET",
  });
}

export function getVisitorLog(page,pageSize){
  return myAxios({
    url: "/api/admin/operation/tasklog",
    method: "GET",
    params:{
      page: page,
      pageSize: pageSize
    }
  });
}