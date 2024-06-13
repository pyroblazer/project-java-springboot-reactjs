package com.onlinestore.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order", schema="public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant moment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @Builder.Default
    private List<OrderDetails> orderDetails = new ArrayList<>();

    @Builder.Default
    private Integer orderStatus = 1;

    private double totalAmount;

    @Override
    public String toString() {
        return "Order(id=" + id + ", moment=" + moment + ", orderStatus=" + orderStatus + ", totalAmount=" + totalAmount + ")";
    }
}

