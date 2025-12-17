package com.logiflow.billing_service.service;

import com.logiflow.billing_service.dto.CalculateFareRequest;
import com.logiflow.billing_service.dto.InvoiceResponseDto;

import java.math.BigDecimal;

public interface BillingService {
    BigDecimal calculateFare(CalculateFareRequest request);

    InvoiceResponseDto createInvoice(String customerId, CalculateFareRequest request);
}
