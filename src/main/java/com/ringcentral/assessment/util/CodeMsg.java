package com.ringcentral.assessment.util;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cxh
 * @Date 2020/7/11
 */
@Data
@Accessors(chain = true)
public class CodeMsg {

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String msg;


    /**
     * 私有构造器
     * @param code
     * @param msg
     */
    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 通用错误返回
     */
    public static final CodeMsg SERVER_INTERNAL_ERROR = new CodeMsg(500,"server internal error");
    public static final CodeMsg BAD_REQUEST_ERROR = new CodeMsg(400,"%s！");
    public static final CodeMsg BIZ_ERROR = new CodeMsg(60002,"biz exception：%s！");
}
