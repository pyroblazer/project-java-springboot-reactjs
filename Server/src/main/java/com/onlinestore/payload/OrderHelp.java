package com.onlinestore.payload;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderHelp {
    private int userId;
    private int productId;
    private int quantity;
}
