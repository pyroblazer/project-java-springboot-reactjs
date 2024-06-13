package com.onlinestore.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor

public class PaymentDetails {
    private String orderId;
    private Double amount;
    private String currency;
    private String key;
}
