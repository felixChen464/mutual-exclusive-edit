package com.ringcentral.assessment.service.impl;

import com.ringcentral.assessment.dao.FileRecordMapper;
import com.ringcentral.assessment.entity.FileRecord;
import com.ringcentral.assessment.service.IFileRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author cxh
 * @Date 2020/7/11
 */

@Service
public class FileRecordServiceImpl implements IFileRecordService {

    @Autowired
    private FileRecordMapper fileRecordMapper;

    @Override
    public FileRecord getById(String fileId) {
        return null;
    }

    @Override
    public List<FileRecord> getAll() {
        return null;
    }

    @Override
    public FileRecord getFile(String fileId) {
        return null;
    }

    @Override
    public FileRecord saveFile(String fileName, String content) {
        return null;
    }

    @Override
    public FileRecord editFile(String fileId, String fileName, String content) {
        return null;
    }
}
