package com.onlinestore.payload;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartHelp {
    private String userEmail;
    private int productId;
    private int quantity;
}
