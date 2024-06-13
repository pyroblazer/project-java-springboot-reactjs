package com.onlinestore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "order_details", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Product product;
    private int quantity;
    private double amount;

    @ToString.Exclude
    @ManyToOne
    private Order order;

    @Override
    public String toString() {
        return "OrderDetails(id=" + id + ", product=" + product + ", quantity=" + quantity + ", amount=" + amount + ")";
    }
}
