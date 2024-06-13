package com.onlinestore.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class OrderDetailDto {
    private int id;
    private ProductDto product;
    private int quantity;
    private double amount;
}
