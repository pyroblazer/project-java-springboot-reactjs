package com.onlinestore.payload;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private int id;
    private UserDto user;
    private double totalAmount;
    @Builder.Default
    private List<CartDetailDto> cartDetails = new ArrayList<>();
}
