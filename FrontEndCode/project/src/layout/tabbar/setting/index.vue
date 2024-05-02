<template>
  <el-button
    size="small"
    icon="Refresh"
    circle
    @click="updateRefsh"
  ></el-button>
  <el-button
    size="small"
    icon="FullScreen"
    circle
    @click="fullScreen"
  ></el-button>
  <el-button size="small" icon="Setting" circle></el-button>
  <img :src="UserStore.avator" alt="头像" class="avatar" />
  <!-- 下拉菜单 -->
  <el-dropdown>
    <span class="el-dropdown-link">
      {{ UserStore.username }}
      <el-icon class="el-icon--right"><arrow-down /></el-icon>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item icon="Plus" @click="logout">
          退出登录
        </el-dropdown-item>
        <el-dropdown-item icon="HomeFilled" @click="toFront">
          返回前台
        </el-dropdown-item>
        <el-dropdown-item icon="User" @click="getMyInfo">
          个人中心
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
  <!-- 个人信息对话框dialog -->
  <el-dialog
    v-model="dialogVisible"
    title="个人信息"
    width="500"
    draggable
    :append-to-body="true"
  >
    <div>
      <el-form
        :model="userForm"
        label-width="auto"
        style="max-width: 600px"
        ref="ruleFormRef"
      >
        <el-form-item label="姓名">
          <el-input v-model="userForm.name" />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="userForm.username" />
        </el-form-item>
        <el-form-item label="性别">
          <el-col :span="12">
            <el-select v-model="userForm.sex">
              <el-option label="男" value="男" />
              <el-option label="女" value="女" />
            </el-select>
          </el-col>
        </el-form-item>

        <el-form-item label="密码">
          <el-input v-model="userForm.password" />
        </el-form-item>

        <el-form-item label="联系方式">
          <el-input v-model="userForm.phone" />
        </el-form-item>
        <el-form-item label="职位">
          <el-col :span="12">
            <el-input v-bind:value="positionMap[userForm.roleId]" disabled />
          </el-col>
        </el-form-item>

        <el-form-item label="用户状态">
          <el-switch v-model="Status" />
        </el-form-item>
      </el-form>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="Cancel">取消</el-button>
        <el-button type="primary" @click="Confirm">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts" name="Setting">
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
// 引入setting相关的自定义piani仓库
import useLayoutSettingStore from '@/store/modules/setting'
//引入user相关仓库
import useUserStore from '@/store/modules/user'
//引入路由器用于退出登录跳转到登录页
import { useRouter, useRoute } from 'vue-router'
//获取router路由器
let $router = useRouter()
//获取当前route路由
let $route = useRoute()
//获取SettingStore仓库对象
let SettingStore = useLayoutSettingStore()
//获取UserStore仓库对象
let UserStore = useUserStore()

//用于控制对话框的弹出
const dialogVisible = ref(false)
//用户请求相关api
import { reqUserInfo, reqChangeUser } from '@/api/user'

//成功弹窗
const MessageFn = (msg: any, type: any) => {
  ElMessage({
    message: msg,
    type: type,
  })
}
//表单DOM对象ref
const ruleFormRef = ref()

//点击刷新
function updateRefsh() {
  SettingStore.refsh = !SettingStore.refsh
}
//全屏功能
function fullScreen() {
  //DOM对象的属性判断当前是否为全屏，【全屏：true，非全屏：null】
  let full = document.fullscreenElement
  //切换模式
  if (!full) {
    //文档根节点requestFullscreen实现全屏
    document.documentElement.requestFullscreen()
  } else {
    //退出全局模式
    document.exitFullscreen()
  }
}
//退出登录
function logout() {
  //第一件事情：需要向服务器发请求（退出登录接口）
  //第二件事情：仓库当中关于用户相关的数据清空【token|username|avatar】
  //第三件事情：跳转到登录页
  UserStore.userLogout()
  $router.push({ path: '/login', query: { redirect: $route.path } })
}
// 定义一个响应式 userForm 对象
let userForm = reactive({
  name: '',
  sex: '',
  roleId: '',
  username: '',
  password: '',
  phone: '',
  status: '',
})
// 获取用户个人信息
async function getMyInfo() {
  try {
    // 获取用户信息
    const data = await reqUserInfo(UserStore.username)
    // 将后端获取的数据赋值给 userForm 对象
    Object.assign(userForm, data.data)
    //密码腾空
    userForm.password = ''
    // 渲染完毕后展示对话框
    dialogVisible.value = true
  } catch (error) {
    console.error('获取用户信息失败：', error)
  }
}

//取消修改
function Cancel() {
  dialogVisible.value = false
  //将数据清空
  Object.assign(userForm, {})
}

//确认修改
async function Confirm() {
  try {
    const data = (await reqChangeUser(userForm)) as any
    if (data.code === 200) {
      MessageFn('修改个人信息成功', 'success')
    } else {
      MessageFn(data.msg, 'error')
    }
  } catch (error) {
    MessageFn('修改个人信息失败', error)
  }
  dialogVisible.value = false
}

//返回前台首页
function toFront() {
  $router.push('/index')
}

//用户信息区域

const positionMap = {
  '1': '社团管理员',
  '2': '社长',
  '3': '社团成员',
  // 添加更多的职位ID和职位名称
}

//经过计算属性处理的status，因为后端传过来的1，2我们要true和false
const Status = computed({
  get: () => {
    return userForm.status == '1' ? true : false
  },
  set: (newValue) => {
    userForm.status = newValue ? '1' : '2' // 如果为 true，则设置为 1，否则设置为 2
  },
})
</script>
<style scoped lang="less">
.avatar {
  margin: 0px 10px;
  height: 24px;
  width: 24px;
  border-radius: 50%;
}
</style>
