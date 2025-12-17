package com.logiflow.billing_service.service.impl;

import com.logiflow.billing_service.dto.CalculateFareRequest;
import com.logiflow.billing_service.dto.InvoiceResponseDto;
import com.logiflow.billing_service.enums.InvoiceState;
import com.logiflow.billing_service.model.Invoice;
import com.logiflow.billing_service.service.BillingService;
import com.logiflow.billing_service.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class BillingServiceImpl implements BillingService {

    // parámetros simples para el cálculo
    private static final BigDecimal BASE_FARE = new BigDecimal("2.50");
    private static final BigDecimal PER_KM = new BigDecimal("1.20");
    private static final BigDecimal PER_MIN = new BigDecimal("0.25");

    private final InvoiceRepository invoiceRepository;

    public BillingServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public BigDecimal calculateFare(CalculateFareRequest request) {
        BigDecimal distance = request.getDistanceKm() == null ? BigDecimal.ZERO : request.getDistanceKm();
        BigDecimal duration = request.getDurationMin() == null ? BigDecimal.ZERO : request.getDurationMin();

        BigDecimal fare = BASE_FARE
                .add(PER_KM.multiply(distance))
                .add(PER_MIN.multiply(duration));

        return fare.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public InvoiceResponseDto createInvoice(String customerId, CalculateFareRequest request) {
        BigDecimal amount = calculateFare(request);
        Invoice invoice = new Invoice();
        invoice.setCustomerId(customerId);
        invoice.setAmount(amount);
        invoice.setState(InvoiceState.BORRADOR);

        Invoice saved = invoiceRepository.save(invoice);

        InvoiceResponseDto dto = new InvoiceResponseDto();
        dto.setId(saved.getId());
        dto.setAmount(saved.getAmount());
        dto.setCustomerId(saved.getCustomerId());
        dto.setState(saved.getState());
        dto.setCreatedAt(saved.getCreatedAt());

        return dto;
    }
}
