package com.ringcentral.assessment.file.impl;

import com.ringcentral.assessment.exception.BadRequestException;
import com.ringcentral.assessment.exception.ServerInternalException;
import com.ringcentral.assessment.file.IFileHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import static com.ringcentral.assessment.constant.CommonConstant.TXT;
import static com.ringcentral.assessment.constant.ErrorCode.FILE_NOT_FOUND;
import static com.ringcentral.assessment.constant.ErrorCode.READ_FILE_ERROR;
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

    @PostConstruct
    public void checkLocation() {
        File file = new File(filePath);
        if (!file.exists() || !file.isDirectory()) {
            Boolean result = file.mkdirs();
            if (BooleanUtils.isNotTrue(result)) {
                logger.error("init file store path error,please check file store path is reachable and not protected,store path:{}", filePath);
            }
        }
    }

    /**
     * 返回写入的文件对象，为null说明写入失败
     *
     * @param fileId
     * @param content
     * @return
     */
    @Override
    public File writeStringFile(String fileId, String content) {
        File file = FileUtils.getFile(filePath + File.separator + fileId + TXT);

        //虽然当前业务几乎不可能重复，还是做一下防重复处理
        if (file.exists()) {
            Boolean deletePreviousFileResult = file.delete();
            if (!deletePreviousFileResult) {
                logger.error("delete former file fail,fileId:{}", fileId);
                return null;
            }
        }

        //创建文件并写入
        try {
            Boolean createFileResult = file.createNewFile();
            if (!createFileResult) {
                logger.error("create new file fail,fileId:{}", fileId);
                return null;
            }
            FileUtils.writeStringToFile(file, content, UTF_8);
        } catch (Exception e) {
            logger.error("exception while writing new file:{}", e);
            return null;
        }

        return file;
    }

    @Override
    public String getFileContent(String fileId) {

        File file = FileUtils.getFile(filePath + File.separator + fileId + TXT);

        //文件不存在或不可读
        if (file == null || !file.exists() || !file.canRead() || !file.isFile() || file.isHidden()) {
            if (logger.isWarnEnabled()) {
                logger.warn("user try to access unavailable file,fileId:{}", fileId);
            }
            return null;
        }

        //读取文件
        String result = null;
        try {
            result = FileUtils.readFileToString(file, UTF_8);
        } catch (IOException e) {
            logger.error("read file fail,fileId:{}", fileId);
            return null;
        }
        return result;
    }

    @Override
    public void handleDownload(HttpServletResponse response, String fileId, String fileName) {

        File file = FileUtils.getFile(filePath + File.separator + fileId + TXT);
        if (!file.exists() || file.isHidden() || !file.canRead() || file.isHidden()) {
            throw new BadRequestException(FILE_NOT_FOUND);
        }
        try (FileInputStream fileInputStream = FileUtils.openInputStream(file)) {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (IOException e) {
            logger.error("handle download file error:{}", e);
            throw new ServerInternalException(READ_FILE_ERROR);
        }

    }
}
