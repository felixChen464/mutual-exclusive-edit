package com.ringcentral.assessment.exception;

import lombok.Data;
/**
 * @Author cxh
 * @Date 2020/7/12
 */

@Data
public class BadRequestException extends RuntimeException {

    private String shortMessage;

    public BadRequestException(String message) {
        super(message);
        this.shortMessage = message;
    }
}
