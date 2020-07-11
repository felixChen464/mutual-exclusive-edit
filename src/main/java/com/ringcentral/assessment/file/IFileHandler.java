package com.ringcentral.assessment.file;

import com.sun.deploy.net.HttpResponse;

import javax.servlet.ServletResponse;
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
     * 本低
     *
     * @param response
     * @param code
     */
    void handleDownload(ServletResponse response, String code);

}
