package com.ringcentral.assessment.constant;


import com.ringcentral.assessment.util.MapUtil;

import java.util.Map;

/**
 * @Author cxh
 * @Date 2020/7/11
 */
public interface ErrorCode {

    public static final String LACK_OF_FILE_ID = "lack.of.file.id";

    public static final String LACK_OF_USER_ID = "lack.of.user.id";

    public static final String LACK_OF_FILE_NAME = "lack.of.file.name";

    public static final String FILE_NOT_FOUND = "file.not.found";

    public static final String LACK_OF_FILE_CODE = "lack.of.file.fileCode";

    public static final String SUCH_FILE_IS_ON_LOCK = "such.file.is.on.lock";

    public static final String READ_FILE_ERROR = "read.file.error";

    public static Map descriptionMap = MapUtil.asMap(

            LACK_OF_FILE_ID,"缺少文件id",

            LACK_OF_USER_ID,"缺少用户id",

            LACK_OF_FILE_NAME,"缺少文件名",

            FILE_NOT_FOUND,"文件未找到",

            FILE_NOT_FOUND,"缺少文件码",

            SUCH_FILE_IS_ON_LOCK,"该文件正在被锁定",

            READ_FILE_ERROR,"读取文件错误"
    );

}
