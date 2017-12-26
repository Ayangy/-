package com.yuwubao.controllers;

import com.yuwubao.util.Const;
import com.yuwubao.util.RestApiResponse;
import com.yuwubao.util.ThinkTankUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${resourcesPath}")
    private String resourcesPath;

    @Value("${serverIp}")
    private String serverIp;

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
        String path = null;
        //String original = null;
        String sysName = System.getProperties().getProperty("os.name");
        String separator = System.getProperties().getProperty("file.separator");
        if (sysName.contains("Linux")) {
            /*if (type == 0) {
                path = separator + "tmp" + separator + "img" + separator + time + separator;
                //original = separator + "tmp" + separator + "original" + separator + time + separator;
            } else if (type == 1){
                path = separator + "tmp" + separator + "video" + separator + time + separator;
            }*/
            switch(type) {
                case 0:
                    path = separator + "tmp" + separator + "img" + separator + time + separator;
                    break;
                case 1:
                    path = separator + "tmp" + separator + "video" + separator + time + separator;
                    break;
                case 2:
                    path = separator + "tmp" + separator + "accessory" + separator + time + separator;
                    break;
                default:
                    path = separator + "tmp" + separator + "accessoryResult" + separator + time + separator;
                    break;
            }
        } else {
            /*if (type == 0) {
                path = resourcesPath + separator + "img" + separator + time + separator;
                //original = resourcesPath + separator + "original" + separator + time + separator;
            } else if (type == 1){
                path = resourcesPath + separator + "video" + separator + time + separator;
            }*/
            switch(type) {
                case 0:
                    path = resourcesPath + separator + "img" + separator + time + separator;
                    break;
                case 1:
                    path = resourcesPath + separator + "video" + separator + time + separator;
                    break;
                case 2:
                    path = resourcesPath + separator + "accessory" + separator + time + separator;
                    break;
                default:
                    path = resourcesPath + separator + "accessoryResult" + separator + time + separator;
                    break;
            }
        }
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        /*File originalFile = null;
        if (original != null) {
            originalFile = new File(original);
            if (!originalFile.exists()) {
                originalFile.mkdirs();
            }
        }*/
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf('.'));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid + suffix;
        String visit;
        /*if (type == 0) {
            visit = "img/" + time + "/" + filename;
        } else if (type == 1){
            visit = "video/" + time + "/" + filename;
        }*/
        switch (type) {
            case 0:
                visit = "img/" + time + "/" + filename;
                break;
            case 1:
                visit = "video/" + time + "/" + filename;
                break;
            case 2:
                visit = "accessory/" + time + "/" + filename;
                break;
            default:
                visit = "accessoryResult/" + time + "/" + filename;
                break;
        }

        try {
            /*if (originalFile != null) {
                file.transferTo(new File(originalFile + separator + filename));
                Thumbnails.of(originalFile + separator + filename).size(350, 194).keepAspectRatio(false).toFile(path + separator + filename);
            } else {
                file.transferTo(new File(path  + filename));
            }*/
            file.transferTo(new File(path  + filename));
            String address = ThinkTankUtil.getLocalHostLANAddress().getHostAddress();
            String ip= "http://" + serverIp + "/";
            result.successResponse(Const.SUCCESS, ip + visit);
        } catch (Exception e) {
            logger.warn("文件上传失败", e);
            result.failedApiResponse(Const.FAILED, "文件上传失败");
        }
        return result;
    }

    /**
     * 删除上传的文件
     * @param urlPath 文件路径
     */
    @DeleteMapping("/delete")
    public RestApiResponse<Boolean> deleteFile(String urlPath) {
       RestApiResponse<Boolean> result = new RestApiResponse<Boolean>();
        try {
            /*String partFilePath = urlPath.substring(ThinkTankUtil.getCharacterPosition(urlPath));
            String sysName = System.getProperties().getProperty("os.name");
            String separator = System.getProperties().getProperty("file.separator");
            String filePath = null;
            if (sysName.contains("Linux")) {
                filePath = separator + "tmp" + separator + partFilePath;
            } else {
                filePath = resourcesPath + partFilePath;
            }
            File deleteFile = new File(filePath);
            boolean state = deleteFile.delete();*/
            result.successResponse(Const.SUCCESS, true, "删除成功");
        } catch (Exception e) {
            logger.warn("删除上传的文件失败", e);
            result.failedApiResponse(Const.FAILED, "删除失败");
        }
        return result;
    }
}
