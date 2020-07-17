package com.ringcentral.assessment.controller;

import com.ringcentral.assessment.entity.FileRecord;
import com.ringcentral.assessment.exception.BadRequestException;
import com.ringcentral.assessment.file.IFileHandler;
import com.ringcentral.assessment.service.IFileRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletResponse;
import static com.ringcentral.assessment.constant.ErrorCode.LACK_OF_FILE_ID;

/**
 * @Author cxh
 * @Date 2020/7/12
 */

@Controller("/api/file")
public class FileAccessController {

    @Autowired
    private IFileRecordService iFileRecordService;

    @Autowired
    private IFileHandler iFileHandler;

    /**
     * 通过文件的id进行下载
     * @param fileId
     * @param servletResponse
     */
    @RequestMapping("/download")
    public void download(String fileId, HttpServletResponse servletResponse) {

        if (StringUtils.isEmpty(fileId)) {
            throw new BadRequestException(LACK_OF_FILE_ID);
        }

        FileRecord fileRecord = iFileRecordService.getById(fileId);
        iFileHandler.handleDownload(servletResponse, fileId, fileRecord.getFileName());
    }
}
