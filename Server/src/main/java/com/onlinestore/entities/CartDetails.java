package com.onlinestore.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CartDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CartDetailsId;

    @ManyToOne
    private Product products;
    private int Quantity;
    private int Amount;

    @ManyToOne
    private Cart cart;
}
