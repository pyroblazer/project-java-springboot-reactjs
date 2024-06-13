package com.onlinestore.payload;

import static org.junit.jupiter.api.Assertions.*;

import com.onlinestore.payload.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class UserDtoTest {

    @Test
    public void testUserDto_NoArgsConstructor_ShouldCreateEmptyObject() {
        UserDto userDto = new UserDto();

        assertAll("userDto",
                () -> assertEquals(0, userDto.getId()),
                () -> assertNull(userDto.getName()),
                () -> assertNull(userDto.getEmail()),
                () -> assertNull(userDto.getPassword()),
                () -> assertNull(userDto.getRole()));
    }

    @Test
    public void testUserDto_AllArgsConstructor_ShouldSetValuesCorrectly() {
        String name = "John Doe";
        String email = "johndoe@example.com";
        String password = "password123";
        String role = "USER";

        UserDto userDto = new UserDto(1, name, email, password, role);

        assertAll("userDto",
                () -> assertEquals(1, userDto.getId()),
                () -> assertEquals(name, userDto.getName()),
                () -> assertEquals(email, userDto.getEmail()),
                () -> assertEquals(password, userDto.getPassword()),
                () -> assertEquals(role, userDto.getRole()));
    }

    @Test
    public void testUserDto_SettersAndGetters_ShouldWorkAsExpected() {
        UserDto userDto = new UserDto();

        userDto.setId(2);
        String name = "Jane Doe";
        String email = "janedoe@example.com";
        String password = "secretpassword";
        String role = "ADMIN";

        userDto.setName(name);
        userDto.setEmail(email);
        userDto.setPassword(password);
        userDto.setRole(role);

        assertAll("userDto",
                () -> assertEquals(2, userDto.getId()),
                () -> assertEquals(name, userDto.getName()),
                () -> assertEquals(email, userDto.getEmail()),
                () -> assertEquals(password, userDto.getPassword()),
                () -> assertEquals(role, userDto.getRole()));
    }
}
