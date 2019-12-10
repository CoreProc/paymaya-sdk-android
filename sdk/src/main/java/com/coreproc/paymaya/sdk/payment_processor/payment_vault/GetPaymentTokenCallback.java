package com.coreproc.paymaya.sdk.payment_processor.payment_vault;

import com.coreproc.paymaya.sdk.payment_processor.models.RequestProcessorError;
import com.coreproc.paymaya.sdk.payment_processor.models.PaymentToken;

public interface GetPaymentTokenCallback {

    void onStart();

    void onSuccess(PaymentToken paymentToken);

    void onError(RequestProcessorError requestProcessorError);

    void onEnd();

}
