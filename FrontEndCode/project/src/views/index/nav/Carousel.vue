<template>
  <div class="carousel-box">
    <el-carousel
      height="450px"
      trigger="click"
      :interval="5000"
      pause-on-hover
      indicator-position="outside"
    >
      <el-carousel-item v-for="item in imgList" :key="item.id">
        <a
          rel="nofollow"
          :href="item.href"
          class="a-dis"
          :style="{
            // 'background': 'url('+item.url+') center center no-repeat',
            background: 'url(' + item.url + ') center center no-repeat',
            // 'background-size': 'auto 100%'
            'background-size': 'cover',
          }"
        ></a>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<style scoped lang="less">
.a-dis {
  display: inline-block;
  width: 100%;
  height: 100%;
}
</style>
<script lang="ts" setup name="Carousel">
const imgList = [
  {
    id: 1,
    url: new URL('@/assets/images/lun1.jpg', import.meta.url),
    href: '#/index/home',
  },
  {
    id: 2,
    url: new URL('@/assets/images/lun2.jpg', import.meta.url),
    href: '#/index/home',
  },
  {
    id: 3,
    url: new URL('@/assets/images/lun3.jpg', import.meta.url),
    href: '#/index/home',
  },
]
</script>
