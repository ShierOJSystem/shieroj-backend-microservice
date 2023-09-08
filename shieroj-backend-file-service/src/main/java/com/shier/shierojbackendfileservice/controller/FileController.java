package com.shier.shierojbackendfileservice.controller;

import com.shier.shierojbackendcommon.common.BaseResponse;
import com.shier.shierojbackendcommon.common.ErrorCode;
import com.shier.shierojbackendcommon.common.ResultUtils;
import com.shier.shierojbackendcommon.exception.BusinessException;
import com.shier.shierojbackendfileservice.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Shier
 * 文件上传接口
 */
@Api(tags = "FileController")
@RestController
@RequestMapping("/")
public class FileController {

    @Resource
    private FileService fileService;

    /**
     * 上传头像
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public BaseResponse<String> uploadOssFile(@RequestPart("file") MultipartFile file) {
        //获取上传的文件
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "上传文件为空");
        }
        //返回上传到oss的路径
        String url = fileService.uploadFileAvatar(file);
        //返回r对象
        return ResultUtils.success(url);
    }
}
