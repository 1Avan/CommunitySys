<template>
  <div class="layout_container">
    <!-- 左侧菜单 -->
    <div class="left_menu" :class="{ fold: SettingStore.fold ? true : false }">
      <Logo></Logo>
      <!-- 展示菜单 -->
      <!-- 滚动组件 -->
      <el-scrollbar>
        <!-- 菜单组件 -->
        <el-menu
          text-color="black"
          router
          :collapse="SettingStore.fold"
          :default-active="$route.path"
        >
          <menu-vue :menuList="userStore.menuRoutes"></menu-vue>
        </el-menu>
      </el-scrollbar>
    </div>
    <!-- 顶部导航组件Tabbar -->
    <div class="layout_nav" :class="{ fold: SettingStore.fold ? true : false }">
      <Tabbar></Tabbar>
    </div>
    <!-- 内容展示区域 -->
    <div
      class="layout_main"
      :class="{ fold: SettingStore.fold ? true : false }"
    >
      <!-- 路由展示区域占位符 -->
      <!-- <router-view></router-view> -->
      <Main></Main>
    </div>
  </div>
</template>
<script setup lang="ts" name="Layout">
// 引入logo组件
import Logo from './logo/index.vue'
// 引入动态菜单
import menuVue from './menu/index.vue'
//引入顶部tabbar组件
import Tabbar from './tabbar/index.vue'
//获取用户相关的小仓库
import useUserStore from '@/store/modules/user'
//引入封装好的路由占位组件Main
import Main from './main/index.vue'
//引入路由对象
import { useRoute } from 'vue-router'
let userStore = useUserStore()
//接收路由
let $route = useRoute()
//获取小仓库SettingStore
import useLayoutSettingStore from '@/store/modules/setting'
let SettingStore = useLayoutSettingStore()
</script>
<style scoped lang="less">
.layout_container {
  width: 100%;
  height: 100vh;
}
.left_menu {
  width: @base-menu-width;
  height: 100vh;
  transition: all 0.3s;
  &.fold {
    width: @base-menu-min-width;
  }
  .el-scrollbar {
    width: 100%;
    height: calc(100vh - @base-logo-height);
    .el-menu {
      border: none;
    }
  }
}
.layout_nav {
  position: fixed;
  width: calc(100% - @base-menu-width);
  height: @base-nav-top;
  top: 0;
  left: @base-menu-width;
  transition: all 0.3s;
  &.fold {
    width: calc(100% - @base-menu-min-width);
    left: @base-menu-min-width;
  }
}
.layout_main {
  position: absolute;
  top: @base-nav-top;
  left: @base-menu-width;
  width: calc(100% - @base-menu-width);
  height: calc(100vh - @base-nav-top);
  background-color: #ccc;
  padding: 20px;
  //给内容区域添加滚动条
  overflow: auto;
  //过渡动画
  transition: all 0.3s;
  &.fold {
    width: calc(100% - @base-menu-min-width);
    left: @base-menu-min-width;
  }
}
</style>
