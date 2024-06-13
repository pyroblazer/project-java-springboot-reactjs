package com.onlinestore.payload;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class CartDtoTest {

    @Test
    public void testCartDto_SettersAndGetters() {
        int id = 1;
        UserDto user = new UserDto(10, "John Doe", "johndoe@example.com", "password123", "USER");
        double totalAmount = 19.99;
        List<CartDetailDto> cartDetails = new ArrayList<>();

        CartDto cartDto = new CartDto(id, user, totalAmount, cartDetails);

        assertEquals(id, cartDto.getId());
        assertEquals(user, cartDto.getUser());
        assertEquals(totalAmount, cartDto.getTotalAmount());
        assertEquals(cartDetails, cartDto.getCartDetails());

        int newId = 2;
        double newTotalAmount = 39.98;
        List<CartDetailDto> newCartDetails = createSampleCartDetails();

        cartDto.setId(newId);
        cartDto.setTotalAmount(newTotalAmount);
        cartDto.setCartDetails(newCartDetails);

        assertEquals(newId, cartDto.getId());
        assertEquals(newTotalAmount, cartDto.getTotalAmount());
        assertEquals(newCartDetails, cartDto.getCartDetails());
    }

    @Test
    public void testCartDto_NoArgsConstructor() {
        CartDto cartDto = new CartDto();

        assertEquals(0, cartDto.getId());
        assertNull(cartDto.getUser());
        assertEquals(0f, cartDto.getTotalAmount());
        assertNotNull(cartDto.getCartDetails());
    }


    @Test
    public void testCartDto_Equals_ShouldConsiderAllFields() {
        CartDto cartDto1 = new CartDto(1, new UserDto(1, "John", "john@example.com", "password", "USER"), 10.0, new ArrayList<>());
        CartDto cartDto2 = new CartDto(1, new UserDto(1, "John", "john@example.com", "password", "USER"), 10.0, new ArrayList<>());
        CartDto cartDto3 = new CartDto(2, new UserDto(), 0.0, new ArrayList<>());

        assertTrue(cartDto1.equals(cartDto2));
        assertFalse(cartDto1.equals(cartDto3));
    }

    @Test
    public void testCartDto_HashCode_ShouldConsiderAllFields() {
        CartDto cartDto1 = new CartDto(1, new UserDto(1, "John", "john@example.com", "password", "USER"), 10.0, new ArrayList<>());
        CartDto cartDto2 = new CartDto(1, new UserDto(1, "John", "john@example.com", "password", "USER"), 10.0, new ArrayList<>());
        CartDto cartDto3 = new CartDto(2, new UserDto(), 0.0, new ArrayList<>());

        // Assert that equal objects have the same hash code
        assertEquals(cartDto1.hashCode(), cartDto2.hashCode());

        // Assert that objects with different values have different hash codes
        assertNotEquals(cartDto1.hashCode(), cartDto3.hashCode());
    }

    private List<CartDetailDto> createSampleCartDetails() {
        // Create sample product and CartDetailDto for testing
        ProductDto product = new ProductDto(101, "T-Shirt", "Cool T-Shirt", 19.99d, 0.25d, null);
        CartDetailDto cartDetailDto = new CartDetailDto(1, product, 2,
                product.getPrice() * 2);

        List<CartDetailDto> cartDetails = new ArrayList<>();
        cartDetails.add(cartDetailDto);

        return cartDetails;
    }
}
