//快讯相关接口
import request from '@/utils/request.ts'
enum API {
  BASE_URL = '/newsletter',
  PAGEQUERY_URL = '/newsletter/page',
  RANDOM_URL = '/newsletter/random'
}

//对外暴露接口方法
export const reqGetNewById = (id: any) =>request.get(API.BASE_URL + '/' + id)
export const reqPageNew = (data: any) =>request.get(API.PAGEQUERY_URL + '?' + new URLSearchParams(data))
export const reqDeleteNew = (id: any) =>request.delete(API.BASE_URL + '/' + id)
export const reqAddNew = (data: any) => request.post(API.BASE_URL, data)
export const reqUpdateNew = (data: any) => request.put(API.BASE_URL, data)
export const reqRandomNews = () => request.get(API.RANDOM_URL)