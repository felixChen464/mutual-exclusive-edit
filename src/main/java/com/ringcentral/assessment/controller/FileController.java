package com.ringcentral.assessment.controller;

import com.mysql.jdbc.StringUtils;
import com.ringcentral.assessment.entity.FileRecord;
import com.ringcentral.assessment.protocol.FileProtocol;
import com.ringcentral.assessment.service.IFileRecordService;
import com.ringcentral.assessment.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.ringcentral.assessment.constant.ErrorCode.FILE_NOT_FOUND;
import static com.ringcentral.assessment.constant.ErrorCode.LACK_OF_FILE_ID;
import static com.ringcentral.assessment.constant.ErrorCode.LACK_OF_FILE_NAME;

/**
 * @Author cxh
 * @Date 2020/7/11
 */

@RestController
@RequestMapping("/api/file/record")
public class FileController {

    @Autowired
    private IFileRecordService iFileRecordService;

    /**
     * 获取所有文件记录
     *
     * @return
     */
    @GetMapping("/all")
    public Result getAllFiles() {
        return Result.success(iFileRecordService.getAll());
    }

    /**
     * 获取某个文件的内容
     *
     * @param fileId
     * @return
     */
    @GetMapping("/content")
    public Result getFileContent(String fileId) {

        if (StringUtils.isEmptyOrWhitespaceOnly(fileId)) {
            return Result.validationError(LACK_OF_FILE_ID);
        }
        return Result.success(iFileRecordService.getFile(fileId));
    }

    /**
     * 获取文件下载地址
     *
     * @param fileId
     * @return
     */
    @GetMapping("/downloadUrl")
    public Result download(String fileId){

        if(StringUtils.isEmptyOrWhitespaceOnly(fileId)){
            return Result.validationError(LACK_OF_FILE_ID);
        }
        FileRecord fileRecord = iFileRecordService.getFile(fileId);
        if (fileRecord == null){
            return Result.validationError(FILE_NOT_FOUND);
        }
        return Result.success(fileRecord);
    }

    /**
     * 保存文件
     * @param input
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody FileProtocol.SaveFile.Input input){

        if(StringUtils.isEmptyOrWhitespaceOnly(input.getFileName())){
            return Result.validationError(LACK_OF_FILE_NAME);
        }
        return Result.success(iFileRecordService.saveFile(input.getFileName(),input.getContent()));
    }

    /**
     * 编辑文件
     * @param input
     * @return
     */
    @PostMapping("/edit")
    public Result edit(@RequestBody FileProtocol.EditFile.Input input){

        if (StringUtils.isEmptyOrWhitespaceOnly(input.getFileId())){
            return Result.validationError(LACK_OF_FILE_ID);
        }
        return Result.success(iFileRecordService.editFile(input.getFileId(),input.getFileName(),input.getContent()));
    }

}
