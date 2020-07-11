package com.ringcentral.assessment.service;

import com.ringcentral.assessment.entity.FileRecord;
import java.util.List;

public interface IFileRecordService {

    /**
     * 找到该id对应的记录
     */
    FileRecord getById(String fileId);

    /**
     * 获取所有文件记录
     * @return
     */
    List<FileRecord> getAll();

    /**
     * 获取文件记录
     * @param fileId
     * @return
     */
    FileRecord getFile(String fileId);

    /**
     * 存储文件
     * @param fileName
     * @param content
     * @return
     */
    FileRecord saveFile(String fileName,String content);

    /**
     * 编辑文件
     * @param fileId
     * @param fileName
     * @param content
     * @return
     */
    FileRecord editFile(String fileId,String fileName,String content);
}
