package com.onlinestore.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class CartDetailDto {
    private int id;
    @NotNull(message = "Product cannot be null")
    private ProductDto product;
    @Min(value = 0, message = "Quantity must be at least 0")
    @Builder.Default
    private int quantity = 0;
    @Min(value = 0, message = "Amount must be at least 0")
    @Builder.Default
    private double amount = 0;
}
