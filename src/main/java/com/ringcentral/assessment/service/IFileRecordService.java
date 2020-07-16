package com.ringcentral.assessment.service;

import com.ringcentral.assessment.entity.FileRecord;
import com.ringcentral.assessment.lock.entity.LockItem;
import com.ringcentral.assessment.protocol.FileProtocol;
import com.ringcentral.assessment.util.Result;
import com.ringcentral.assessment.vo.ContentVo;
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
     * 获取文件内容
     * @param fileId
     * @return
     */
    ContentVo getFileContent(String fileId);

    /**
     *查看文件当前状态
     * @param fileId
     * @return
     */
    LockItem getFileStatus(String fileId, String userId);

    /**
     * 存储文件
     * @param fileName
     * @param content
     * @return
     */
    FileRecord saveFile(String fileName,String content);

    /**
     * 编辑文件
     * @param input
     * @return
     */
    Result editFile(FileProtocol.EditFile.Input input);

    /**
     * 给某个文件加锁
     * @param input
     * @return
     */
    Result lock(FileProtocol.Lock.Input input);
}
