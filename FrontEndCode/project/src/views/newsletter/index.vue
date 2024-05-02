<template>
  <div>
    <el-card style="max-width: 100%" class="main-card">
      <template #header>
        <div class="card-header">
          <el-row>
            <el-col :span="16">
              <el-row>
                <el-col :span="4">
                  <span style="font-size: 18px">社团快讯</span>
                </el-col>
                <el-col :span="20">
                  <el-button type="primary" @click="addNewFn">
                    发布快讯
                  </el-button>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="8">
              <el-row>
                <el-col :span="12">
                  <el-input v-model="pageForm.name" placeholder="输入快讯标题关键词" :suffix-icon="Search"
                    @keyup.enter="handleEnter" />
                </el-col>
                <el-col :span="2"></el-col>
                <el-col :span="10">
                  <el-input v-model="pageForm.content" placeholder="输入快讯内容描述" :suffix-icon="Search"
                    @keyup.enter="handleEnter" />
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </div>
      </template>

      <el-table :data="tableData" style="width: 100%; height: 600px">
        <el-table-column type="index" label="#" />
        <el-table-column prop="name" label="快讯标题" width="180" />
        <el-table-column prop="content" label="快讯内容" align="center"  width="500"/>
        <el-table-column label="图片" width="auto" height="20px">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <img @click="showImg(scope.row.img)" :src="scope.row.img" style="
                  max-width: 100px;
                  max-height: 100px;
                  width: auto;
                  height: auto;
              "/>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="right">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)">
              编辑
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <el-pagination v-model:current-page="pageForm.page" v-model:page-size="pageForm.pageSize"
          :page-sizes="[5, 10, 15, 20]" :small="true" layout="total, sizes, prev, pager, next, jumper" :total="total"
          @size-change="handleSizeChange" @current-change="handleCurrentChange" />
      </template>

      <!-- 操作框 -->
      <el-dialog v-model="dialog" :title="dialogTitle" width="500" draggable @closed="clearDialog">
        <el-form :model="newsletterForm" label-width="auto" style="max-width: 600px">
          <el-form-item label="标题：">
            <el-input v-model="newsletterForm.name" />
          </el-form-item>
          <el-form-item label="内容：">
            <el-input v-model="newsletterForm.content" type="textarea" />
          </el-form-item>
          <el-form-item label="图片：">
            <el-upload class="avatar-uploader" action="/cms/upload" method="post"
              :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon">
                <Plus />
              </el-icon>
            </el-upload>
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialog = false">取消</el-button>
            <el-button type="primary" @click="confirmFn">
              确定
            </el-button>
          </div>
        </template>
      </el-dialog>

      <!-- 预览图片弹窗 -->
      <el-dialog v-model="imgDialog" title="图片预览" width="800" draggable @closed="curImg=''">
        <img :src="curImg" 
        style="max-width: 800px; max-height: 400px;width: auto;height: auto;"/>
      </el-dialog>
    </el-card>

  </div>
</template>

<script setup lang="ts">
import { ElMessage, ElMessageBox } from 'element-plus'
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { reqAddNew, reqDeleteNew, reqPageNew, reqUpdateNew } from '@/api/newsletter'
//上传文件
import type { UploadProps } from 'element-plus'

const imageUrl = ref('')
const curImg = ref('')
const imgDialog = ref(false)

const handleAvatarSuccess: UploadProps['onSuccess'] = (
  response,
  uploadFile
) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw!)
  //将请求返回的图片保存到newsletterForm中
  if (response.code == 200) {
    newsletterForm.value.img = response.data
  }
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('图片必须是jpg格式!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('图片大小不能超过2MB!')
    return false
  }
  return true
}
const dialog = ref(false)
const dialogTitle = ref()
const pageForm = ref({
  page: 1,
  pageSize: 5,
  name: '',
  content: ''
})
const total = ref()

const tableData = ref([])

const newsletterForm: any = ref({})

async function getAllNew() {
  const data: any = await reqPageNew(pageForm.value);
  total.value = data.total
  tableData.value = data.records

}
getAllNew()
const myMessage = (msg: any, type: any) => {
  ElMessage({
    message: msg,
    type: type,
  })
}

const handleEdit = (index: any, row: any) => {
  dialogTitle.value = "更新快讯"
  Object.assign(newsletterForm.value, row)
  imageUrl.value = row.img
  dialog.value = true
}

const addNewFn = () => {
  dialogTitle.value = "发布快讯"
  dialog.value = true
}

//对话框关闭时的回调
const clearDialog = () => {
  newsletterForm.value = {}
}


const handleSizeChange = () => {
  getAllNew()
}

const handleCurrentChange = () => {
  getAllNew()
}

const handleEnter = () => {
  getAllNew()
}

const handleDelete = (index: any, row: any) => {
  ElMessageBox.confirm('您是否确认删除该快讯?', '删除快讯', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(async () => {
      // 删除活动
      const data: any = await reqDeleteNew(row.id)
      if (data.code == 200) {
        myMessage('删除成功', 'success')
        //删除成功刷新活动列表
        getAllNew()
      } else {
        myMessage('删除失败', 'error')
      }
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })
}

async function confirmFn() {
  //如果id不存在那么就是添加操作反之是更新
  if (newsletterForm.value.id) {
    //更新
    const data: any = await reqUpdateNew(newsletterForm.value)
    if (data.code == 200) {
      myMessage("更新快讯成功", "success")
    } else {
      myMessage(data.msg, "error")
    }
  } else {
    //添加
    const data: any = await reqAddNew(newsletterForm.value)
    if (data.code == 200) {
      myMessage("添加快讯成功", "success")
    } else {
      myMessage(data.msg, "error")
    }
  }
  dialog.value = false
  getAllNew()
}
function showImg(img:any){
  curImg.value = img
  imgDialog.value = true

}
</script>

<style scoped lang="less">
.main-card {
  height: 820px;
}

.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
