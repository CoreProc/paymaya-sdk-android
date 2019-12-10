package com.coreproc.paymaya.sdk.payment_processor.models;

public class RequestProcessorError {

    private Integer code;
    private String message;

    public RequestProcessorError(Integer code) {
        this.code = code;
    }

    public RequestProcessorError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
