<template>
  <el-icon @click="changeStatu()" size="20px">
    <component :is="fold ? 'Expand' : 'Fold'"></component>
  </el-icon>
  <!-- 左侧面包屑 -->
  <el-breadcrumb separator-icon="ArrowRight">
    <el-breadcrumb-item
      v-for="item in $route.matched"
      :key="item.path"
      :to="{ path: item.path }"
      v-show="item.meta.title"
    >
      <el-icon>
        <component :is="item.meta.icon"></component>
      </el-icon>
      <!-- 面包屑展示路由标题 -->
      <span>{{ item.meta.title }}</span>
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>
<script setup lang="ts" name="Breadcrumb">
import useLayoutSettingStore from '@/store/modules/setting'
import { storeToRefs } from 'pinia'
import { useRoute } from 'vue-router'

//使用路由对象
let $route = useRoute()

let SettingStore = useLayoutSettingStore()
//将store中的数据提取并通过storeToRefs设置响应式
let { fold } = storeToRefs(SettingStore)

//控制折叠图标的变量
function changeStatu() {
  fold.value = !fold.value
}
</script>
<style scoped>
.el-icon {
  margin: 0 15px;
}
</style>
