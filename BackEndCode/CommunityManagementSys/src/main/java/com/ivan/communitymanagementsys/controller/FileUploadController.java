package com.ivan.communitymanagementsys.controller;
import com.ivan.communitymanagementsys.result.Result;
import com.ivan.communitymanagementsys.utils.AliOssUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/cms/upload")
@Tag(name = "上传文件")
public class FileUploadController {
    @PostMapping
    public Result<String> upload(MultipartFile file) throws Exception {
        //把文件的内容存储到本地磁盘上
        String originalFilename = file.getOriginalFilename();
        //保证文件的名字是唯一的，从而防止文件覆盖
        String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        //文件上传到本地
//        file.transferTo(new File("C:\\Users\\ivan's小新\\Desktop\\files\\"+filename));
        //文件上传到云
        String url = AliOssUtil.uploadFile(originalFilename, file.getInputStream());
        return Result.success(url);
    }
}
