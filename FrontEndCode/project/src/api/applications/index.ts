import request from '@/utils/request'

enum API {
  BASE_URL = '/application',
  USERAPPLICATIONS_URL = '/application/useroneself',
  UPDATEAPPLICATIONS_URL = '/application/update',
  JOINACITIVITY_URL = '/application/activity',
  DELETE_URL = '/application/activity/delete'
}

//用户申请加入社团接口
export const reqUserToCommunity = (data: any) =>request.post(API.BASE_URL, data)
//根据用户id获取用户相关审核信息
export const reqUserApplications = (userId: any) =>
  request.get(API.USERAPPLICATIONS_URL + '/' + userId)
//根据用户id、审核信息id、审核状态完成审核
export const reqUpdateApplications = (data: any) =>
  request.put(API.UPDATEAPPLICATIONS_URL, data)
//根据社团id获取本社团申请信息
export const reqApplicationsByCid = (id: any) =>
  request.get(API.BASE_URL + '/' + id)

//用户参加社团活动
export const reqJoinActivity = (data: any) =>
  request.post(API.JOINACITIVITY_URL, data)

//导出活动成员excel
export const reqExportUserToActivity = (activityId: any) =>
  request.get(API.JOINACITIVITY_URL + '/' + activityId)

//根据成员id获取参加活动列表
export const reqAllActivityByUserId = (userId:any)=>request.get(API.JOINACITIVITY_URL,{params:{userId:userId}})

//根据成员id和活动id退出活动
export const reqTuiChu = (data:any)=>request.post(API.DELETE_URL,data)