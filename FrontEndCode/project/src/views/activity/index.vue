<template>
  <div>
    <el-card style="max-width: 100%" class="main-card">
      <template #header>
        <div class="card-header">
          <el-row>
            <el-col :span="16">
              <el-row>
                <el-col :span="4">
                  <span style="font-size: 18px">活动管理</span>
                </el-col>
                <el-col :span="20">
                  <el-button type="primary" @click="addActivityFn">
                    新增活动
                  </el-button>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="8">
              <el-row>
                <el-col :span="10">
                  <!-- 搜索框 -->
                  <el-input
                    v-model="serchUserForm.description"
                    placeholder="活动描述关键词"
                    :suffix-icon="Search"
                    @keyup.enter="handleEnter"
                  />
                </el-col>
                <el-col :span="1"></el-col>
                <el-col :span="9">
                  <el-input
                    v-model="serchUserForm.name"
                    placeholder="活动名关键词"
                    :suffix-icon="Search"
                    @keyup.enter="handleEnter"
                  />
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </div>
      </template>
      <el-table :data="tableData" style="width: 100%; height: 600px">
        <el-table-column type="index" label="#" />
        <el-table-column prop="name" label="活动名" width="180" />
        <el-table-column prop="description" label="活动描述" />
        <el-table-column prop="activityPlace" label="活动地点" />
        <el-table-column prop="communityId" label="所属社团" />
        <el-table-column prop="startTime" label="开始时间" />
        <el-table-column prop="endTime" label="结束时间" />

        <el-table-column label="操作" align="right">
          <template #default="scope">
            <!-- 导出报表按钮 -->
            <el-tooltip
              class="box-item"
              effect="dark"
              content="导出活动成员报表"
              placement="top-start"
            >
              <el-button
                size="small"
                type="primary"
                @click="downloadExcel(scope.$index, scope.row)"
                circle
              >
                <el-icon>
                  <Download />
                </el-icon>
              </el-button>
            </el-tooltip>
            <el-button
              size="small"
              @click="handleEdit(scope.$index, scope.row)"
            >
              编辑
            </el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <el-pagination
          v-model:current-page="serchUserForm.page"
          v-model:page-size="serchUserForm.pageSize"
          :page-sizes="[5, 10, 15, 20]"
          :small="true"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </template>
      <!-- 添加活动框 -->
      <el-dialog
        v-model="addDialogVisible"
        title="新增活动"
        width="500"
        draggable
        :append-to-body="true"
        @closed="clearDialog"
      >
        <div>
          <el-form
            :model="addActivityForm"
            label-width="auto"
            style="max-width: 600px"
          >
            <el-form-item label="活动名">
              <el-input v-model="addActivityForm.name" />
            </el-form-item>
            <el-form-item label="所属社团">
              <el-select
                v-model="addActivityForm.communityId"
                filterable
                remote
                reserve-keyword
                placeholder="请输入社团关键词"
                remote-show-suffix
                :remote-method="remoteMethod"
                :loading="loading"
                style="width: 240px"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="活动地点">
              <el-input v-model="addActivityForm.activityPlace" />
            </el-form-item>
            <el-form-item label="活动描述">
              <el-input v-model="addActivityForm.description" type="textarea" />
            </el-form-item>
            <el-form-item label="活动时间">
              <el-date-picker
                v-model="dateTime"
                type="datetimerange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              />
            </el-form-item>
          </el-form>
        </div>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="addCancel">取消</el-button>
            <el-button type="primary" @click="addConfirm">确定</el-button>
          </div>
        </template>
      </el-dialog>

      <!-- 编辑活动框 -->
      <el-dialog
        v-model="dialogVisible"
        title="编辑活动"
        width="500"
        draggable
        :append-to-body="true"
        @closed="clearDialog"
      >
        <div>
          <el-form
            :model="addActivityForm"
            label-width="auto"
            style="max-width: 600px"
          >
            <el-form-item label="活动名">
              <el-input v-model="addActivityForm.name" />
            </el-form-item>
            <el-form-item label="所属社团">
              <el-select
                v-model="addActivityForm.communityId"
                filterable
                remote
                reserve-keyword
                placeholder="请输入社团关键词"
                remote-show-suffix
                :remote-method="remoteMethod"
                :loading="loading"
                style="width: 240px"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="活动地点">
              <el-input v-model="addActivityForm.activityPlace" />
            </el-form-item>
            <el-form-item label="活动描述">
              <el-input v-model="addActivityForm.description" type="textarea" />
            </el-form-item>
            <el-form-item label="活动时间">
              <el-date-picker
                v-model="dateTime"
                type="datetimerange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              />
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
    </el-card>
  </div>
</template>
<script setup lang="ts">
import { ElMessage, ElMessageBox } from 'element-plus'
import { ref, reactive, onMounted } from 'vue'
import { Search } from '@element-plus/icons-vue'
import {
  reqAllActivity,
  reqDeleteActivity,
  reqActivityById,
  reqUpdateActivity,
  reqAddActivity,
} from '@/api/activity'
import { formatDateTime } from '@/utils/time'
import { reqSearchCommunity } from '@/api/community'
import useUserStore from '@/store/modules/user'
import { reqUserInfo } from '@/api/user'
import request from '@/utils/request.ts'
const UserStore = useUserStore()
//社团选择相关
const dateTime = ref([])
interface ListItem {
  value: string
  label: string
}
const list = ref<ListItem[]>([])
const options = ref<ListItem[]>([])
const loading = ref(false)

onMounted(async () => {
  //查询所有社团获取的社团列表
  const data = await reqSearchCommunity(null)
  let states = []
  if (data.code == 200) {
    states = data.data
  }
  if (states.length != 0) {
    //将获取的值通过map逐项赋值给list
    list.value = states.map((item: any) => {
      return { value: `${item.id}`, label: `${item.communityName}` }
    })
  }
})

const remoteMethod = (query: string) => {
  //每次输入后搜索社团
  if (query) {
    loading.value = true
    setTimeout(() => {
      loading.value = false
      options.value = list.value.filter((item) => {
        return item.label.toLowerCase().includes(query.toLowerCase())
      })
    }, 200)
  } else {
    options.value = []
  }
}

//控制编辑框
let addDialogVisible = ref(false)
let dialogVisible = ref(false)
//消息函数
const myMessage = (msg: any, type: any) => {
  ElMessage({
    message: msg,
    type: type,
  })
}

//新增活动
function addActivityFn() {
  addDialogVisible.value = true
}
//新增活动对象
const addActivityForm = reactive({
  name: '',
  description: '',
  activityPlace: '',
  communityId: '',
  startTime: '',
  endTime: '',
})

//查询对象
const serchUserForm = reactive({
  name: '',
  description: '',
  communityId:'',
  pageSize: 5,
  page: 1,
})
//数据总数
let total = ref()
//表格数据
const tableData = ref([])

// 发送请求获取所有活动数据
async function getAllActivity() {
  let communityList = ref()
  const da = await reqSearchCommunity(null)
  if (da.data.length != 0) {
    //将获取的值通过map逐项赋值给list
    communityList.value = da.data.map((item: any) => {
      return { value: `${item.id}`, label: `${item.communityName}` }
    })
  }
  //获取当前用户信息
  const user:any = await reqUserInfo(UserStore.username)
  if(user.data.roleId!=1){
    serchUserForm.communityId = user.data.communityId
  }
  const { data } = await reqAllActivity(serchUserForm)
  tableData.value = data.records

  tableData.value.forEach((item) => {
    //找出communityId对应的元素matchedItem，将matchedItem的值赋值给tableData的communityId
    const matchedItem = communityList.value.find(
      (listItem) => listItem.value == item.communityId,
    )
    if (matchedItem) {
      item.communityId = matchedItem.label
    }
  })
  //分页相关
  total = data.total
}
//调用渲染数据
getAllActivity()

const handleEdit = async (index: number, row: any) => {
  console.log(row)
  //获取当前id的活动信息
  const { data } = await reqActivityById(row.id)
  Object.assign(addActivityForm, data)
  dateTime.value = [addActivityForm.startTime, addActivityForm.endTime]
  dialogVisible.value = true
}

const handleDelete = (index: number, row: any) => {
  ElMessageBox.confirm('您是否确认删除该活动?', '删除用户', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(async () => {
      //确认操作
      // 删除活动
      const data: any = await reqDeleteActivity(row.id)
      if (data.code == 200) {
        myMessage('删除活动成功', 'success')
        //删除成功刷新活动列表
        getAllActivity()
      } else {
        myMessage('删除活动失败', 'error')
      }
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })
}

const handleSizeChange = () => {
  getAllActivity()
}
const handleCurrentChange = () => {
  getAllActivity()
}
const handleEnter = () => {
  getAllActivity()
}
//将数据清空
function clearDialog() {
  Object.keys(addActivityForm).forEach((key) => {
    addActivityForm[key] = '' // 清空表单数据
  })
  dateTime.value = []
}
//取消修改
function Cancel() {
  dialogVisible.value = false
}
//确认修改
async function Confirm() {
  addActivityForm.startTime = formatDateTime(dateTime.value[0])
  addActivityForm.endTime = formatDateTime(dateTime.value[1])
  const data: any = await reqUpdateActivity(addActivityForm)
  if (data.code == 200) {
    myMessage(data.msg, 'success')
  } else {
    myMessage(data.msg, 'error')
  }
  getAllActivity()
  dialogVisible.value = false
}

//取消添加
function addCancel() {
  addDialogVisible.value = false
}
//确认添加
async function addConfirm() {
  addActivityForm.startTime = formatDateTime(dateTime.value[0])
  addActivityForm.endTime = formatDateTime(dateTime.value[1])
  const data: any = await reqAddActivity(addActivityForm)
  console.log(data)
  if (data.code == 200) {
    myMessage(data.msg, 'success')
    getAllActivity()
  } else {
    myMessage(data.msg, 'error')
  }
  addDialogVisible.value = false
}

//下载成员报表
function downloadExcel(index: number, row: any) {
  console.log(row)

  // 发送GET请求到后端接口，下载Excel文件
  request.get('application/activity/' + row.id, {
      responseType: 'blob', // 告诉axios返回的数据类型是Blob
    })
    .then((response:any) => {
      // 创建Blob对象
      const blob = new Blob([response.data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      })
      // 创建临时URL并创建链接下载
      const url = window.URL.createObjectURL(blob)
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', 'activity_members.xlsx') // 设置下载文件的名称
      document.body.appendChild(link)
      link.click()
      // 释放临时URL对象
      window.URL.revokeObjectURL(url)
    })
    .catch((error:any) => {
      console.error('下载失败:', error)
    })
}
</script>
<style scoped lang="less">
.main-card {
  height: 820px;
}
</style>
