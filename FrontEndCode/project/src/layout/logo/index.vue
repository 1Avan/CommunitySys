<template>
  <div class="logo">
    <img :src="logoUrl" alt="" />
    <p v-if="!SettingStore.fold">{{ title }}</p>
  </div>
</template>
<script setup lang="ts" name="Logo">
//引入logo、title的配置对象
import setMessage from '@/setting'
//引入小仓库判断是否折叠菜单
import useLayoutSettingStore from '@/store/modules/setting'
let { title, logoUrl } = setMessage

//获取仓库对象
let SettingStore = useLayoutSettingStore()
</script>
<style scoped lang="less">
.logo {
  height: @base-logo-height;
  display: flex;
  align-items: center;
  color: black;
  font-size: 16px;
  justify-content: center;
  transition: all 0.3s;
  img {
    height: @base-logo-height;
    border-radius: 50%;
    margin-right: 5px;
  }
}
</style>
