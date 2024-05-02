import { createApp } from 'vue'
//引入elementplus ui的组件及其样式
import ElementPlus from 'element-plus'
//element国际化
// @ts-ignore
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import 'element-plus/dist/index.css'
//导入矢量图标插件
import 'virtual:svg-icons-register'
import App from '@/App.vue'
//引入自定义插件用于注册所有全局组件
import CpAllGloabl from '@/components/index.ts'
//引入模板的全局样式
import '@/styles/index.less'
// 引入配置好的路由
import router from '@/router'
//引入pinia仓库
import pinia from '@/store/index.ts'
//获取应用的实例对象
const app = createApp(App)

app.use(ElementPlus, {
  locale: zhCn,
})
//使用CpAllGloabl插件，注册全局组件
app.use(CpAllGloabl)
//注册模板路由
app.use(router)
//引入路由鉴权文件
import './permission'
//安装仓库
app.use(pinia)
app.mount('#app')

//测试代码
// import axios from 'axios';
// // 登录接口
// axios({
//   url:"/api/user/login",
//   method:"post",
//   data:{
//     username:"admin",
//     password:"1112111"
//   }
// })

// console.log(import.meta.env);
