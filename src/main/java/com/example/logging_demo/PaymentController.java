package com.example.logging_demo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final Counter paymentCaptureErrors;

    public PaymentController(MeterRegistry meterRegistry) {
        this.paymentCaptureErrors = Counter.builder("payment.capture.errors")
                .description("Number of failed payment capture attempts")
                .register(meterRegistry);
    }

    @PostMapping("/capture")
    PaymentResponse capture(@RequestBody PaymentRequest request) {
        PaymentCaptureLog safeLog = new PaymentCaptureLog(
                request.paymentId(),
                request.amountMinor(),
                request.currency(),
                "REQUESTED"
        );
        log.info("payment.capture.requested {}", safeLog);

        try {
            if (request.amountMinor() <= 0) {
                throw new IllegalArgumentException("amount must be positive");
            }
            log.info("payment.capture.completed {}", safeLog.withStatus("CAPTURED"));
            return new PaymentResponse(request.paymentId(), "CAPTURED");

        } catch (Exception ex) {
            paymentCaptureErrors.increment();
            log.error("payment.capture.failed {}", safeLog.withStatus("FAILED"), ex);
            throw ex;
        }

    }


}
