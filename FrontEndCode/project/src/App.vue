<template>
  <div>
    <router-view></router-view>
  </div>
</template>
<script setup lang="ts">
// 获取用户相关的小仓库
import useUserStore from '@/store/modules/user'
let userStore = useUserStore()
function getUserInfo() {
  const userInfo = JSON.parse(localStorage.getItem('userInfo') as string)
  // console.log(userInfo);
  //每次刷新给username和avator重新赋值
  if (userInfo) {
    if (userInfo.username) {
      userStore.username = userInfo.username
    }
    if (userInfo.avator) {
      userStore.avator = userInfo.avator
    }
    if (userInfo.roleId) {
      userStore.roleId = userInfo.roleId
    }
  }
}
getUserInfo()
</script>
<style scoped></style>
