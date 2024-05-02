<template>
  <div class="con-1 container clearfix">
    <!-- 活力社团 -->
    <div class="nrfl fl">
      <h3>{{ titleText }}</h3>
      <ul class="cleafix">
        <li class="hoverl" v-if="titleText == '优秀社团'">
          <a href="#/index/community/huoli" title="活力社团" class="hover">
            活力社团
          </a>
        </li>
        <li class="hoverl" v-else>
          <a href="#/index/community/youxiu" title="优秀社团" class="hover">
            优秀社团
          </a>
        </li>
      </ul>
    </div>

    <div class="nrfr fr clearfix">
      <div class="dpdq">
        <h2>{{ titleText }}</h2>
        <div class="cleafix fr">
          <el-breadcrumb separator=">>">
            <el-breadcrumb-item :to="{ path: '/index/community' }">
              当前位置
            </el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/index/community/huoli' }">
              活力社团
            </el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/index/community/youxiu' }">
              优秀社团
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
      </div>
      <router-view></router-view>
    </div>
  </div>
</template>
<script setup lang="ts" name="ConOne">
import { computed, ref } from 'vue'
import { useRoute } from 'vue-router'
const route = useRoute()
// console.log(route.path);
//每次路由path变化就更新titleText
const titleText = computed(() => {
  if (route.path == '/index/community/huoli') return '活力社团'
  else return '优秀社团'
})
</script>
<style scoped lang="less">
.con-1 {
  margin: 50px auto;

  .nrfl {
    width: 220px;

    > h3 {
      height: 70px;
      line-height: 70px;
      font-size: 35px;
      color: #ffffff;
      background-color: #fa710d;
      text-align: center;
      position: relative;
      margin-top: -90px;
    }

    ul {
      padding-bottom: 20px;
      border: 1px solid #e9e9e9;

      li {
        height: 54px;
        line-height: 54px;
        text-align: center;

        a {
          display: block;
          width: 188px;
          margin: auto;
          font-size: 17px;
          border-bottom: 1px solid #e9e9e9;
          background-image: url(../images/icon10_06.png);
          background-position: right center;
          background-repeat: no-repeat;
        }

        a.hover {
          background-image: url(../images/icon10_03.png);
          color: #fa710d;
        }
      }
    }
  }

  .nrfr {
    width: 940px;
    padding: 20px 0px 30px;
    background-color: #ffffff;
    min-height: 600px;

    .dpdq {
      height: 45px;
      line-height: 45px;
      border-bottom: 1px solid #cdcfd2;
    }
    h2 {
      display: inline-block;
      border-bottom: 3px solid #fa710d;
      font-size: 18px;
      color: #333;
      padding: 0px 12px;
      height: 44px;
    }
    .el-breadcrumb {
      background: url(../../../../src/assets/images/house_03.png) no-repeat left
        center;
      padding-left: 20px;
      height: 45px;
      line-height: 45px;
    }
    .el-breadcrumb-item {
      display: inline-block;
      line-height: 45px;
    }
  }
}
</style>
