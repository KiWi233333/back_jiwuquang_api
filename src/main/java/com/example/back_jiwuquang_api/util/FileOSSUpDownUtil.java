package com.example.back_jiwuquang_api.util;

import com.example.back_jiwuquang_api.domain.config.FileOSSConfig;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传工具类OSS对象存储
 *
 * @className: FileUploadUtil
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/8 1:34
 */
@Slf4j
@Component
public class FileOSSUpDownUtil {
    @Autowired
    private FileOSSConfig fileOSSConfig;

    public final static String TYPE_IMAGE = "image/";
    public final static String TYPE_VIDEO = "video/";
    public final static String TYPE_FILE = "file/";

    /**
     * 上传图片
     *
     * @param file 返回路径
     * @return
     */
    public String uploadImage(MultipartFile file) {
        return uploadFile(file, TYPE_IMAGE);
    }

    // 上传
    private String uploadFile(MultipartFile file, String Type) {
        log.info("Uploading {}", file.getOriginalFilename());
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(cfg);

        // 1、生成上传凭证，然后准备上传
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String accessKey = fileOSSConfig.accessKey;
        String secretKey = fileOSSConfig.secretKey;
        String bucketName = fileOSSConfig.bucketName;
        // 以什么为文件名
        String fileName = getRandomNameByFile(file);

        // 2、身份信息
        Auth auth = Auth.create(accessKey, secretKey);

        try {
            if (file.getOriginalFilename() == null) return null;
            // 3、上传文件
            String upToken = auth.uploadToken(bucketName);
            Response res = uploadManager.put(file.getBytes(), Type + fileName, upToken);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
            // 4、打印返回的信息
            if (res.isOK() && res.isJson()) {
                log.warn("Upload OK {}", putRet.toString());
                res.close();
                return fileName;
            } else {
                log.error("七牛异常 fail:" + res.bodyString());
                res.close();
                return fileName;
            }
        } catch (IOException e) {
            log.warn("Upload fail {}", e.getMessage());
        }
        return null;
    }


    /**
     * 更新覆盖照片
     *
     * @param file
     * @param oldKey
     * @return
     */
    public String updateImage(MultipartFile file, String oldKey) {
        return uploadUpdateFile(file, TYPE_IMAGE, oldKey);
    }


    /**
     * 上传图片（批量）
     *
     * @param fileList
     * @return
     */
    public List<String> uploadBatchImage(MultipartFile[] fileList) {
        List<String> imgList = new ArrayList<>();
        for (int i = 0; i < fileList.length; i++) {
            String url = uploadUpdateFile(fileList[i], TYPE_IMAGE, null);
            if (url != null) {
                imgList.add(url);
            }
        }
        return imgList;
    }


    /**
     * 上传视频
     *
     * @param video 返回路径
     * @return
     */
    public String uploadVideo(MultipartFile video, String oldKey) {
        return uploadUpdateFile(video, TYPE_VIDEO, oldKey);
    }


    // 覆盖
    private String uploadUpdateFile(MultipartFile file, String Type, String oldKey) {
        log.info("Uploading {}", file.getOriginalFilename());
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(cfg);

        // 1、生成上传凭证，然后准备上传
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String accessKey = fileOSSConfig.accessKey;
        String secretKey = fileOSSConfig.secretKey;
        String bucketName = fileOSSConfig.bucketName;
        // 以什么为文件名
        String fileName = getRandomNameByFile(file);

        // 2、身份信息
        Auth auth = Auth.create(accessKey, secretKey);

          try {
            if (file.getOriginalFilename() == null) return null;
            // 3、上传文件
            String upToken = auth.uploadToken(bucketName);
            Response res = uploadManager.put(file.getBytes(), Type + fileName, upToken);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
            // 4、打印返回的信息
            if (res.isOK() && res.isJson()) {
                // 5、删除旧照片
                BucketManager bucketManager = new BucketManager(auth, cfg);
                bucketManager.delete(bucketName, Type + oldKey);
                // 返回这张存储照片的地址
                log.warn("Upload OK {}", putRet.toString());
                res.close();
                return fileName;
            } else {
                log.error("七牛异常 fail:" + res.bodyString());
                res.close();
                return fileName;
            }
        } catch (IOException e) {
            log.warn("Upload fail {}", e.getMessage());
        }
        return null;
    }

    // 获取随机文件名
    private String getRandomNameByFile(MultipartFile file) {
        String oldFileName = file.getOriginalFilename();
        String ext = "." + FilenameUtils.getExtension(oldFileName);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid + ext;
    }
}
