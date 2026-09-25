package com.example.logging_demo;

public record PaymentCaptureLog(String paymentId,
                                Long amountMinor,
                                String currency,
                                String status) {

    public PaymentCaptureLog withStatus(String newStatus) {
        return new PaymentCaptureLog(paymentId, amountMinor, currency, newStatus);
    }

    @Override
    public String toString() {
        return "paymentId=" + paymentId +
                ", amountMinor=" + amountMinor +
                ", currency=" + currency +
                ", status=" + status;
    }
}
