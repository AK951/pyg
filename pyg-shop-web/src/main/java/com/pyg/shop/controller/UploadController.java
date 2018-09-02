package com.pyg.shop.controller;

import com.pyg.util.FastDFSClient;
import com.pyg.vo.InfoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/13 10:53
 * @since 1.0.0
 */
@RestController
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    @RequestMapping("/upload")
    public InfoResult upload(MultipartFile file) throws Exception {
        // 获取文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件类型
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
            String path = fastDFSClient.uploadFile(file.getBytes(), extName);
            String url = FILE_SERVER_URL + path;
            return new InfoResult(true, url);
        } catch (Exception e) {
            e.printStackTrace();
            return new InfoResult(false, "上传失败");
        }
    }

}