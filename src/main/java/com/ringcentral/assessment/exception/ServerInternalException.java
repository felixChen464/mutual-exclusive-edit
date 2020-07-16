package com.ringcentral.assessment.exception;

import lombok.Data;

/**
 * @Author cxh
 * @Date 2020/7/12
 */

@Data
public class ServerInternalException extends RuntimeException {

    private String message;

    public ServerInternalException(String message) {
        super(message);
        this.message = message;
    }
}
