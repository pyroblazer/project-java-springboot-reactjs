package com.onlinestore.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentInfoRequest {
    private String id;
    private Long amount;
    private String currency;
    private String receiptEmail;
}
