//通过vue-router插件实现路由设置
import { createRouter, createWebHashHistory } from 'vue-router'
import { constantRoute } from './routers.ts'
//创建路由器
const router = createRouter({
  //路由模式hash，history，这个项目使用hash模式
  history: createWebHashHistory(),
  routes: constantRoute,
  //滚动行为
  scrollBehavior() {
    return {
      left: 0,
      top: 0,
    }
  },
})

export default router
