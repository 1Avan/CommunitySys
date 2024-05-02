package com.ivan.communitymanagementsys.service.impl;

import com.ivan.communitymanagementsys.dto.ActivityApplicationDTO;
import com.ivan.communitymanagementsys.entity.Activity;
import com.ivan.communitymanagementsys.entity.ActivityApplication;
import com.ivan.communitymanagementsys.entity.User;
import com.ivan.communitymanagementsys.mapper.ActivityApplicationMapper;
import com.ivan.communitymanagementsys.mapper.ActivityMapper;
import com.ivan.communitymanagementsys.mapper.UserMapper;
import com.ivan.communitymanagementsys.service.ActivityApplicationService;
import com.ivan.communitymanagementsys.vo.UserToActivity;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import jakarta.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityApplicationServiceImpl implements ActivityApplicationService {
    @Autowired
    private ActivityApplicationMapper activityApplicationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public void sentActivityApplication(ActivityApplication activityApplication) {
        //申请时间就是当前时间
        activityApplication.setApplicationTime(LocalDateTime.now());
        activityApplicationMapper.insert(activityApplication);
    }

    @Override
    public void exportActivityXSLX(HttpServletResponse response, Long activityId) {
        //1. 查询数据库，获取参与活动的成员列表
//        获取用户id列表
        List<Long> useridList = activityApplicationMapper.getByActivityId(activityId);
        List<UserToActivity> userList = new ArrayList<>();
        //根据用户id获取用户
        for(Long userId : useridList){
            User user = userMapper.getUserById(userId);
            UserToActivity userToActivity = new UserToActivity();
            BeanUtils.copyProperties(user,userToActivity);
            userList.add(userToActivity);
        }
        //根据活动id获取活动名
        Activity activity = activityMapper.findById(activityId);
        //2. 通过POI将数据写入到Excel文件中
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("templates/参加活动成员模板.xlsx");

        try {
            //基于模板文件创建一个新的Excel文件
            XSSFWorkbook excel = new XSSFWorkbook(in);
            //获取表格文件的Sheet页
            XSSFSheet sheet = excel.getSheet("Sheet1");
            //填充数据--活动名
            sheet.getRow(1).getCell(1).setCellValue("活动名：" +activity.getName());
            //填充数据--活动地点
            sheet.getRow(2).getCell(1).setCellValue("活动地点：" +activity.getActivityPlace());
            XSSFRow row;
            int curRow = 4;
            //填充明细数据
            for (UserToActivity item : userList) {
                //获得某一行
                row = sheet.getRow(curRow);
//                System.out.println(row+":"+curRow);
                row.getCell(1).setCellValue(item.getUsername());
                row.getCell(2).setCellValue(item.getName());
                row.getCell(3).setCellValue(item.getSex());
                row.getCell(4).setCellValue(item.getPhone());
                curRow++;
            }

            //3. 通过输出流将Excel文件下载到客户端浏览器
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);

            //关闭资源
            out.close();
            excel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<ActivityApplication> getUserList(Long userId) {
        List<ActivityApplication> activityList = activityApplicationMapper.findByUserId(userId);
        return activityList;

    }

    @Override
    public void deleteActivityForUser(ActivityApplicationDTO activityApplicationDTO) {
        activityApplicationMapper.delete(activityApplicationDTO);
    }

}
