package com.onlinestore.payload;

import lombok.*;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private int id;
    private String name;
    private String email;
    private String password;
    private String role;
}
