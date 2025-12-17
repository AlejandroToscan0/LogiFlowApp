package com.logiflow.billing_service.controller;

import com.logiflow.billing_service.dto.CalculateFareRequest;
import com.logiflow.billing_service.dto.InvoiceResponseDto;
import com.logiflow.billing_service.service.BillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/billing")
@Validated
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<BigDecimal> calculateFare(@Valid @RequestBody CalculateFareRequest request) {
        BigDecimal amount = billingService.calculateFare(request);
        return ResponseEntity.ok(amount);
    }

    @PostMapping("/invoices")
    public ResponseEntity<InvoiceResponseDto> createInvoice(@RequestParam(name = "customerId") String customerId,
            @Valid @RequestBody CalculateFareRequest request) {
        InvoiceResponseDto dto = billingService.createInvoice(customerId, request);
        return ResponseEntity.ok(dto);
    }
}
