//创建用户相关的小仓库
import { defineStore } from 'pinia'
//引入接口
import { reqLogin, reqUserInfo } from '@/api/user'
//引用数据类型
import { loginForm, loginResponseDate } from '@/api/user/type'
import { UserState } from './types/type'
//引入路由操作本地存储的工具方法
import { constantRoute } from '@/router/routers'
// 创建用户小仓库
let useUserStore = defineStore('User', {
  //数据存储的地方state
  state: (): UserState => {
    return {
      token: localStorage.getItem('TOKEN'), //用户唯一标识
      menuRoutes: constantRoute, //存储生成菜单需要数组
      username: '',
      avator: '',
      roleId:''
    }
  },
  //异步、逻辑的地方
  actions: {
    //用户登录方法
    async userLogin(data: loginForm) {
      //登录请求
      let result: loginResponseDate = await reqLogin(data)
      // 登录成功获取token
      if (result.code === 200) {
        //这里的token并没有持久化只是存储在对象中
        this.token = result.data.token as string
        //本地持久化
        localStorage.setItem('TOKEN', result.data.token as string)
        const userInfo = {
          username: String,
          avator: String,
          roleId:Number
        }
        userInfo.username = result.data.username
        userInfo.avator ='https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif' as any
        userInfo.roleId = result.data.roleId as any
        localStorage.setItem('userInfo', JSON.stringify(userInfo))
        //将用户名和用户头像存储到仓库
        this.username = result.data.username
        this.avator ='https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif'
        this.roleId = result.data.roleId
        //能保证当前async函数返回一个成功的promise
        return 'ok'
      } else {
        //登录失败返回登录失败错误信息
        //返回一个错误的promise并携带错误信息
        return Promise.reject(new Error(result.msg))
      }
    },
    //用户退出登录的方法
    userLogout() {
      //目前没有mock接口：退出登录的（通知本地服务器用户唯一标识失效）
      this.token = ''
      this.username = ''
      this.avator = ''
      this.roleId = ''
      localStorage.removeItem('TOKEN')
      localStorage.removeItem('userInfo')
    },
  },
  getters: {},
})

//对外暴露仓库
export default useUserStore
