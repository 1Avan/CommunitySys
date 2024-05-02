<template>
  <div class="container">
    <ul
      v-infinite-scroll="load"
      class="infinite-list"
      style="overflow: auto"
      :infinite-scroll-disabled="activityList.length == total"
    >
      <el-empty description="暂无活动" v-if="total == 0" :image-size="300" />
      <li
        v-for="item in activityList"
        :key="item"
        class="infinite-list-item"
        @click="detailFn(item)"
      >
        <h1>{{ item.name + ' | ' + item.description }}</h1>
        <div class="item-footer">
          <span>{{ getClubName(item.communityId) }}</span>
          <span v-if="item.startTime">开始时间：{{ item.startTime }}</span>
          <span v-if="item.endTime">结束时间：{{ item.endTime }}</span>
        </div>
      </li>
    </ul>

    <!-- 详细框 -->
    <el-dialog
      v-model="detailDialogVisible"
      :title="ActivityDetailForm.name"
      width="800"
      draggable
      @closed="clearFn"
    >
      <el-input
        disabled
        :value="ActivityDetailForm.description"
        style="width: 600px"
        :autosize="{ minRows: 2, maxRows: 4 }"
        type="textarea"
      />
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="detailDialogVisible = false">取消</el-button>
          <el-button type="warning" @click="applicationFn()">
            申请加入
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup name="activity">
import { reqActivityByCid, reqAllActivity } from '@/api/activity'
import { reqJoinActivity } from '@/api/applications'
import { reqSearchCommunity } from '@/api/community'
import { reqUserInfo } from '@/api/user'
import useUserStore from '@/store/modules/user'
import { ElMessage } from 'element-plus'
import { onMounted, reactive, ref } from 'vue'
//消息函数
const myMessage = (msg: any, type: any) => {
  ElMessage({
    message: msg,
    type: type,
  })
}
//详细框
let detailDialogVisible = ref(false)
//用户仓库
const UserStore = useUserStore()
//当前登录的用户有大用
const userInfo: any = ref({})
//获取社团列表
const communityList: any = ref([])
async function getCommunityList() {
  const data: any = await reqSearchCommunity(null)
  communityList.value = data.data
}
//活动对象
const ActivityDetailForm: any = reactive({})
getCommunityList()
const searchActivity = reactive({
  page: 1,
  pageSize: 5,
})
const total = ref(0)
const activityList: any = ref([])
onMounted(async () => {
  const { data: user } = await reqUserInfo(UserStore.username)
  //将用户信息存储起来
  userInfo.value = user
  if (userInfo.value.roleId == 1 || userInfo.value.roleId == null) {
    const curlist = await reqAllActivity(searchActivity)
    total.value = curlist.data.total
    activityList.value = activityList.value.concat(curlist.data.records)
  } else {
    const curlist = await reqActivityByCid(userInfo.value.communityId)
    total.value = curlist.data.length
    activityList.value = curlist.data
  }
})

//对话框关闭的回调
function clearFn() {
  Object.keys(ActivityDetailForm).forEach((key) => {
    ActivityDetailForm[key] = ''
  })
}

const load = async () => {
  // 在函数声明的前面加上 async 关键字
  // 增加 try-catch 语句来处理可能出现的异常情况
  if (userInfo.value.roleId == 1 || userInfo.value.roleId == null) {
    try {
      searchActivity.page += 1
      const curlist = await reqAllActivity(searchActivity)
      // 将新获取的数据追加到 activityList.value 中
      activityList.value = activityList.value.concat(curlist.data.records)
    } catch (error) {
      // 捕获可能出现的异常并进行处理
      console.error('加载活动列表时发生错误:', error)
    }
  }
}
// 创建一个函数来根据社团 ID 获取社团名
const getClubName = (clubId: any) => {
  const club = communityList.value.find((club) => club.id == clubId)
  return club ? club.communityName : '未知社团'
}

function detailFn(item: any) {
  Object.assign(ActivityDetailForm, item)
  detailDialogVisible.value = true
  console.log(ActivityDetailForm)
}

//报名活动
async function applicationFn() {
  //报名活动需要向后端发送活动id和个人id
  const userId = userInfo.value.id
  if (userId) {
    const req: any = await reqJoinActivity({
      userId: userId,
      activityId: ActivityDetailForm.id,
    })
    if (req.code == 200) {
      myMessage('参加活动成功', 'success')
    } else {
      myMessage(req.msg, 'error')
    }
  }
  detailDialogVisible.value = false
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
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border-bottom: 1px solid #ccc;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  height: 120px;
  margin: 10px;
  padding: 10px 10px;
  overflow: hidden;

  h1 {
    font-size: 16px;
    line-height: 24px;
    color: #222222;
  }

  .item-footer {
    font-size: 14px;
    line-height: 20px;
    color: #999;

    span {
      margin-right: 20px;
    }
  }
}

.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}
</style>
