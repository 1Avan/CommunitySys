//统一管理项目活动管理相关接口
import request from '@/utils/request.ts'
//统一管理活动接口
enum API {
  GETALLACTIVITY_URL = 'activity/page',
  BASE_URL = 'activity',
  GETBYCID_URL = 'activity/tocommunity',
  RANDOM_URL = 'activity/random-activities'
}

//对外暴露接口方法
//分页查询活动列表
export const reqAllActivity = (queryParams: any) =>
  request.get(API.GETALLACTIVITY_URL + '?' + new URLSearchParams(queryParams))
//根据活动id删除活动
export const reqDeleteActivity = (id: any) =>
  request.delete(API.BASE_URL + '/' + id)
//根据活动id获取活动信息
export const reqActivityById = (id: any) => request.get(API.BASE_URL + '/' + id)
//修改活动
export const reqUpdateActivity = (data: any) => request.put(API.BASE_URL, data)
//添加活动
export const reqAddActivity = (data: any) => request.post(API.BASE_URL, data)
//根据社团id获取活动
export const reqActivityByCid = (communityId: any) =>
  request.get(API.GETBYCID_URL + '/' + communityId)
//随机获取4个活动
export const reqRandomActivity = ()=>request.get(API.RANDOM_URL)
