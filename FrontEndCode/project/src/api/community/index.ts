//统一管理项目社团管理相关接口
import request from '@/utils/request.ts'
//统一管理社团接口
enum API {
  SEARCHBYNAME_URL = '/community/search',
  BASE_URL = '/community',
  PAGEQUERY_URL = '/community/page',
}

//对外暴露接口方法
export const reqSearchCommunity = (name: any) =>
  request.get(API.SEARCHBYNAME_URL + '/' + name)
export const reqAllCommunity = (data: any) =>
  request.get(API.PAGEQUERY_URL + '?' + new URLSearchParams(data))
export const reqDeleteCommunity = (id: any) =>
  request.delete(API.BASE_URL + '/' + id)
export const reqAddCommunity = (data: any) => request.post(API.BASE_URL, data)
export const reqUpdateCommunity = (data: any) => request.put(API.BASE_URL, data)
