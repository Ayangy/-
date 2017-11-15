package com.yuwubao.controllers;

import com.yuwubao.util.Const;
import com.yuwubao.util.RestApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by yangyu on 2017/11/15.
 */
@RestController
@RequestMapping("/resource")
@CrossOrigin
public class FileUploadController {

    private final static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    /**
     * 将上传的图片保存到本地
     * @param file  上传的文件
     * @return
     */
    @PostMapping("/upload")
    public RestApiResponse<String> saveFile(@RequestParam int type,  MultipartFile file) {
        RestApiResponse<String> result = new RestApiResponse<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(new Date());
        String path;
        if (type == 0) {
            path = "E:\\img\\" + time + "\\";
        } else {
            path = "E:\\video\\" + time + "\\";
        }
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf('.'));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid + suffix;
        String visit;
        if (type == 0) {
            visit = "img/" + time + "/" + filename;
        } else {
             visit = "video/" + time + "/" + filename;
        }
        try {
            file.transferTo(new File(path + filename));
            result.successResponse(Const.SUCCESS, visit);
        } catch (Exception e) {
            logger.warn("文件上传失败", e);
            result.failedApiResponse(Const.FAILED, "文件上传失败");
        }
        return result;
    }

    /**
     * 删除上传的文件
     * @param fullFilePath 文件路径
     */
    @DeleteMapping("/delete")
    public void deleteFile(String fullFilePath) {
        try {
            String file= "E:/" + fullFilePath;
            File deleteFile = new File(file);
            deleteFile.delete();
        } catch (Exception e) {
            logger.warn("删除上传的文件失败", e);
        }
    }

}
