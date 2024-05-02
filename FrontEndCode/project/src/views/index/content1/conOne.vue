<template>
  <div class="con-1 container clearfix">
    <!-- 社团快讯 -->
    <div class="stkx fl">
      <div class="stkx-top">
        <el-icon :size="20" color="#87CEEB">
          <Memo />
        </el-icon>
        <span class="title">社团快讯</span>
        <a class="more fr" href="#/index/newsletter">MORE+</a>
      </div>
      <!-- 查看快讯 -->
      <a href="javascript:;">
        <div class="stkx-content">
          <div class="stkx-left fl">
            <img :src="Fnews.img" alt="" />
            <h2>{{Fnews.name}}</h2>
            <div class="time fr">
              <el-icon>
                <AlarmClock />
              </el-icon>
              {{ Fnews.publishTime }}
            </div>
            <p>
              {{ Fnews.content }}
            </p>
          </div>
          <div class="stkx-right fr">
            <ul class="clearfix">
              <li v-for="news in newsList" :key="news.id" @click="checkNews(news)">
                <a
                  :title="news.name"
                >
                  {{news.content}}
                  <span class="fr gay">{{news.publishTime}}</span>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </a>
    </div>
    <!-- 通知公告 -->
    <div class="tzgg fr">
      <!-- 通知公告标题区域 -->
      <div class="tzgg-top">
        <el-icon :size="20" color="#87CEEB">
          <Promotion />
        </el-icon>
        <span class="title">通知公告</span>
        <!-- <a class="more fr" href="javascript:;">MORE+</a> -->
        <a class="more fr" href="#/index/activity" >MORE+</a>
      </div>
      <!-- 通知公告内容区域 -->
      <!-- 从后端随机获取四个活动展示 -->
      <div class="tzgg-content">
        <ul class="clearfix">
          <li v-for="activity in activityList" @click="checkActivity(activity)">
            <!-- target="_blank"，a空白页打开 -->
            <a
              href="javascript:;"
              :title="activity.name"
            >
              <div class="st-time fl">
                <span class="day">{{ activity.startTime?.slice(8,10) }}</span>
                <span class="time">{{ activity.startTime?.slice(0,7) }}</span>
              </div>
              <div class="st-wz fr">
                {{ activity.description }}
              </div>
            </a>
          </li>
        </ul>
      </div>

      <!-- 展示活动的对话框 -->
      <el-dialog v-model="activityDialogVisible" title="活动详细" width="500" draggable>
        <div class="content">
          <div class="activity_detail">
            <h2>活动名称: {{ curActivity.name }}</h2>
            <p>活动地点: {{ curActivity.activityPlace }}</p>
            <p style="overflow: hidden;text-overflow: ellipsis;">活动描述: {{ curActivity.description }}</p>
            <p v-show="curActivity.startTime">开始时间: {{ curActivity.startTime }}</p>
            <p v-show="curActivity.endTime">结束时间: {{ curActivity.endTime }}</p>
          </div>
        </div>
  </el-dialog>
  <!-- 展示快讯的对话框 -->
  <el-dialog v-model="newsDialogVisible" title="快讯详细" width="500" draggable>
        <div class="content">
          <div class="activity_detail">
            <h2>标题: {{ curNews.name }}</h2>
            <p style="overflow: hidden;text-overflow: ellipsis;">内容: {{ curNews.content }}</p>
            <p >通知时间: {{ curNews.publishTime }}</p>
            <img :src="curNews.img" alt="" v-show="curNews.img" style="max-width: 400px;max-height: 400px;width: auto;height: auto;">
          </div>
        </div>
  </el-dialog>
    </div>
  </div>
</template>
<script setup lang="ts" name="ConOne">
import { reqRandomActivity } from "@/api/activity"
import { reqRandomNews } from "@/api/newsletter"
import { ref } from "vue"
const activityDialogVisible = ref(false)
const newsDialogVisible = ref(false)
//随机获取4个活动展示
const activityList:any = ref([])
async function getRandomActivity (){
  const data = await reqRandomActivity()
  activityList.value = data.data
}
getRandomActivity()

//展示当前点击活动详细
const curActivity:any = ref({})
function checkActivity(activity:any){
  activityDialogVisible.value = true
  console.log(activity);
  curActivity.value = activity
}

//随机获取4个快讯
//取出头一个展示在左边
const Fnews:any = ref({})
const newsList:any = ref([])
async function getRandomnews (){
  const data = await reqRandomNews()
  newsList.value = data.data
  Fnews.value = newsList.value[0]
  
}
getRandomnews()

//展示当前点击快讯详细
const curNews:any = ref({})
function checkNews(news:any){
  newsDialogVisible.value = true
  curNews.value = news
}

</script>
<style scoped lang="less">
.con-1 {
  margin: 50px auto;

  .stkx {
    width: 810px;

    .stkx-top {
      height: 48px;
      line-height: 25px;
      border-bottom: 1px solid #ccc;

      .title {
        font-size: 20px;
        color: skyblue;
      }

      .more {
        font-size: 16px;
        color: #ccc;
      }
    }

    .stkx-content {
      margin-top: 15px;

      .stkx-left:hover {
        transform: scale(1.03);
      }

      .stkx-left {
        padding: 0px 10px;
        padding-bottom: 24px;
        width: 380px;
        box-shadow:
          0px 0px 0px 0px #fff,
          0px 0px 0px 0px #fff,
          0px 0px 0px 0px #fff,
          0px 3px 2px #e7e7e7;
        transition: transfrom 0.3s ease;

        /* 添加过渡效果 */
        img {
          width: 100%;
          height: 252px;
          overflow: hidden;
        }

        h2 {
          height: 50px;
          border-top: none;
          color: #555555;
          font-size: 16px;
          line-height: 50px;
          font-weight: normal;
        }

        .time {
          font-size: 13px;
          color: #b2b1b1;
          margin-right: 20px;
        }

        p {
          margin-top: 20px;
          line-height: 26px;
          font-size: 14px;
          color: #838383;
        }
      }

      .stkx-right {
        width: 405px;
        border: 1px solid #ccc;

        ul {
          border: 1px solid #e3e3e3;
          border-top: 10px solid #ececec;
          padding: 0px 20px;
        }

        li {
          border-bottom: 1px solid #f0f0f0;
          height: 106px;
          padding: 16px 0px;

          > a {
            font-size: 16px;
            line-height: 35px;
          }
          > a:hover {
            color: skyblue;
          }

          .gay {
            color: #c1c5c8;
          }
        }
      }
    }
  }

  .tzgg {
    width: 358px;

    .tzgg-top {
      height: 48px;
      line-height: 25px;
      border-bottom: 1px solid #ccc;

      .title {
        font-size: 20px;
        color: skyblue;
      }

      .more {
        font-size: 16px;
        color: #ccc;
      }
    }

    .tzgg-content {
      margin-top: 15px;

      ul {
        padding: 18px;
        border: 1px solid #e3e3e3;
        > li:hover .st-time {
          background: skyblue;
        }
        > li:hover .st-wz {
          color: skyblue;

        }

        li {
          clear: both;
          padding: 15px 0px;
          border-bottom: 1px solid #e3e3e3;
          height: 102px;
          overflow: hidden; /* 溢出隐藏 */
          text-overflow: ellipsis; /* 文本溢出显示省略号 */
          .st-time {
            width: 72px;
            background-color: #c1c1c1;
            text-align: center;
            color: #fff;
            .day {
              display: block;
              height: 40px;
              line-height: 40px;
              font-size: 25px;
            }
            .time {
              display: block;
              height: 22px;
              line-height: 22px;
              font-size: 14px;
            }
          }
          .st-wz {
            width: 228px;
          }
        }
      }
    }
  }
}


.content {
    max-width: 800px;
    margin: 20px auto;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    padding: 20px;
  }

  .activity_detail {
    border-bottom: 1px solid #ccc;
    padding: 20px 0;
  }

  .activity_detail h2 {
    margin: 0;
    font-size: 24px;
    color: #333;
  }

  .activity_detail p {
    margin: 10px 0;
    font-size: 16px;
    color: #666;
  }
</style>
