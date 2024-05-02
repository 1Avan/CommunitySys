<template>
  <div class="footer">
    <div class="container content">
      <div class="fl p-list">
        <p>Copyright © 2024湖北师范大学社团联合会 版权所有.</p>
        <p>地址:湖北师范大学2105班</p>
        <p>邮编:437400</p>
      </div>
      <div class="fr logo">
        <a href="https://www.hbnu.edu.cn/" target="_blank">
          <img src="../../../../src/assets/images/hs.png" alt="" />
        </a>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts" name="Footer"></script>
<style scoped lang="less">
.footer {
  margin-top: 30px;
  width: 100%;
  height: 200px;
  background: #383838 center;
  .content {
    .p-list {
      padding-top: 45px;
      width: 378px;
      p {
        font-size: 14px;
        color: #6b6b6b;
        line-height: 32px;
      }
    }
    .logo {
      padding-top: 45px;
    }
  }
}
</style>
