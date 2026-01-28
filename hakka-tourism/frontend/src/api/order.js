import request from '@/utils/request'

export function createOrder(data) {
  return request({
    url: '/order/create',
    method: 'post',
    data
  })
}

export function getOrderList() {
  return request({
    url: '/order/list',
    method: 'get'
  })
}

export function getUserOrders(userId) {
  return request({
    url: `/order/user/${userId}`,
    method: 'get'
  })
}

export function updateOrder(data) {
  return request({
    url: '/order/update',
    method: 'put',
    data
  })
}

export function deleteOrder(id) {
  return request({
    url: `/order/${id}`,
    method: 'delete'
  })
}
