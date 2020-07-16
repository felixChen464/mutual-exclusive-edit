package com.ringcentral.assessment.file;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

public interface IFileHandler {

    /**
     * 写文件
     *
     * @param fileId
     * @param content
     * @return
     */
    File writeStringFile(String fileId, String content);

    /**
     * 获取文件内容
     *
     * @param fileId
     * @return
     */
    String getFileContent(String fileId);

    /**
     * 下载文件
     *
     * @param response
     * @param code
     */
    void handleDownload(HttpServletResponse response, String code, String fileName);

}
