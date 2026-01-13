package com.sky.controller.admin;

import com.sky.constant.FileUpLoadConstant;
import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传:{}",file);
        try {
            String originalfilename = file.getOriginalFilename();
            String extension = originalfilename.substring(originalfilename.lastIndexOf("."));
            if (!extension.equals(".png")&&!extension.equals(".jpg")&&!extension.equals("/jpeg")){
                log.info("文件不合法，文件名为{}",originalfilename);
                return Result.success("文件不合法重新上传");
            }
            String newFileName = UUID.randomUUID().toString() + extension;
            String path = FileUpLoadConstant.FILE_UPLOAD_PATH + newFileName;
            file.transferTo(new File(path));
            return Result.success(FileUpLoadConstant.FILE_VIVST_PATH + newFileName);
        }catch (IOException e){
            log.info("文件上传失败:{}",e);
            throw new RuntimeException(e);
        }
    }
}
