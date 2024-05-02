//对外暴露插件对象，对象中包含需要构架到全局的组件
import SvgIcon from '@/components/SvgIcon/index.vue'

// 从 @element-plus/icons-vue 中导入所有图标并进行全局注册
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const allGloblComponent: any = { SvgIcon }
//Object.keys方法返回对象中所有的键组成的数组
// console.log(Object.keys(allGloblComponent));

export default {
  //务必叫做install方法，应用使用插件时会把app应用作为参数传递到install方法作为参数
  // @ts-ignore
  install(app) {
    // console.log(app);
    Object.keys(allGloblComponent).forEach((key) => {
      app.component(key, allGloblComponent[key])
    })

    //将elementplus提供的全局组件注册为全局组件
    for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
      app.component(key, component)
    }
  },
}
