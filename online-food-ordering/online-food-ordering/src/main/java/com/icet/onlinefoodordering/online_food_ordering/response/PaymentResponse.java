package com.icet.onlinefoodordering.online_food_ordering.response;

import lombok.Data;

@Data
public class PaymentResponse {
    private String payment_url;
    private String status;

    public String getPayment_url() {
        return payment_url;
    }

    public void setPayment_url(String payment_url) {
        this.payment_url = payment_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
