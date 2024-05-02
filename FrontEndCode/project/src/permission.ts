//路由鉴权
import router from '@/router'
//引入进度条插件，这里的插件是js的所以会报错
//@ts-ignore
import NProgress from 'nprogress'
//引入进度条样式
import 'nprogress/nprogress.css'
NProgress.configure({ showSpinner: false })
//获取用户相关的小仓库，去判断用户是否登录成功
import useUserStore from '@/store/modules/user'
import pinia from './store'
import setting from './setting'
let userStore = useUserStore(pinia)

//全局守卫：项目中任意路由切换都会触发的钩子
router.beforeEach(async (to: any, from: any, next: any) => {
  //to：代表要访问哪一个路由
  //from：你从哪个路由而来
  //next：路由的放行函数
  //访问某一个路由之前守卫
  NProgress.start()
  //获取token判断用户是否登录
  let token = userStore.token
  //获取用户名
  let username = userStore.username
  //用户登录判断
  if (token) {
    //登录成功不能去login
    if (to.path === '/login') {
      next({ path: '/' })
    } else {
      //如果有用户信息放行
      if (username) {
        next()
      } else {
        //如果没有用户信息就发请求获取用户信息后再放行
        try {
          next()
        } catch (error) {
          //token过期
          //用户退出登录
          userStore.userLogout()
          next({ path: '/login', query: { redirect: to.path } })
        }
      }
    }
  } else {
    if (to.path === '/login' || to.path === '/index') {
      next()
    } else {
      next({ path: '/login', query: { redirect: to.path } })
    }
  }
})
//全局后置守卫
router.afterEach((to: any, from: any) => {
  document.title = `${setting.title} - ${to.meta.title}`
  NProgress.done()
})

//第一问题：解决任意路由切换实现进度条业务 --nprogress
//第二个问题：路由鉴权（路由组件访问权限的设置）

//用户未登录只能访问login不能访问其他路由（指向login）
//登录成功后不能访问login其他可以访问
