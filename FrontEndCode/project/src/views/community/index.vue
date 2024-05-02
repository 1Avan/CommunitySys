<template>
  <div>
    <el-card style="max-width: 100%" class="main-card">
      <template #header>
        <div class="card-header">
          <el-row>
            <el-col :span="16">
              <el-row>
                <el-col :span="4">
                  <span style="font-size: 18px">社团管理</span>
                </el-col>
                <el-col :span="20">
                  <el-button type="primary" @click="addDialog = true">
                    新增社团
                  </el-button>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="8">
              <el-row>
                <el-col :span="12">
                  <el-input
                    v-model="searchCommunityForm.communityName"
                    placeholder="输入社团名关键词"
                    :suffix-icon="Search"
                    @keyup.enter="handleEnter"
                  />
                </el-col>
                <el-col :span="2"></el-col>
                <el-col :span="10">
                  <el-input
                    v-model="searchCommunityForm.description"
                    placeholder="输入社团描述"
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
        <el-table-column prop="communityName" label="社团名" width="180" />
        <el-table-column prop="description" label="社团简介" align="center" />

        <el-table-column label="操作" align="right">
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
          v-model:current-page="searchCommunityForm.page"
          v-model:page-size="searchCommunityForm.pageSize"
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
        v-model="addDialog"
        title="添加社团"
        width="500"
        @closed="clearDialog"
      >
        <div>
          <el-form
            :model="communityForm"
            label-width="auto"
            style="max-width: 600px"
          >
            <el-form-item label="社团名">
              <el-input v-model="communityForm.communityName" />
            </el-form-item>
            <el-form-item label="社团简介">
              <el-input v-model="communityForm.description" />
            </el-form-item>
          </el-form>
        </div>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="addDialog = false">取消</el-button>
            <el-button type="primary" @click="addCommunity">确定</el-button>
          </div>
        </template>
      </el-dialog>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ElMessage, ElMessageBox } from 'element-plus'
import { ref, reactive } from 'vue'
import { Search } from '@element-plus/icons-vue'
import {
  reqAllCommunity,
  reqDeleteCommunity,
  reqAddCommunity,
  reqUpdateCommunity,
} from '@/api/community'

const addDialog = ref(false)
const total = ref('')
const tableData = ref([])
const searchCommunityForm = reactive({
  communityName: '',
  page: 1,
  pageSize: 5,
  description: '',
})

const communityForm = reactive({
  id: '',
  communityName: '',
  description: '',
})

function clearDialog() {
  Object.keys(communityForm).forEach((key) => {
    communityForm[key] = '' // 清空表单数据
  })
}

async function getAllCommunity() {
  try {
    const { data } = await reqAllCommunity(searchCommunityForm)
    tableData.value = data.records
    total.value = data.total
  } catch (error) {
    console.error('Error fetching community data:', error)
  }
}

getAllCommunity()

const myMessage = (msg, type) => {
  ElMessage({
    message: msg,
    type: type,
  })
}

const handleEdit = (index, row) => {
  Object.assign(communityForm, row)
  addDialog.value = true
  // 获取当前id的社团信息
}

const handleDelete = (index, row) => {
  ElMessageBox.confirm('您是否确认删除该社团?', '删除社团', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(async () => {
      const data = await reqDeleteCommunity(row.id)
      if (data.code === 200) {
        myMessage('删除社团成功', 'success')
        getAllCommunity()
      } else {
        myMessage('删除社团失败', 'error')
      }
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })
}
async function addCommunity() {
  // 如果communityForm中id不为null那么就是更新操作
  if (communityForm.id) {
    const data: any = await reqUpdateCommunity(communityForm)
    if (data.code === 200) {
      myMessage(data.msg, 'success')
      getAllCommunity()
    } else {
      myMessage(data.msg, 'error')
    }
  } else {
    const data: any = await reqAddCommunity(communityForm)
    if (data.code === 200) {
      myMessage(data.msg, 'success')
      getAllCommunity()
    } else {
      myMessage(data.msg, 'error')
    }
  }

  addDialog.value = false
}

const handleSizeChange = () => {
  getAllCommunity()
}

const handleCurrentChange = () => {
  getAllCommunity()
}

const handleEnter = () => {
  getAllCommunity()
}
</script>

<style scoped lang="less">
.main-card {
  height: 820px;
}
</style>
