package com.ringcentral.assessment.dao;

import com.ringcentral.assessment.entity.FileRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author cxh
 * @Date 2020/7/11
 */

public interface FileRecordMapper {

    /**
     * 根据文件id查询
     * @param id
     * @return
     */
    @Select("SELECT * FROM file_record WHERE id = #{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "fileName",column = "file_name")
    })
    FileRecord getById(String id);

    /**
     * 获取所有文件列表
     * @return
     */
    @Select("SELECT * FROM file_record")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "fileName",column = "file_name")
    })
    List<FileRecord> getAll();

    /**
     * 插入单个文件记录
     * @param fileRecord
     */
    @Insert("INSERT INTO file_record(id,fileName) VALUES(#{id}, #{fileName})")
    void insert(FileRecord fileRecord);

    /**
     * 删除单个文件记录
     * @param fileId
     */
    @Delete("DELETE FROM file_record id = #{fileId}")
    void delete(String fileId);
    
}
