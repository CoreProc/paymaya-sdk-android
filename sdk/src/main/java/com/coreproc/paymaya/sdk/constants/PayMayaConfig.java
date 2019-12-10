package com.coreproc.paymaya.sdk.constants;

public enum PayMayaConfig {

    SANDBOX, PRODUCTION;

    public String getUrl() {
        if (this == PayMayaConfig.PRODUCTION) {
            return "https://pg.paymaya.com";
        }
        return "https://pg-sandbox.paymaya.com";
    }

    public String getPaymentVaultEndpoint() {
        return getUrl() + "/payments/v1/payment-tokens";
    }

}
