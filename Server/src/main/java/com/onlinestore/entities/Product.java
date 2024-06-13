package com.onlinestore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "product", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

    private String description;
    private Double price;
    private Double weight;
    @Column(length = 65555)
    private byte[] img;

    @ToString.Exclude
    @OneToMany(mappedBy = "product")
    private List<CartDetails> cartDetailsList;

    @ToString.Exclude
    @OneToMany(mappedBy = "product")
    private List<OrderDetails> orderDetailsList;
}
