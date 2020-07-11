package com.ringcentral.assessment.file.impl;

import com.ringcentral.assessment.file.IFileHandler;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletResponse;
import java.io.File;
import java.io.IOException;

import static com.ringcentral.assessment.constant.CommonConstant.TXT;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @Author cxh
 * @Date 2020/7/11
 */

@Component
public class LocalFileHandler implements IFileHandler {

    private static Logger logger = LoggerFactory.getLogger(LocalFileHandler.class);

    @Value("${file.store.path}")
    private String filePath;

    /**
     *返回写入的文件对象，为null说明写入失败
     * @param fileId
     * @param content
     * @return
     */
    @Override
    public File writeStringFile(String fileId, String content) {
        File file = FileUtils.getFile(filePath + File.pathSeparator + fileId + TXT);

        //虽然当前业务几乎不可能重复，还是做一下防重复处理
        if (file.exists()){
            Boolean deletePreviousFileResult = file.delete();
            if (!deletePreviousFileResult){
                logger.error("delete former file fail,fileId:{}",fileId);
                return null;
            }
        }

        //创建文件并写入
        try {
            Boolean createFileResult = file.createNewFile();
            if (!createFileResult){
                logger.error("create new file fail,fileId:{}",fileId);
                return null;
            }
            FileUtils.writeStringToFile(file,content,UTF_8);
        }
        catch (Exception e){
            logger.error("exception while writing new file:{}",e);
            return null;
        }

        return file;
    }

    @Override
    public String getFileContent(String fileId) {

        File file = FileUtils.getFile(filePath + File.pathSeparator + fileId + TXT);

        //文件不存在或不可读
        if (file == null || !file.exists() || file.canRead() || !file.isFile() || file.isHidden()){
            logger.warn("user try to access unavailable file,fileId:{}",fileId);
            return null;
        }

        //读取文件
        String result = null;
        try {
            result = FileUtils.readFileToString(file,UTF_8);
        } catch (IOException e) {
            logger.error("read file fail,fileId:{}",fileId);
            return null;
        }
        return result;
    }

    @Override
    public void handleDownload(ServletResponse response, String code) {

    }
}
