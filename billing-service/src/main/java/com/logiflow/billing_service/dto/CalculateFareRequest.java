package com.logiflow.billing_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CalculateFareRequest {
    @NotNull
    @DecimalMin("0")
    private BigDecimal distanceKm;

    @NotNull
    @DecimalMin("0")
    private BigDecimal durationMin;

    public BigDecimal getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(BigDecimal distanceKm) {
        this.distanceKm = distanceKm;
    }

    public BigDecimal getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(BigDecimal durationMin) {
        this.durationMin = durationMin;
    }
}
