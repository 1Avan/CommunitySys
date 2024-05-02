import { defineConfig, loadEnv } from 'vite'
import { viteMockServe } from 'vite-plugin-mock'
import VueSetupExtend from 'vite-plugin-vue-setup-extend'
import vue from '@vitejs/plugin-vue'
import path from 'path'
//导入element相关插件
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
//导入svg矢量图标插件
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'
// https://vitejs.dev/config/
export default defineConfig(({ command, mode }) => {
  // 获取各种环境下的对应的变量
  let env = loadEnv(mode, process.cwd())
  return {
    plugins: [
      // element插件
      AutoImport({
        resolvers: [ElementPlusResolver()],
      }),
      Components({
        resolvers: [ElementPlusResolver()],
      }),
      vue(),
      //配置图标插件
      createSvgIconsPlugin({
        // Specify the icon folder to be cached
        iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
        // Specify symbolId format
        symbolId: 'icon-[dir]-[name]',
      }),
      //setup语法糖配置name
      VueSetupExtend(),
    ],
    resolve: {
      alias: {
        '@': path.resolve('./src'), // 相对路径别名配置，使用 @ 代替 src
      },
    },
    //less全局变量一个配置
    css: {
      preprocessorOptions: {
        less: {
          javascriptEnabled: true,
          additionalData: '@import "./src/styles/common.less";',
        },
      },
    },
    server: {
      port: 8888,
      proxy: {
        [env.VITE_APP_BASE_API]: {
          // ws:true,实时通信
          //找获取数据的服务器地址
          target: env.VITE_SERVE,
          //需要代理跨域
          changeOrigin: true,
          ///api 开头的路径字符串中的 /api 替换为空字符串。换句话说，它去掉了路径字符串开头的 /api 部分
          // 举个例子，如果传入的 path 是 '/api/data'，那么经过这个箭头函数处理后，返回的结果将是 'data'。
          rewrite: (path) => path.replace(/^\/api/, ''),
        },
      },
    },
  }
})
