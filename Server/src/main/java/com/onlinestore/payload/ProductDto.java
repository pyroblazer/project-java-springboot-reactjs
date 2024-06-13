package com.onlinestore.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class ProductDto {
    private int id;
    private String name;
    private String description;
    private Double price;
    private Double weight;
    private byte[] img;
}
