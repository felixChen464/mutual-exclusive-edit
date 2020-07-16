package com.ringcentral.assessment.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cxh
 * @Date 2020/7/15
 */

@Data
@Accessors(chain = true)
public class ContentVo {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 内容
     */
    private String content;
}
