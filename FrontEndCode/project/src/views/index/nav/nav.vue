<template>
  <div>
    <div class="nav-box container">
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        :router="true"
      >
        <el-menu-item index="/index/home">首页</el-menu-item>
        <el-sub-menu index="/index/community">
          <template #title>活力社团</template>
          <el-menu-item index="/index/community/huoli">活力社团</el-menu-item>
          <el-menu-item index="/index/community/youxiu">加入社团</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="3" disabled>青春社联</el-menu-item>
        <el-menu-item index="/index/newsletter">社团快讯</el-menu-item>
        <el-menu-item index="/index/regulations">规章制度</el-menu-item>
        <el-sub-menu index="/index/activity">
          <template #title>社团活动</template>
          <el-menu-item index="/index/activity">参加活动</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </div>
    <Carousel></Carousel>
  </div>
</template>
<script setup lang="ts" name="Nav">
import { useRouter, useRoute } from 'vue-router';
import { ref } from 'vue'
import Carousel from './Carousel.vue'
const router = useRouter();

const activeIndex = ref('/index/home')
//路由跳转后更新当前选中的nav
router.afterEach((to)=>{
  activeIndex.value = to.path
  
})
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>
<style scoped lang="less">
.el-menu {
  background-color: #bede8a;
  display: flex;
  justify-content: space-around;
}
</style>
