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
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM file_record WHERE id = #{id} LIMIT 1")
    @ResultMap("fileRecord")
    FileRecord getById(String id);


    /**
     * 获取所有文件列表
     *
     * @return
     */

    @Results(id = "fileRecord", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "modifyTime", column = "modifyTime"),

    })
    @Select("SELECT * FROM file_record")
    List<FileRecord> getAll();

    /**
     * 插入单个文件记录
     *
     * @param fileRecord
     */
    @Insert("INSERT INTO file_record(id,fileName,createTime,modifyTime) VALUES(#{id}, #{fileName},#{createTime},#{modifyTime})")
    void insert(FileRecord fileRecord);

    /**
     * 更新文件的更新时间
     *
     * @param modifyTime
     * @param id
     */
    @Update("UPDATE file_record SET modifyTime = #{modifyTime} WHERE id = #{id}")
    void updateModifyTime(@Param("modifyTime") String modifyTime, @Param("id") String id);

    /**
     * 删除单个文件记录
     *
     * @param fileId
     */
    @Delete("DELETE FROM file_record id = #{fileId}")
    void delete(String fileId);

}
