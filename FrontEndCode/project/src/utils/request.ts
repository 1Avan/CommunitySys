import axios from 'axios'
import { ElMessage } from 'element-plus'
//引入用户相关仓库
import useUserStore from '@/store/modules/user.ts'
//创建axios实例
let request = axios.create({
  //配置axios请求基础路径
  baseURL: import.meta.env.VITE_APP_BASE_API,
  //最大响应时间
  timeout: 5000,
})
//请求拦截器
request.interceptors.request.use((config) => {
  //获取用户相关的小仓库，从而获取里面的token，登录成功后请求携带token向服务器发送请求
  let userStore = useUserStore()
  // console.log(userStore.token);
  if (userStore.token) {
    config.headers.token = userStore.token
  }
  //config配置对象，headers属性请求头，经常给服务器端携带公共参数
  return config
})
//响应拦截器
request.interceptors.response.use(
  (response) => {
    // 成功回调
    //简化数据
    return response.data
  },
  (error) => {
    //处理网络错误
    let msg = ''
    let status = error.response.status
    switch (status) {
      case 401:
        msg = 'token过期'
        break
      case 403:
        msg = '无权访问'
        break
      case 404:
        msg = '请求地址错误'
        break
      case 500:
        msg = '服务器出现问题'
        break
      default:
        msg = '无网络'
    }
    ElMessage({
      type: 'error',
      message: msg,
    })
    return Promise.reject(error)
  },
)
//暴露的request是经过处理的axios
export default request
