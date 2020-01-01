package org.kettle.scheduler.system.api.api;

import io.swagger.annotations.Api;
import oracle.jdbc.proxy.annotation.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 18:00
 */
@Api(tags = "文件上传")
@RequestMapping("/sys/upload")
public interface UploadApi {

    @PostMapping("/uploadExcel")
    @ResponseBody
    String uploadExcel(MultipartFile file, @RequestParam String fileType) throws IOException, ClassNotFoundException;

    @PostMapping("/test")
    String test();
}
