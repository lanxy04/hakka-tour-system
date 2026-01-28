import request from '@/utils/request'

export function getResourceList() {
  return request({
    url: '/resource/list',
    method: 'get'
  })
}

export function getResourceDetail(id) {
  return request({
    url: `/resource/${id}`,
    method: 'get'
  })
}

export function searchResource(params) {
  return request({
    url: '/resource/search',
    method: 'post',
    data: params
  })
}

export function createResource(data) {
  return request({
    url: '/resource/create',
    method: 'post',
    data
  })
}

export function updateResource(data) {
  return request({
    url: '/resource/update',
    method: 'put',
    data
  })
}

export function deleteResource(id) {
  return request({
    url: `/resource/${id}`,
    method: 'delete'
  })
}

export function rateResource(id, score) {
  return request({
    url: `/resource/${id}/rate`,
    method: 'post',
    data: { score }
  })
}
