package com.onlinestore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class Cart implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private double TotalAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", fetch=FetchType.EAGER)
    @Builder.Default
    private List<CartDetails> cartDetails = new ArrayList<>();

}
