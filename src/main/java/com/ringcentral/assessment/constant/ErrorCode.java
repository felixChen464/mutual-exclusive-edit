package com.ringcentral.assessment.constant;


import com.ringcentral.assessment.util.MapUtil;

import java.util.Map;

/**
 * @Author cxh
 * @Date 2020/7/11
 */
public interface ErrorCode {

    public static final String LACK_OF_FILE_ID = "lack.of.file.id";

    public static final String LACK_OF_FILE_NAME = "lack.of.file.name";

    public static final String FILE_NOT_FOUND = "file.not.found";

    public static final String LACK_OF_FILE_CODE = "lack.of.file.code";

    public static Map descriptionMap = MapUtil.asMap(
            LACK_OF_FILE_ID,"缺少文件id",

            LACK_OF_FILE_NAME,"缺少文件名",

            FILE_NOT_FOUND,"文件未找到",

            FILE_NOT_FOUND,"缺少文件码"
    );

}
