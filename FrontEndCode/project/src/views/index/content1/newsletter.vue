<template>
  <div class="container">
    <ul
      v-infinite-scroll="load"
      class="infinite-list"
      style="overflow: auto"
      :infinite-scroll-disabled="newsList.length == total"
    >
      <el-empty description="暂无快讯" v-if="total == 0" :image-size="300" />
      <li
        v-for="item in newsList"
        :key="item"
        class="infinite-list-item"
        @click="detailFn(item)"
      >
        <h1>{{ item.name + ' | ' + item.content }}</h1>
        <div class="item-footer">
          <span v-if="item.publishTime">发布时间：{{ item.publishTime }}</span>
        </div>
      </li>
    </ul>

    <!-- 详细框 -->
    <el-dialog
      v-model="detailDialogVisible"
      :title="newsDetail.name"
      width="800"
      draggable
      @closed="newsDetail = ''"
    >
      <el-input
        disabled
        :value="newsDetail.content"
        style="width: 600px"
        :autosize="{ minRows: 2, maxRows: 4 }"
        type="textarea"
      />
    </el-dialog>
  </div>
</template>

<script lang="ts" setup name="activity">
import { reqPageNew } from '@/api/newsletter'
import { ref } from 'vue'
//懒加载可以模拟分页
const searchNews = ref({
  page: 1,
  pageSize: 5,
})
const total = ref(0)
const newsDetail:any = ref({})
//详细框
let detailDialogVisible = ref(false)
//获取快讯列表
const newsList: any = ref([])
async function getNewsList() {
  const data: any = await reqPageNew(searchNews.value)
  
  newsList.value = data.records
  total.value = data.total
}
getNewsList()





const load = async () => {
  // 在函数声明的前面加上 async 关键字
  // 增加 try-catch 语句来处理可能出现的异常情况
    try {
      searchNews.value.page += 1
      const curlist = await reqPageNew(searchNews.value)
      // 将新获取的数据追加到 newsList.value 中
      newsList.value = newsList.value.concat(curlist.data.records)
    } catch (error) {
      // 捕获可能出现的异常并进行处理
      console.error('加载活动列表时发生错误:', error)
    }
}


function detailFn(item: any) {
  newsDetail.value = item
  detailDialogVisible.value = true
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
