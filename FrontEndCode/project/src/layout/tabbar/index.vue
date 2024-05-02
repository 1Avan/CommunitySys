<template>
  <div class="tabbar">
    <div class="tabbar_left">
      <!-- 封装的面包屑组件 -->
      <BreadCrumb></BreadCrumb>
    </div>
    <!-- 右侧功能区域 -->
    <div class="tabbar_right">
      <Setting></Setting>
    </div>
  </div>
</template>
<script setup lang="ts" name="Tabbar">
import BreadCrumb from './breadcrumb/index.vue'
import Setting from './setting/index.vue'
</script>
<style scoped lang="less">
.tabbar {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  .tabbar_left {
    display: flex;
  }
  .tabbar_right {
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>
