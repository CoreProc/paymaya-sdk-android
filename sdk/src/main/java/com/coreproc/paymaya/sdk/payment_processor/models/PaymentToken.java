package com.coreproc.paymaya.sdk.payment_processor.models;

public class PaymentToken {

    private String paymentTokenId;
    private String state;
    private String env;
    private String type;
    private String error;

    public String getPaymentTokenId() {
        return paymentTokenId;
    }

    public String getError() {
        return error;
    }

    public String getType() {
        return type;
    }

    public String getEnv() {
        return env;
    }

    public String getState() {
        return state;
    }

    public void setPaymentTokenId(String paymentTokenId) {
        this.paymentTokenId = paymentTokenId;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setError(String error) {
        this.error = error;
    }
}
