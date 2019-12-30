package org.kettle.scheduler.system.biz.controller;

import com.alibaba.excel.EasyExcel;
import org.kettle.scheduler.system.api.api.UploadApi;
import org.kettle.scheduler.system.biz.service.SysSystemService;
import org.kettle.scheduler.system.biz.util.ExcelListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 18:02
 */
@RestController
public class UploadApiController implements UploadApi {

    @Autowired
    SysSystemService sysSystemService;

    @Override
    public String uploadExcel(MultipartFile file, String fileType) throws IOException, ClassNotFoundException {
        if(fileType.equals("System")){
            fileType="org.kettle.scheduler.system.api.entity.System";
        }
        EasyExcel.read(file.getInputStream(), Class.forName(fileType), new ExcelListener(sysSystemService)).sheet().doRead();
        return "success";
    }
}
