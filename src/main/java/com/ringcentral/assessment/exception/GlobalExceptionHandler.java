package com.ringcentral.assessment.exception;

import com.ringcentral.assessment.util.CodeMsg;
import com.ringcentral.assessment.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author cxh
 * @Date 2020/7/12
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BadRequestException.class)
    public Result handleException(BadRequestException e) {
        logger.warn("bad request,message:{}",e);
        return Result.validationError(e.getShortMessage());
    }

    @ExceptionHandler(value = ServerInternalException.class)
    public Result handleException(ServerInternalException e) {
        logger.warn("server internal exception,shortMessage:{}",e);
        return Result.error(CodeMsg.SERVER_INTERNAL_ERROR);
    }


    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e) {
        logger.error("unknown exception,reason:{}:", e);
        return Result.error(CodeMsg.SERVER_INTERNAL_ERROR);
    }
}
