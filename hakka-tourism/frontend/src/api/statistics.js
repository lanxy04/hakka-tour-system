import request from '@/utils/request'

export function getStatisticsByType(type) {
  return request({
    url: `/statistics/${type}`,
    method: 'get'
  })
}

export function getAllStatistics() {
  return request({
    url: '/statistics/all',
    method: 'get'
  })
}
