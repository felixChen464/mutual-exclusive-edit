package com.ringcentral.assessment.service.impl;

import com.ringcentral.assessment.dao.FileRecordMapper;
import com.ringcentral.assessment.entity.FileRecord;
import com.ringcentral.assessment.exception.BadRequestException;
import com.ringcentral.assessment.file.IFileHandler;
import com.ringcentral.assessment.lock.LockService;
import com.ringcentral.assessment.lock.entity.LockItem;
import com.ringcentral.assessment.protocol.FileProtocol;
import com.ringcentral.assessment.service.IFileRecordService;
import com.ringcentral.assessment.util.Result;
import com.ringcentral.assessment.vo.ContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static com.ringcentral.assessment.constant.CommonConstant.TXT;
import static com.ringcentral.assessment.constant.ErrorCode.FILE_NOT_FOUND;
import static com.ringcentral.assessment.constant.ErrorCode.SUCH_FILE_IS_ON_LOCK;

/**
 * @Author cxh
 * @Date 2020/7/11
 */

@Service
public class FileRecordServiceImpl implements IFileRecordService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 锁要维持的时间
     */
    @Value(value = "${lock.seconds}")
    private Integer lockSeconds;

    @Autowired
    private FileRecordMapper fileRecordMapper;

    @Autowired
    private IFileHandler fileHandler;

    @Autowired
    private LockService lockService;

    @Override
    public FileRecord getById(String fileId) {
        FileRecord record = fileRecordMapper.getById(fileId);
        if (record == null) {
            throw new BadRequestException(FILE_NOT_FOUND);
        }
        return record;
    }

    @Override
    public List<FileRecord> getAll() {
        return fileRecordMapper.getAll();
    }

    @Override
    public ContentVo getFileContent(String fileId) {

        FileRecord fileRecord = fileRecordMapper.getById(fileId);

        if (fileRecord == null) {
            throw new BadRequestException(FILE_NOT_FOUND);
        }

        String content = fileHandler.getFileContent(fileId);

        if (content == null) {
            throw new BadRequestException(FILE_NOT_FOUND);
        }

        return new ContentVo().setContent(content).setFileName(fileRecord.getFileName());
    }

    @Override
    public LockItem getFileStatus(String fileId, String userId) {

        return lockService.getLockItem(fileId);
    }

    @Override
    public FileRecord saveFile(String fileName, String content) {

        //todo 可以考虑添加文件名重复的校验

        //生成文件id
        String fileId = UUID.randomUUID().toString();

        //存文件
        fileHandler.writeStringFile(fileId, content);

        String createTime = sdf.format(new Date());
        FileRecord fileRecord = new FileRecord()
                .setId(fileId)
                .setFileName(fileName + TXT)
                .setCreateTime(createTime)
                .setModifyTime(createTime);

        //保存对应记录
        fileRecordMapper.insert(fileRecord);
        return fileRecord;
    }

    @Override
    public Result editFile(FileProtocol.EditFile.Input input) {
        if (lockService.tryLock(input.getFileId(), input.getUserId(), lockSeconds)) {
            fileHandler.writeStringFile(input.getFileId(), input.getContent());
            fileRecordMapper.updateModifyTime(sdf.format(new Date()),input.getFileId());
            return Result.success();
        } else {
            return Result.validationError(SUCH_FILE_IS_ON_LOCK);
        }
    }

    @Override
    public Result lock(FileProtocol.Lock.Input input) {

        //尝试加锁
        lockService.tryLock(input.getFileId(), input.getUserId(), lockSeconds);

        //返回加锁结果
        return Result.success(lockService.getLockItem(input.getFileId()));
    }
}
