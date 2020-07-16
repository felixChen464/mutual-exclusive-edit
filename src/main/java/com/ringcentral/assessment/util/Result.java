package com.ringcentral.assessment.util;

import lombok.Data;
import java.io.Serializable;
/**
 * @Author cxh
 * @Date 2020/7/11
 */
@Data
public class Result implements Serializable {

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 正常数据返回
     */
    private Object data;

    private Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回
     *
     * @param data
     * @param
     * @return
     */
    public static  Result success(Object data) {
        return new Result(0, "success", data);
    }

    /**
     * 成功返回
     *
     * @param
     * @return
     */
    public static  Result success() {
        return success(null);
    }


    /**
     * 失败返回
     *
     * @param codeMsg
     * @param
     * @return
     */
    public static  Result error(CodeMsg codeMsg) {
        return new Result(codeMsg.getCode(), codeMsg.getMsg(), null);
    }

    /**
     * 失败返回
     *
     * @param msg
     * @param
     * @return
     */
    public static Result validationError(String msg) {
        CodeMsg validationError = CodeMsg.BAD_REQUEST_ERROR;
        String format = String.format(validationError.getMsg(), msg);
        return new Result(validationError.getCode(), format, null);
    }

}
