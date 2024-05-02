<template>
  <header class="head">
    <div class="head-top">
      <p>欢迎访问湖北师范大学学生社团工作网站！</p>
      <div class="top-link">
        <div v-if="UserStore.username == null || UserStore.username == ''">
          <router-link to="/login">登录</router-link>
          <span>&#47;</span>
          <router-link to="/user">后台管理系统</router-link>
        </div>
        <div v-else>
          {{ UserStore.username }},你好
          <span>&nbsp;&nbsp;&nbsp;</span>
          <router-link to="/user">后台管理系统</router-link>
          <span style="margin-left: 40px ;cursor: pointer;" @click="getActivityByUserId">我的活动</span>
          <span style="margin-left: 40px;cursor: pointer;">
            <el-icon size="24px" @click="getApplications">
              <Message />
            </el-icon>
          </span>

        </div>
      </div>
    </div>
    <div class="head-bottom container">
      <!-- 学校社团的logo -->
      <div class="logo">
        <a href="#/index/home"></a>
      </div>
      <!-- 搜索框可搜索活动 -->
      <div class="search-box">
        <el-input placeholder="请输入关键词"></el-input>
        <el-button>搜索</el-button>
      </div>
    </div>

    <!-- 消息抽屉 -->
    <el-drawer v-model="drawer" title="message drawer" :with-header="false">
      <h1 style="font-size: 24px; font-weight: 700; text-align: center">
        消息通知
      </h1>
      <ul class="infinite-list" style="overflow: auto">
        <li v-for="item in applications" :key="item.id" class="infinite-list-item" @click="AuditFn(item)">
          <div class="list-content">
            {{ item.username }}申请加入{{ item.communityName }}
            <el-tag effect="dark" round :type="tagType[item.status].type">
              {{ tagType[item.status].label }}
            </el-tag>
          </div>
          <div class="user-time">
            发起人：{{ item.username }}&nbsp;&nbsp;{{ item.applicationTime }}
          </div>
        </li>
      </ul>
    </el-drawer>

    <!-- 审核对话框 -->
    <el-dialog v-model="auditDialogVisible" title="审核信息" width="500" draggable>
      <span>{{ curForm.username }}申请加入社团</span>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="refuseApply">拒绝</el-button>
          <el-button type="primary" @click="allowApply">批准</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 用户参加活动对话框 -->
    <el-dialog v-model="activityDialogVisible" title="我的活动" width="500" draggable @closed="activityList = []">
      <div class="activity_box">
        <el-empty description="空空如也" v-if="activityList.length==0"/>
        <div class="list-box" v-else>
          <div class="list-item" v-for="activity in activityList" :key="activity.id">
            <span class="activityName">{{ activity.name }}</span>
            <span class="time" v-show="activity.startTime">{{ activity.startTime?.slice(0,10) }}至{{ activity.endTime?.slice(0,10) }}</span>
            <el-button class="tuichu" @click="tuichu(activity)">退出</el-button>
          </div>
        </div>
      </div>
    </el-dialog>
  </header>
</template>
<script setup lang="ts" name="Header">
import { ref } from 'vue'
//导入用户仓库
import useUserStore from '@/store/modules/user'
import {
  reqAllActivityByUserId,
  reqApplicationsByCid,
  reqTuiChu,
  reqUpdateApplications,
  reqUserApplications,
} from '@/api/applications'
import { reqEditUser, reqUserById, reqUserInfo } from '@/api/user'
import { ElMessage } from 'element-plus'
import { reqSearchCommunity } from '@/api/community'
import { reqActivityById } from '@/api/activity'
const activityDialogVisible = ref(false)
const auditDialogVisible = ref(false)
const UserStore = useUserStore()
//获取社团列表
const communityList: any = ref([])
//当前登录用户参加活动列表
const activityList:any = ref([])
async function getCommunityList() {
  const data: any = await reqSearchCommunity(null)
  communityList.value = data.data
  // console.log(communityList.value.data);
}
getCommunityList()

//当前点击信息的对象
const applicationInfo: any = ref({
  id: '',
  userId: '',
  status: '',
})
//当前登录的用户有大用
const userInfo = ref({
  roleId: 3,
})
//点击信息对应的用户对象
const curForm = ref()
//tag标签的类型于审核状态对应
//    0待审核、1已批准、2已拒绝
const tagType = ref([
  { type: 'warning', label: '待审核' },
  { type: 'success', label: '已批准' },
  { type: 'error', label: '已拒绝' },
])
//社长点击消息可以弹出审核对话框（普通用户不行）
async function AuditFn(row: any) {
  // 当前用户是社长才能继续执行
  if (userInfo.value.roleId == 2) {
    try {
      const { data: curUser } = await reqUserById(row.userId)
      curUser.password = ''
      curForm.value = curUser
      applicationInfo.value = row
      if(row.status==0)
      auditDialogVisible.value = true
      else myMessage("不可重复审核","warning")
    } catch (error) {
      console.error('请求用户信息时出错：', error)
    }
  } else {
    console.log('我是普通成员')
  }
}
//消息函数
const myMessage = (msg: any, type: any) => {
  ElMessage({
    message: msg,
    type: type,
  })
}
const applications: any = ref([])
const drawer = ref(false)

//获取审核信息列表，这是展示给普通用户
async function getApplications() {
  drawer.value = true
  const { data: user } = await reqUserInfo(UserStore.username)
  //将用户信息存储起来
  userInfo.value = user
  const userId = user.id
  const role = user.roleId
  //role不为社长执行以下逻辑
  if (userId && role != 2) {
    const req: any = await reqUserApplications(userId)
    if (req.code == 200) {
      console.log(req.data)
      //找出社团id对应的社团名并赋值给每一项
      req.data.forEach((item:any) => {
        const curCommunity = communityList.value.find(
          (obj:any) => obj.id == item.communityId,
        )
        item.communityName = curCommunity.communityName
      })
      applications.value = req.data
      console.log(applications.value)
    } else {
      myMessage('获取信息失败，请重试', 'error')
    }
  } else if (userId && role == 2) {
    const req: any = await reqApplicationsByCid(user.communityId)
    if (req.code == 200) {
      console.log(req.data)
      //找出社团id对应的社团名并赋值给每一项
      req.data.forEach((item:any) => {
        const curCommunity = communityList.value.find(
          (obj:any) => obj.id == item.communityId,
        )
        item.communityName = curCommunity.communityName
      })
      applications.value = req.data
      console.log(applications.value)
    } else {
      myMessage('获取信息失败，请重试', 'error')
    }
  }
}

//拒绝用户申请
async function refuseApply() {
  const data: any = await reqUpdateApplications({
    id: applicationInfo.value.id,
    userId: applicationInfo.value.userId,
    status: 2,
  })
  if (data.code == 200) {
    myMessage('已拒绝', 'success')
    getApplications()
    auditDialogVisible.value = false
  } else {
    myMessage(data.msg, 'error')
  }
}

//批准用户请求
async function allowApply() {
  const data: any = await reqUpdateApplications({
    id: applicationInfo.value.id,
    userId: applicationInfo.value.userId,
    status: 1,
  })
  if (data.code == 200) {
    myMessage('已批准', 'success')
    getApplications()
    auditDialogVisible.value = false
    //批准后将用户的社团id设为申请的社团id,将用户的角色设为社团成员
    await reqEditUser({
      communityId: applicationInfo.value.communityId,
      id: applicationInfo.value.userId,
      roleId: 3,
    })
  } else {
    myMessage(data.msg, 'error')
  }
}

//获取用户参加的活动
async function getActivityByUserId() {
  const { data: user } = await reqUserInfo(UserStore.username)
  
  if(user.id){
    const {data:activityIdList} = await reqAllActivityByUserId(user.id)
    // 根据活动id获取活动信息
    // activityList
    for(let index in activityIdList){
      const {data} = await reqActivityById(activityIdList[index].activityId);
      if(data){
        data.userId = user.id
        activityList.value.push(data)
      }
    }
    // reqActivityById()
    activityDialogVisible.value = true;
  }
}

//退出活动
async function tuichu(activity:any){
  const reqdata = {userId:activity.userId,activityId:activity.id}
  const data:any = await reqTuiChu(reqdata)
  if(data.code==200){
    myMessage("已退出","success")
    activityList.value = []
    const {data:activityIdList} = await reqAllActivityByUserId(activity.userId)
    // 根据活动id获取活动信息
    // activityList
    for(let index in activityIdList){
      const {data} = await reqActivityById(activityIdList[index].activityId);
      if(data){
        activityList.value.push(data)
      }
    }
  }else{
    myMessage(data.msg,"error")
  }
  
  
}
</script>
<style scoped lang="less">
.infinite-list {
  height: 500px;
  padding: 0;
  margin: 0;
  list-style: none;
}

.infinite-list .infinite-list-item {
  border-bottom: 1px solid #ccc;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
  padding: 20px;
  position: relative;

  .list-content {
    font-weight: bold;
    text-align: left;
  }

  .user-time {
    position: absolute;
    bottom: 5px;
    right: 5px;
    font-size: 12px;
    color: #666;
  }
}

.infinite-list .infinite-list-item+.list-item {
  margin-top: 10px;
}

.head {
  .head-top {
    display: flex;
    justify-content: space-around;
    height: 40px;
    line-height: 40px;
    background-color: skyblue;
    color: #fff;

    >.top-link {
      a {
        color: #fff;
      }

      a:hover {
        color: antiquewhite;
      }
    }
  }

  .head-bottom {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 165px;

    >.logo {
      width: 500px;
      height: 100%;
      background: url('@/assets/images/logo.png') center center no-repeat;

      >a {
        display: block;
        width: 100%;
        height: 100%;
      }
    }

    >.search-box {
      display: flex;
      text-align: center;
    }
  }
}

.activity_box {
  .list-box {
    width: 100%; /* 设置列表框的宽度 */
    height: 200px; /* 设置列表框的高度 */
    overflow-y: auto; /* 添加垂直滚动条 */
    padding: 5px; /* 添加内边距 */
  }
  .list-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    height: 40px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin-bottom: 10px; /* 设置列表项之间的间距 */
    overflow: hidden;
    .activityName{
      flex: 8;
    }
    .time{
      flex: 10;
      font-size: 12px;
      color: #ccc;
      margin-right: 5px;
    }
    .tuichu {
      flex: 4;
    }
  }
}
</style>
