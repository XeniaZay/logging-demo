package com.example.logging_demo;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class PartnerHealthIndicator implements HealthIndicator {
    private static final Logger log = LoggerFactory.getLogger(PartnerHealthIndicator.class);

    private final PartnerHealthProperties properties;
    private final HttpClient httpClient;

    public PartnerHealthIndicator(PartnerHealthProperties properties) {
        this.properties = properties;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(properties.getTimeoutMs()))
                .build();
    }

    @Override
    public Health health() {
        String url = properties.getHealthUrl();
        long timeout = properties.getTimeoutMs();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofMillis(timeout))
                    .GET()
                    .build();

            HttpResponse<Void> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.discarding());

            int statusCode = response.statusCode();

            if (statusCode >= 200 && statusCode < 400) {
                return Health.up()
                        .withDetail("url", url)
                        .withDetail("status", statusCode)
                        .withDetail("responseTimeMs", "measured below")
                        .build();
            }

            return Health.down()
                    .withDetail("url", url)
                    .withDetail("status", statusCode)
                    .withDetail("reason", "unexpected status code")
                    .build();

        } catch (Exception ex) {
            log.warn("Partner health check failed: url={} error={}", url, ex.getMessage());
            return Health.down(ex)
                    .withDetail("url", url)
                    .withDetail("error", ex.getClass().getSimpleName())
                    .build();
        }
    }

}
