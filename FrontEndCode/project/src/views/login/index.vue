<template>
  <div class="login_container">
    <el-row>
      <!-- 当页面宽度小于768 xs起作用 -->
      <el-col :span="12" :xs="0"></el-col>
      <el-col :span="12" :xs="24">
        <!-- 登录注册区域 -->
        <el-tabs
          v-model="activeName"
          class="demo-tabs"
          @tab-click="handleClick"
        >
          <el-tab-pane label="用户注册" name="first">
            <el-form :model="registerForm" :rules="rules" ref="registerForms">
              <el-form-item prop="username">
                <el-input
                  v-model="registerForm.username"
                  :prefix-icon="User"
                  placeholder="请输入用户名"
                ></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  v-model="registerForm.password"
                  type="password"
                  :prefix-icon="Lock"
                  show-password
                  placeholder="请输入密码"
                ></el-input>
              </el-form-item>
              <el-form-item prop="name">
                <el-input
                  v-model="registerForm.name"
                  :prefix-icon="Avatar"
                  placeholder="请输入姓名"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  :loading="isLoading"
                  type="primary"
                  class="login_btn"
                  @click="register()"
                  :disable="isLoading"
                >
                  注册
                </el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="登录" name="second">
            <el-form
              class="login_form"
              :model="loginForm"
              :rules="rules"
              ref="loginForms"
            >
              <el-form-item prop="username">
                <el-input
                  v-model="loginForm.username"
                  :prefix-icon="User"
                  placeholder="请输入用户名"
                ></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  v-model="loginForm.password"
                  type="password"
                  :prefix-icon="Lock"
                  show-password
                  placeholder="请输入密码"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  :loading="isLoading"
                  type="primary"
                  class="login_btn"
                  @click="loginFn()"
                  :disable="isLoading"
                >
                  确定登录
                </el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
  </div>
</template>
<script setup lang="ts">
import { reactive, ref } from 'vue'
import { User, Lock, Avatar } from '@element-plus/icons-vue'
import { useRouter, useRoute } from 'vue-router'
import useUserStore from '@/store/modules/user.ts'
import { ElNotification } from 'element-plus'
//引入获取当前时间的函数
import { getTime } from '@/utils/time.ts'
//引入用户注册api
import { reqRegister } from '@/api/user/index'

import type { TabsPaneContext } from 'element-plus'

const activeName = ref('second')

const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event)
}
// 定义一个变量控制按钮是否加载
let isLoading = ref(false)
//获取路由器
let $router = useRouter()
//获取当前路由,将路由的redirect解构出来
let {
  query: { redirect },
} = useRoute()

//获取仓库
let useStore = useUserStore()
//获取表单根元素
let loginForms = ref()
let registerForms = ref()
//登录数据
let loginForm = reactive({
  username: 'admin',
  password: '123456',
})
//注册数据
let registerForm = reactive({
  username: 'admin',
  password: '123456',
  name: 'lisi',
})
//注册按钮的回调方法
async function register() {
  //保证每一个校验通过再发送请求
  await registerForms.value.validate()
  isLoading.value = true
  //注册成功
  const data: any = await reqRegister(registerForm)
  //异步请求传递过来promise对象是请求成功就往下执行
  isLoading.value = false
  console.log(data)
  if (data.code === 200) {
    ElNotification({
      message: '注册成功',
      type: 'success',
    })
  } else {
    // 登录失败提示信息
    ElNotification({
      title: '错误',
      message: data.msg,
      type: 'error',
    })
  }
}

//登录按钮回调方法
async function loginFn() {
  // console.log(loginForms.value.validate());
  //保证每一个校验通过再发送请求
  await loginForms.value.validate()
  //点击登录按钮开始加载
  isLoading.value = true
  //点击登录按钮
  //通知仓库发登录请求
  //请求成功->展示首页并将用户信息存储在用户小仓库中
  //请求失败->弹出失败信息、
  try {
    //登录成功
    await useStore.userLogin(loginForm)
    //异步请求传递过来promise对象是请求成功就往下执行
    //编程实导航跳转到首页，两个情况路由有query参数redirect或没有
    if (redirect) {
      $router.push({ path: redirect as string })
    } else {
      $router.push('/')
    }

    isLoading.value = false
    ElNotification({
      title: `Hi,${getTime()}好~~~`,
      message: '登录成功',
      type: 'success',
    })
  } catch (error) {
    //登录失败提示信息
    ElNotification({
      title: '错误',
      message: (error as Error).message,
      type: 'error',
    })
  } finally {
    //请求结束，停止加载
    isLoading.value = false
  }
}

//自定义校验规则方法
function validateUserName(rule: any, value: any, callback: any) {
  if (/^[\w-]{4,16}$/.test(value)) {
    callback()
  } else {
    callback(new Error('用户名输入4-16位'))
  }
}
//定义表单校验规则
const rules = {
  username: [
    // element官方提供的校验规则
    // { required: true, message: '用户名不能为空', trigger: 'blur' },
    //自定义校验规则
    { validator: validateUserName, trigger: 'change' },
  ],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
}
</script>
<style scoped lang="less">
.login_container {
  width: 100%;
  // 对于视窗（viewpoint）的宽度或者高度。1vw等于视窗宽度的1%，1vh等于视窗高度的1%。
  height: 100vh;
  background: url('@/assets/images/background.jpg') no-repeat;
  background-size: cover;
}
.demo-tabs {
  position: relative;
  width: 70%;
  padding: 40px;
  top: 30vh;
  background-color: #adc;
  opacity: 0.8;
  background-size: cover;
  border-radius: 10px;
  text-align: center;
  .login_btn {
    width: 100%;
  }
}
</style>
