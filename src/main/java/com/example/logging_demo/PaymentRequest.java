package com.example.logging_demo;

public record PaymentRequest(String paymentId,
                             Long amountMinor,
                             String currency,
                             String cardNumber,
                             String cvv,
                             String token,
                             String email) {
}
