<template>
  <!-- svg:图标外层容器节点，内部需要与use标签结合使用 -->
  <svg :style="{ width: width, height: height }">
    <!-- xlink:href执行用哪一个图标，属性值务必是#icon-图标名字 -->
    <!-- use标签fill属性可以设置颜色 -->
    <use :xlink:href="prefix + name" :fill="color"></use>
  </svg>
</template>
<script setup lang="ts">
// 接收父组件传递的参数
defineProps({
  // href属性值前缀
  prefix: {
    type: String,
    default: '#icon-',
  },
  //提供使用图标名字
  name: {
    type: String,
  },
  //接收父组件传递的颜色
  color: {
    type: String,
    default: '',
  },
  //接收宽高
  width: {
    type: String,
    default: '16px',
  },
  height: {
    type: String,
    default: '16px',
  },
})
</script>
<style scoped></style>
