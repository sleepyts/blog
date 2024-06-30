//package com.ts.Controller;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * It has been abandoned since the function has been integrated into the front-end interface.
// */
//@RestController
//@RequestMapping("/upload")
//@CrossOrigin(origins = "*")
//@Slf4j
//public class UploaderController {
//
//    @Autowired
//    private OSSUtil ossUtil;
//
////    @Value("${file.path}")
////    private String IMAGE_PATH;
////
////    @PostMapping("/img")
////    public Result uploadImage(@RequestParam("file") MultipartFile image) {
////
////        try {
////            return Result.success(ossUtil.uploadFile(image));
////            // 上传到OSS
////        } catch (IOException e) {
////            throw new RuntimeException("图片上传失败", e);
////        }
////    }
////    @DeleteMapping("/img")
////    public Result deleteImage(@RequestParam("name") String filename) {
////
////        ossUtil.deleteFile(filename);
////        return Result.success();
////    }
////    @PostMapping("/blog/img")
////    @GetMapping("/blog/delete")
////    public Result deleteBlogImg(@RequestParam("name") String filename) {
////        File file = new File(IMAGE_PATH, filename);
////        if (file.isDirectory()) {
////            return Result.error("错误的文件名称");
////        }
////        FileUtil.del(file);
////        return Result.success();
////    }
//
////    private String createNewFileName(String originalFilename) {
////        // 获取后缀
////        String suffix = StrUtil.subAfter(originalFilename, ".", true);
////        // 生成目录
////        String name = UUID.randomUUID().toString();
////        // 生成文件名
////        return StrUtil.format("{}.{}", name, suffix);
////    }
////    public static void deleteFile(String filePath) {
////        // 创建 Path 对象
////        Path path = Paths.get(filePath);
////        try {
////            // 删除文件
////            Files.delete(path);
////            System.out.println("文件删除成功！");
////        } catch (IOException e) {
////            // 处理删除文件时可能出现的异常
////            System.err.println("删除文件时出现异常：" + e.getMessage());
////        }
////    }
//}
