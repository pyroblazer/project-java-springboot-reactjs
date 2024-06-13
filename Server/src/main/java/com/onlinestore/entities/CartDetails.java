package com.onlinestore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "cart_details", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class CartDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    private int quantity;
    private double amount;

    @ManyToOne(fetch = FetchType.EAGER)
    private Cart cart;
}
