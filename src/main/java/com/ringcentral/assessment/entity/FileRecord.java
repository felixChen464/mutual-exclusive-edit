package com.ringcentral.assessment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author cxh
 * @Date 2020/7/11
 */

@Data
@Accessors(chain = true)
public class FileRecord implements Serializable {

    /**
     * 文件id，同时作为存储的文件名
     */
    private String id;

    /**
     * 展示用文件名
     */
    private String fileName;

    /**
     * 文件的创建时间
     */
    private String createTime;

    /**
     * 文件的最近一次编辑时间
     */
    private String modifyTime;

}
