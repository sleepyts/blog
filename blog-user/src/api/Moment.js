import axios from "axios";

export function getMoment(page, size) {
  return axios({
    method: "GET",
    url: `/api/moment`,
    headers: {
      "Content-Type": "application/json",
    },
    params: {
      page: page,
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
