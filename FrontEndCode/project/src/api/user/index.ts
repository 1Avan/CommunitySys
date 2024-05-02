//统一管理项目用户管理相关接口
import request from '@/utils/request.ts'
import {
  loginForm,
  loginResponseDate,
  userResponseData,
  registerForm,
} from './type.ts'
//统一管理接口
enum API {
  LOGIN_URL = '/user/login',
  USERINFO_URL = '/user/userInfo',
  REGISTER_URL = '/user/register',
  BASE_URL = '/user',
  GETALLUSER_URL = '/user/all',
  ADDUSER_URL = '/user/add',
  EDITUSER_URL = '/user/edit',
}

//对外暴露请求函数
//用户登录的方法
export const reqLogin = (data: loginForm) =>
  request.post<any, loginResponseDate>(API.LOGIN_URL, data)
//用户注册的方法
export const reqRegister = (data: registerForm) =>
  request.post(API.REGISTER_URL, data)
//根据用户名获取用户个人信息
export const reqUserInfo = function (username: String) {
  //泛型第一个参数是服务器返回数据的类型，第二个参数是经过 axios 包装一层得到的 response 对象的类型
  return request.get<any, userResponseData>(API.USERINFO_URL, {
    params: {
      username: username,
    },
  })
}
//修改个人信息
export const reqChangeUser = (data: any) => request.put(API.BASE_URL, data)
//获取所有用户信息
export const reqAllUser = (queryParams: any) =>
  request.get(API.GETALLUSER_URL + '?' + new URLSearchParams(queryParams))
//根据用户id伪删除用户
export const reqDeleteUser = (id: any) =>
  request.delete(API.BASE_URL + '/' + id)
//根据用户id获取用户信息
export const reqUserById = (id: any) => request.get(API.BASE_URL + '/' + id)
//插入用户
export const reqAddUser = (user: any) => request.post(API.ADDUSER_URL, user)
//编辑并修改用户信息
export const reqEditUser = (data: any) => request.put(API.EDITUSER_URL, data)
