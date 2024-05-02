<template>
  <div>
    <!-- header头部区域 -->
    <Header></Header>
    <!-- 内容区域 -->
    <div class="content">
      <!-- 导航栏+轮播图 -->
      <div class="nav-box">
        <Nav></Nav>
      </div>

      <!-- 内容1 -->
      <div class="con1">
        <!-- 这里放置路由窗口 -->
        <router-view></router-view>
      </div>
    </div>
    <!-- footer尾部区域 -->
    <Footer></Footer>
  </div>
</template>
<script setup lang="ts">
//头部区域
import Header from './header/header.vue'
//导航栏
import Nav from './nav/nav.vue'
//尾部
import Footer from './footer/footer.vue'
</script>
<style scoped lang="less">
//内容区域样式开始
.content {
  .nav-box {
    background-color: #bede8a;
  }
  .con1 {
    margin-top: 20px;
  }
}
//内容区域样式结束
</style>
