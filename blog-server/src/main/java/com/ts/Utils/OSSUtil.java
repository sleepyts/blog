//package com.ts.Utils;
//
//
//import cn.hutool.core.io.file.FileNameUtil;
//import com.aliyun.oss.ClientException;
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import com.aliyun.oss.OSSException;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.UUID;
//
///*
// * 阿里云OSS工具类
// */
//@Component
//@ConfigurationProperties(prefix = "oss.config")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class OSSUtil {
//    private String endpoint;
//    private String accessKeyId;
//    private String accessKeySecret;
//    private String bucketName;
//
//    /*
//     * 上传文件到OSS
//     * @param file 上传的文件
//     * @return 上传成功后的文件URL
//     */
//    public String uploadFile(MultipartFile file) throws IOException {
//        String originalFilename = file.getOriginalFilename();
//        String ext="."+ FileNameUtil.getSuffix(originalFilename);
//        String uuid= UUID.randomUUID().toString().replaceAll("-", "");
//        String fileName=uuid+ext;
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        ossClient.putObject(
//                bucketName,
//                fileName,
//                file.getInputStream()
//        );
//        ossClient.shutdown();
//        return "https://" + bucketName + "." + endpoint + "/" + fileName;
//    }
//
//    /*
//     * 删除OSS上文件
//     * @param fileName 文件名
//     * @return 无
//     */
//    public void deleteFile(String fileName) {
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//        try {
//            // 删除文件或目录。如果要删除目录，目录必须为空。
//            ossClient.deleteObject(bucketName, fileName);
//        } catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//    }
//}
