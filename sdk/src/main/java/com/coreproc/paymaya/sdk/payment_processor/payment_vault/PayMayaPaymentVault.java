package com.coreproc.paymaya.sdk.payment_processor.payment_vault;

import android.content.Context;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.coreproc.paymaya.sdk.constants.PayMayaConfig;
import com.coreproc.paymaya.sdk.payment_processor.models.Card;
import com.coreproc.paymaya.sdk.payment_processor.models.PaymentToken;
import com.coreproc.paymaya.sdk.payment_processor.models.RequestProcessorError;
import com.coreproc.paymaya.sdk.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PayMayaPaymentVault {

    // Constants
    private Context context;
    private PayMayaConfig payMayaConfig;
    private String idempotentKey;
    private String clientKey;

    public PayMayaPaymentVault(Context context, PayMayaConfig payMayaConfig,
                               String clientKey) {
        this.context = context;
        this.payMayaConfig = payMayaConfig;
        this.clientKey = clientKey;
        this.idempotentKey = UUID.randomUUID().toString();
    }

    public void getPaymentToken(Card card, final GetPaymentTokenCallback getPaymentTokenCallback) {
        if (payMayaConfig == PayMayaConfig.SANDBOX) Logger.DEBUGGABLE = true;
        Logger.message("--------GET PAYMENT TOKEN STARTED---------");
        Logger.message("Preparing getting Payment Token...");
        Logger.message("Using CLIENT KEY: " + this.clientKey);
        this.clientKey = Base64.encodeToString((clientKey + ":").getBytes(), Base64.NO_WRAP);
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = payMayaConfig.getPaymentVaultEndpoint();
        final String authorizationKey = "Basic " + this.clientKey;

        Logger.message("Generating Payment Token Request Body...");
        final JSONObject cardJsonObject = new JSONObject();
        final JSONObject jsonObjectRequestBody = new JSONObject();
        try {
            cardJsonObject.put("number", card.getNumber());
            cardJsonObject.put("expMonth", card.getExpMonth());
            cardJsonObject.put("expYear", card.getExpYear());
            cardJsonObject.put("cvc", card.getCvc());
            jsonObjectRequestBody.put("card", cardJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            Logger.message(e);
            RequestProcessorError requestProcessorError =
                    new RequestProcessorError(500,
                            "Error occurred.");
            getPaymentTokenCallback.onError(requestProcessorError);
            return;
        }

        Logger.message("Payment Body: " + jsonObjectRequestBody.toString());
        Logger.message("Starting getting Payment Token...");

        getPaymentTokenCallback.onStart();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                jsonObjectRequestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.message("Getting Payment Token Response: \n" + response.toString());
                        Logger.message("Getting Payment Token success!");


                        getPaymentTokenCallback.onEnd();

                        if (response.has("error")) {
                            try {
                                String error = response.getString("error");
                                if (error != null && error.isEmpty()) {
                                    RequestProcessorError requestProcessorError =
                                            new RequestProcessorError(500,
                                                    "Error occurred.\n\n" + error);
                                    getPaymentTokenCallback.onError(requestProcessorError);
                                    return;
                                }
                            } catch (Exception e) {
                                Logger.message(e);
                                e.printStackTrace();
                            }
                        }

                        PaymentToken paymentToken = new PaymentToken();
                        if (response.has("paymentTokenId")) {
                            try {
                                paymentToken.setPaymentTokenId(response.getString("paymentTokenId"));
                            } catch (Exception ex) {
                                Logger.message(ex);
                                ex.printStackTrace();
                                RequestProcessorError requestProcessorError =
                                        new RequestProcessorError(500,
                                                "Error occurred.\n\n" + ex);
                                getPaymentTokenCallback.onError(requestProcessorError);
                                return;
                            }
                        }

                        getPaymentTokenCallback.onSuccess(paymentToken);
                        Logger.message("---------REQUEST ENDED---------");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getPaymentTokenCallback.onEnd();

                Logger.message("Getting Payment Token error!");
                Logger.message(error);

                RequestProcessorError requestProcessorError =
                        new RequestProcessorError(error.networkResponse.statusCode,
                                "Error occurred.\n\n" + error.getMessage() + error.getLocalizedMessage());

                if (error.networkResponse.data != null) {
                    String body = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    Logger.message(body);
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        if (jsonObject.has("message"))
                            requestProcessorError.setMessage(jsonObject.getString("message"));

                        if (jsonObject.has("parameters") && jsonObject.get("parameters") instanceof JSONArray) {
                            Logger.message("Error Body has parameters field");
                            StringBuilder descriptions = new StringBuilder();
                            JSONArray jsonArray = jsonObject.getJSONArray("parameters");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Logger.message("Error Body Description: " + jsonArray.getJSONObject(i).getString("description"));
                                descriptions.append(jsonArray.getJSONObject(i).getString("description")).append("\n");
                            }

                            if (descriptions.length() > 0)
                                requestProcessorError.setMessage(descriptions.toString());
                        }

                    } catch (Exception ex) {
                        Logger.message(ex);
                    }
                }

                getPaymentTokenCallback.onError(requestProcessorError);
                Logger.message("---------REQUEST ENDED---------");
            }


        }) {
            @Override
            public Map<String, String> getHeaders() {
                Logger.message("Setting Up headers for getting Payment Token...");
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", authorizationKey);
                headers.put("Idempotent-Token", idempotentKey);
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    Logger.message("HEADER: " + entry.getKey() + ": " + entry.getValue());
                }

                return headers;
            }
        };


        queue.add(jsonObjectRequest);
    }

}
