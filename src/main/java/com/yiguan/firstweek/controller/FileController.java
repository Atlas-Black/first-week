package com.yiguan.firstweek.controller;

import com.yiguan.firstweek.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {
    public static final String UPLOAD_DIR = "/Users/atlasblack/Desktop/Study-personal/实习/campus-images";

    @PostMapping("/upload")
    //接收上传文件，前端上传时字段名叫file
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws Exception{
        if(file.isEmpty()){
            return Result.fail(500, "上传文件不能为空");
        }

        //取原始文件后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = "";
        if(originalFilename != null && originalFilename.contains(".")){
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        //用UUID生成新的文件名，防止重名覆盖
        String newFileName = UUID.randomUUID() + suffix;

        File dir = new File(UPLOAD_DIR);
        if(!dir.exists()){
            dir.mkdirs();
        }

        File dest = new File(dir, newFileName);
        //保存到本地目录，写到磁盘
        file.transferTo(dest);

        //返回图片访问地址
        String imageUrl = "http://localhost:8080/images/" + newFileName;
        return Result.success(imageUrl);
    }
}
