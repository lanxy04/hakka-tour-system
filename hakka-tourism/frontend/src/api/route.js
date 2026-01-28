import request from '@/utils/request'

export function createCustomRoute(data) {
  return request({
    url: '/route/custom',
    method: 'post',
    data
  })
}

export function getRouteList() {
  return request({
    url: '/route/list',
    method: 'get'
  })
}

export function getRouteDetail(id) {
  return request({
    url: `/route/${id}`,
    method: 'get'
  })
}

export function getUserRoutes(userId) {
  return request({
    url: `/route/user/${userId}`,
    method: 'get'
  })
}

export function updateRoute(data) {
  return request({
    url: '/route/update',
    method: 'put',
    data
  })
}

export function deleteRoute(id) {
  return request({
    url: `/route/${id}`,
    method: 'delete'
  })
}
