<template>
  <template v-for="item in menuList" :key="item.path">
    <!-- 没有子路由 -->
    <el-menu-item
      v-if="!item.children && !item.meta.hidden&&item.meta.allowRoleId?.includes(UserStore.roleId)"
      :index="item.path"
    >
      <span>
        <el-icon>
          <component :is="item.meta.icon"></component>
        </el-icon>
      </span>
      <template #title>
        <span>{{ item.meta.title }}</span>
      </template>
    </el-menu-item>
    <!-- 有一个子路由 -->
    <el-menu-item
      v-if="item.children && item.children.length == 1 && !item.meta.hidden"
      :index="item.children[0].path"
    >
      <span>
        <el-icon>
          <component :is="item.children[0].meta.icon"></component>
        </el-icon>
      </span>
      <template #title>
        <span>{{ item.children[0].meta.title }}</span>
      </template>
    </el-menu-item>
    <!-- 有子路由且不止一个 -->
    <el-sub-menu
      :index="item.path"
      v-if="item.children && item.children.length > 1 && !item.meta.hidden "
    >
      <template #title>
        <el-icon>
          <component :is="item.meta.icon"></component>
        </el-icon>
        <span>{{ item.meta.title }}</span>
      </template>
      <!-- 这里Menu组件递归调用了自己 -->
      <Menu :menuList="item.children"></Menu>
    </el-sub-menu>
  </template>
</template>
<script setup lang="ts" name="Menu">
//获取父组件传递过来的全部路由数组
defineProps(['menuList'])
//从仓库获取roleId
import useUserStore from '@/store/modules/user'
const UserStore = useUserStore()
console.log(UserStore.roleId);

</script>
<style scoped lang="less">
.el-menu-item:hover {
  background-color: antiquewhite;
}
</style>
