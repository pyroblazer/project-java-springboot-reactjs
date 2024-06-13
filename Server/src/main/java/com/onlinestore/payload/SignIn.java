package com.onlinestore.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class SignIn {
    private String email;
    private String password;
    private String jwt;
}
