package com.yuewangwang.controller;

import com.yuewangwang.utlis.AliOssUtil;
import com.yuewangwang.utlis.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.io.IOException;


@RestController
public class FileUploadController {

    //        originalFilename.lastIndexOf(".") 获取文件名，通过.分割后获取最后数据的索引
    //        originalFilename.substring（） 根据索引返回数据

    //        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
    //        System.out.println(substring);   .png

    @PostMapping("uoload")
    public Result<String> FileUpload(MultipartFile file) throws IOException {
        //获取文件名
        String originalFilename = file.getOriginalFilename();

        //利用UUID 更改文件名，避免因文件名一致导致文件覆盖
        String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));

        //把前端上传的文件存储到指定路径
        // file.transferTo(new File("C:\\Users\\yuewangwang\\Desktop\\pusst\\" + filename));

        String url = AliOssUtil.uploadFile(filename, file.getInputStream());
        return Result.success(url);

    }
}
