<template>
  <div>
    <el-card style="max-width: 100%" class="main-card">
      <template #header>
        <div class="card-header">
          <el-row>
            <el-col :span="16">
              <el-row>
                <el-col :span="4">
                  <span style="font-size: 18px">用户管理</span>
                </el-col>
                <el-col :span="20">
                  <el-button type="primary" @click="addUserFn" v-if="UserStore.roleId!=3">
                    新增用户
                  </el-button>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="8">
              <el-row>
                <el-col :span="12">
                  <!-- 搜索框 -->
                  <el-input
                    v-model="serchUserForm.name"
                    placeholder="输入用户姓名关键词"
                    :suffix-icon="Search"
                    @keyup.enter="handleEnter"
                  />
                </el-col>
                <el-col :span="2"></el-col>
                <el-col :span="10">
                  <!-- 选择框 -->
                  <el-select
                    v-model="serchUserForm.sex"
                    placeholder="请选择性别"
                    clearable
                  >
                    <el-option label="男" value="男" />
                    <el-option label="女" value="女" />
                  </el-select>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </div>
      </template>
      <el-table :data="tableData" style="width: 100%; height: 600px">
        <el-table-column type="index" label="#" />
        <el-table-column prop="username" label="用户名" width="180" />
        <el-table-column prop="name" label="姓名" width="180" />
        <el-table-column prop="sex" label="性别" />
        <el-table-column prop="phone" label="联系方式" />
        <el-table-column prop="communityId" label="所属社团" />
        <el-table-column prop="roleId" label="职位" :formatter="formatRole" />
        <el-table-column prop="status" label="用户状态">
          <template #default="scope">
            <el-tag :type="formatStatus(scope.row.status).type">
              {{ formatStatus(scope.row.status).status }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="right" v-if="UserStore.roleId!=3">
          <template #default="scope">
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

      <!-- 编辑框 -->
      <el-dialog
        v-model="dialogVisible"
        title="编辑用户"
        width="500"
        draggable
        @closed="clearDialog"
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
            <el-form-item label="所属社团">
              <el-select
                v-model="userForm.communityId"
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
            <el-form-item label="联系方式">
              <el-input v-model="userForm.phone" />
            </el-form-item>
            <el-form-item label="职位">
              <el-col :span="12">
                <el-select
                  v-model="userForm.roleId"
                  placeholder="Select"
                  size="large"
                  style="width: 240px"
                >
                  <el-option
                    v-for="item in Position"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
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

      <!-- 新增用户框 -->
      <el-dialog
        v-model="addDialogVisible"
        title="新增用户"
        width="500"
        draggable
        @closed="clearDialog"
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
            <el-form-item label="所属社团">
              <el-select
                v-model="userForm.communityId"
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

            <el-form-item label="联系方式">
              <el-input v-model="userForm.phone" />
            </el-form-item>
            <el-form-item label="职位">
              <el-col :span="12">
                <el-select
                  v-model="userForm.roleId"
                  placeholder="Select"
                  size="large"
                  style="width: 240px"
                >
                  <el-option
                    v-for="item in Position"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-col>
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
    </el-card>
  </div>
</template>
<script setup lang="ts">
import { ElMessage, ElMessageBox } from 'element-plus'
import { ref, reactive, computed, onMounted } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { User } from './type'
import {
  reqAllUser,
  reqDeleteUser,
  reqUserById,
  reqAddUser,
  reqEditUser,
  reqUserInfo,
} from '@/api/user'
import { reqSearchCommunity } from '@/api/community'
import useUserStore from '@/store/modules/user'
const UserStore = useUserStore()
//当前登录的用户个人信息
const curUser:any = ref({})
interface ListItem {
  value: string
  label: string
}
const list = ref<ListItem[]>([])
const loading = ref(false)
const options = ref<ListItem[]>([])
onMounted(async () => {
  //查询所有社团获取的社团列表
  const data: any = await reqSearchCommunity(null)
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
//控制编辑框
let dialogVisible = ref(false)
let addDialogVisible = ref(false)
//消息函数
const myMessage = (msg: any, type: any) => {
  ElMessage({
    message: msg,
    type: type,
  })
}
//新增用户
function addUserFn() {
  // 职位默认是社团成员
  userForm.roleId = '3'
  addDialogVisible.value = true
}
//用户职位
function formatRole(row: any) {
  switch (row.roleId) {
    case 1:
      return '社团管理员'
    case 2:
      return '社长'
    default:
      return '社团成员'
  }
}
//用户状态
function formatStatus(row: any) {
  switch (row) {
    case 1:
      return { type: 'success', status: '正常' }
    case 2:
      return { type: 'danger', status: '禁用' }
    default:
      return { type: 'primary', status: '未知' }
  }
}
//查询对象
const serchUserForm:any = reactive({
  name: '',
  sex: '',
  pageSize: 5,
  page: 1,
})
let total = ref()

//表格数据
const tableData = ref([])

// 发送请求获取所有用户数据
async function getAllUser() {
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
  curUser.value = user.data
  if(curUser.value.roleId!=1){
    serchUserForm.communityId = curUser.value.communityId
  }else{
    serchUserForm.communityId = ''
  }
  
  const { data } = await reqAllUser(serchUserForm)
  tableData.value = data.records
  tableData.value.forEach((item) => {
    //找出communityId对应的元素matchedItem，将matchedItem的值赋值给tableData的communityId
    const matchedItem = communityList.value.find(
      (listItem:any) => listItem.value == item.communityId,
    )
    if (matchedItem) {
      item.communityId = matchedItem.label
    }
  })
  //分页相关
  total = data.total
}
//调用渲染数据
getAllUser()

const handleEdit = async (index: number, row: User) => {
  const { data }: any = await reqUserById(row.id)
  Object.assign(userForm, data)
  //这是职位数据还是数字，我们要找出value值对应的label并显示
  const matchedPosition = Position.find(
    (position) => position.value === userForm.roleId.toString(),
  )
  if (matchedPosition) {
    userForm.roleId = matchedPosition.value
  } else {
    console.error(`无法匹配职位值：${userForm.roleId.toString()}`)
  }
  dialogVisible.value = true
}

const handleDelete = (index: number, row: User) => {
  ElMessageBox.confirm('您是否确认删除该用户?', '删除用户', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(async () => {
      //确认操作
      // 删除用户
      const data: any = await reqDeleteUser(row.id)
      if (data.code == 200) {
        myMessage('删除用户成功', 'success')
        //删除成功刷新列表
        getAllUser()
      } else {
        myMessage('删除用户失败', 'error')
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
  getAllUser()
}
const handleCurrentChange = () => {
  getAllUser()
}
const handleEnter = () => {
  getAllUser()
}

//编辑用户信息相关
let userForm = reactive({
  name: '',
  username: '',
  sex: '',
  communityId: '',
  phone: '',
  roleId: '', // 职位ID
  status: '', // 用户状态
})

//经过计算属性处理的status，因为后端传过来的1，2我们要true和false
const Status = computed({
  get: () => {
    return userForm.status == '1'
  },
  set: (newValue) => {
    userForm.status = newValue ? '1' : '2' // 如果为 true，则设置为 1，否则设置为 2
  },
})
const Position = [
  { value: '1', label: '社团管理员' },
  { value: '2', label: '社长' },
  { value: '3', label: '社团成员' },
]

//取消修改
function Cancel() {
  dialogVisible.value = false
}

function addCancel() {
  addDialogVisible.value = false
}
//确认修改
async function Confirm() {
  try {
    //倒反
    const matchedPosition = Position.find(
      (position) => position.value === userForm.roleId.toString(),
    )
    if (matchedPosition) {
      userForm.roleId = matchedPosition.value
    } else {
      console.error(`无法匹配职位值：${userForm.roleId.toString()}`)
    }

    const data = (await reqEditUser(userForm)) as any
    if (data.code === 200) {
      myMessage('修改个人信息成功', 'success')
      // 成功后刷新列表
      getAllUser()
    } else {
      myMessage(data.msg, 'error')
    }
  } catch (error) {
    myMessage('修改个人信息失败', error)
  }
  dialogVisible.value = false
}

//确认添加
async function addConfirm() {
  try {
    //倒反
    const matchedPosition = Position.find(
      (position) => position.value === userForm.roleId.toString(),
    )
    if (matchedPosition) {
      userForm.roleId = matchedPosition.value
    } else {
      console.error(`无法匹配职位值：${userForm.roleId.toString()}`)
    }
    const data = (await reqAddUser(userForm)) as any
    if (data.code === 200) {
      myMessage('添加用户成功', 'success')
      // 成功后刷新列表
      getAllUser()
    } else {
      myMessage(data.msg, 'error')
    }
  } catch (error) {
    myMessage(error, 'error')
  }
  addDialogVisible.value = false
}

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

// 清空对话框
function clearDialog() {
  //将数据清空
  Object.keys(userForm).forEach((key) => {
    userForm[key] = '' // 清空表单数据
  })
}
</script>
<style scoped lang="less">
.main-card {
  height: 820px;
}
</style>
