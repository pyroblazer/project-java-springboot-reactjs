package com.onlinestore.payload;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class CartDetailDtoTest {

    @Test
    public void testCartDetailDto_NoArgsConstructor_ShouldCreateEmptyObject() {
        CartDetailDto cartDetailDto = new CartDetailDto();

        assertAll("cartDetailDto",
                () -> assertEquals(0, cartDetailDto.getId()),
                () -> assertNull(cartDetailDto.getProduct()),
                () -> assertEquals(0, cartDetailDto.getQuantity()),
                () -> assertEquals(0.0, cartDetailDto.getAmount()));
    }

    @Test
    public void testCartDetailDto_AllArgsConstructor_ShouldSetValuesCorrectly() {
        ProductDto productDto = new ProductDto(1, "Test Product", "This is a test product description", 10.0, 1.5, null);
        CartDetailDto cartDetailDto = new CartDetailDto(1, productDto, 2, 20.0);

        assertAll("cartDetailDto",
                () -> assertEquals(1, cartDetailDto.getId()),
                () -> assertEquals(productDto, cartDetailDto.getProduct()),
                () -> assertEquals(2, cartDetailDto.getQuantity()),
                () -> assertEquals(20.0, cartDetailDto.getAmount()));
    }

    @Test
    public void testCartDetailDto_SettersAndGetters_ShouldWorkAsExpected() {
        CartDetailDto cartDetailDto = new CartDetailDto();

        cartDetailDto.setId(2);
        ProductDto productDto = new ProductDto(2, "Another Test Product", "This is another test product description", 15.0, 2.0, null);
        cartDetailDto.setProduct(productDto);
        cartDetailDto.setQuantity(3);
        cartDetailDto.setAmount(45.0);

        assertAll("cartDetailDto",
                () -> assertEquals(2, cartDetailDto.getId()),
                () -> assertEquals(productDto, cartDetailDto.getProduct()),
                () -> assertEquals(3, cartDetailDto.getQuantity()),
                () -> assertEquals(45.0, cartDetailDto.getAmount()));
    }
}