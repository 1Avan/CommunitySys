<template>
  <div>
    <ul
      v-infinite-scroll="load"
      class="infinite-list"
      style="overflow: auto"
      :infinite-scroll-disabled="communityList.length == total"
    >
      <li
        v-for="item in communityList"
        :key="item.id"
        class="infinite-list-item"
      >
        <el-row style="display: flex; align-items: center">
          <el-col :span="4">
            <span>{{ item.communityName }}</span>
          </el-col>
          <el-col :span="18" style="overflow: hidden; text-overflow: ellipsis">
            <span>{{ item.description }}</span>
          </el-col>
          <el-col :span="2">
            <span class="detail" @click="detailFn(item)">查看详情</span>
          </el-col>
        </el-row>
      </li>
    </ul>
    <!-- 详细框 -->
    <el-dialog
      v-model="detailDialogVisible"
      :title="CommunityDetailForm.communityName"
      width="800"
      draggable
      @closed="clearFn"
    >
      <el-input
        disabled
        :value="CommunityDetailForm.description"
        style="width: 400px"
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
<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue'
import { reqAllCommunity } from '@/api/community'
// 导入用户仓库
import useUserStore from '@/store/modules/user'
import { reqUserInfo } from '@/api/user'
import { reqUserToCommunity } from '@/api/applications'
import { ElMessage } from 'element-plus'
const UserStore = useUserStore()
let detailDialogVisible = ref(false)
const searchCommunityForm = reactive({
  communityName: '',
  page: 1,
  pageSize: 5,
  description: '',
})

//消息函数
const myMessage = (msg: any, type: any) => {
  ElMessage({
    message: msg,
    type: type,
  })
}
//获取社团列表
const total = ref(0)
const communityList: any = ref([])
onMounted(async () => {
  const curlist = await reqAllCommunity(searchCommunityForm)
  total.value = curlist.data.total
  communityList.value = communityList.value.concat(curlist.data.records)
})
const load = async () => {
  searchCommunityForm.page += 1
  const curlist = await reqAllCommunity(searchCommunityForm)
  communityList.value = communityList.value.concat(curlist.data.records)
}

//社团对象
const CommunityDetailForm = reactive({
  id: '',
  communityName: '',
  description: '',
})
function detailFn(item: any) {
  Object.assign(CommunityDetailForm, item)
  detailDialogVisible.value = true
}
//对话框关闭的回调
function clearFn() {
  Object.keys(CommunityDetailForm).forEach((key) => {
    CommunityDetailForm[key] = ''
  })
}

//申请加入社团
async function applicationFn() {
  //申请加入社团需要向后端发送社团id和个人id
  // console.log(CommunityDetailForm.id,UserStore.username);
  //由于仓库没有存储用户id所以需要根据用户名查询获取
  const { data } = await reqUserInfo(UserStore.username)
  const UserId = data.id

  if (UserId) {
    const req: any = await reqUserToCommunity({
      userId: UserId,
      communityId: CommunityDetailForm.id,
      username: UserStore.username,
    })
    if (req.code == 200) {
      myMessage('申请成功，等待社长审核', 'success')
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
  border-bottom: 1px solid #ccc;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  height: 80px;
  margin: 10px;
  line-height: 80px;
  padding: 0px 10px;
}
.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}
.detail {
  color: #ccc;
  font-size: 12px;
  cursor: pointer;
}
.detail:hover {
  color: skyblue;
}
</style>
