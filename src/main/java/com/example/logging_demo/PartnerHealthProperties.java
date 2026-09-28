package com.example.logging_demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("partner")
public class PartnerHealthProperties {
    private String healthUrl;
    private long timeoutMs;

    public String getHealthUrl() { return healthUrl; }
    public void setHealthUrl(String healthUrl) { this.healthUrl = healthUrl; }
    public long getTimeoutMs() { return timeoutMs; }
    public void setTimeoutMs(long timeoutMs) { this.timeoutMs = timeoutMs; }
}
