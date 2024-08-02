import axios from "axios";

export function getMoment(currentPage, size) {
  return axios({
    method: "GET",
    url: `/api/moment`,
    params: {
      page: currentPage,
      size: size,
    },
  });
}

export function getRecentMoment() {
  return axios({
    method: "GET",
    url: `/api/moment/recent`,
  });
}

export function likeMoment(momentId) {
  return axios({
    method: "POST",
    url: `/api/moment/like/${momentId}`,
  });
}
