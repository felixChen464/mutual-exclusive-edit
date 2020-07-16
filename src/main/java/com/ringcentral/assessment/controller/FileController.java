package com.ringcentral.assessment.controller;

import com.ringcentral.assessment.protocol.FileProtocol;
import com.ringcentral.assessment.service.IFileRecordService;
import com.ringcentral.assessment.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static com.ringcentral.assessment.constant.ErrorCode.*;

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
        if (StringUtils.isEmpty(fileId)) {
            return Result.validationError(LACK_OF_FILE_ID);
        }
        return Result.success(iFileRecordService.getFileContent(fileId));
    }


    /**
     * 当前文件状态
     * @param fileId
     * @param userId
     * @return
     */
    @GetMapping("/status")
    public Result getFileStatus(String fileId,String userId){
        if(StringUtils.isEmpty(fileId)){
            return Result.validationError(LACK_OF_FILE_ID);
        }
        return Result.success(iFileRecordService.getFileStatus(fileId,userId));
    }

    /**
     * 保存文件
     * @param input
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody FileProtocol.SaveFile.Input input){

        if(StringUtils.isEmpty(input.getFileName())){
            return Result.validationError(LACK_OF_FILE_NAME);
        }
        return Result.success(iFileRecordService.saveFile(input.getFileName(),input.getContent()));
    }

    @PostMapping("/lock")
    public Result lock(@RequestBody FileProtocol.Lock.Input input){
        if (StringUtils.isEmpty(input.getFileId())){
            return Result.validationError(LACK_OF_FILE_ID);
        }
        if (StringUtils.isEmpty(input.getUserId())){
            return Result.validationError(LACK_OF_USER_ID);
        }
        return iFileRecordService.lock(input);
    }

    /**
     * 编辑文件
     * @param input
     * @return
     */
    @PostMapping("/edit")
    public Result edit(@RequestBody FileProtocol.EditFile.Input input){

        if (StringUtils.isEmpty(input.getFileId())){
            return Result.validationError(LACK_OF_FILE_ID);
        }
        return iFileRecordService.editFile(input);
    }

}
