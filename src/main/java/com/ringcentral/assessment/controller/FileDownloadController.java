package com.ringcentral.assessment.controller;

import com.mysql.jdbc.StringUtils;
import com.ringcentral.assessment.file.IFileHandler;
import com.ringcentral.assessment.service.IFileRecordService;
import com.ringcentral.assessment.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.ServletResponse;
import static com.ringcentral.assessment.constant.ErrorCode.LACK_OF_FILE_CODE;

/**
 * @Author cxh
 * @Date 2020/7/12
 */

@Controller("/api/file/download")
public class FileDownloadController {

    @Autowired
    private IFileRecordService iFileRecordService;

    @Autowired
    private IFileHandler iFileHandler;

    @RequestMapping("/code")
    public Result download(String fileCode, ServletResponse servletResponse) {

        if (StringUtils.isEmptyOrWhitespaceOnly(fileCode)) {
            return Result.validationError(LACK_OF_FILE_CODE);
        }

        iFileHandler.handleDownload(servletResponse,fileCode);
        return null;
    }
}
