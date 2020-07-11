package com.ringcentral.assessment.util;

import lombok.Data;
import java.io.Serializable;
/**
 * @Author cxh
 * @Date 2020/7/11
 */
@Data
public class Result<T> implements Serializable {

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
    private T data;

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(0, "success", data);
    }

    /**
     * 成功返回
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> success() {
        return success(null);
    }


    /**
     * 失败返回
     *
     * @param codeMsg
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<T>(codeMsg.getCode(), codeMsg.getMsg(), null);
    }

    /**
     * 失败返回
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> validationError(String msg) {
        CodeMsg validationError = CodeMsg.BAD_REQUEST_ERROR;
        String format = String.format(validationError.getMsg(), msg);
        return new Result<T>(validationError.getCode(), format, null);
    }

    /**
     * 失败返回
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> badRequest(Integer code,String msg) {
        return new Result<T>(code,msg,null);
    }
}
