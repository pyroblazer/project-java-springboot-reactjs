package com.onlinestore.payload;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class OrderDetailDtoTest {

    @Test
    public void testOrderDetailDto_NoArgsConstructor_ShouldCreateEmptyObject() {
        OrderDetailDto orderDetailDto = new OrderDetailDto();

        assertAll("orderDetailDto",
                () -> assertEquals(0, orderDetailDto.getId()),
                () -> assertNull(orderDetailDto.getProduct()),
                () -> assertEquals(0, orderDetailDto.getQuantity()),
                () -> assertEquals(0.0, orderDetailDto.getAmount()));
    }

    @Test
    public void testOrderDetailDto_AllArgsConstructor_ShouldSetValuesCorrectly() {
        ProductDto productDto = new ProductDto(1, "Test Product", "This is a test product description", 10.0, 1.5,
                null);
        OrderDetailDto orderDetailDto = new OrderDetailDto(1, productDto, 2, 20.0);

        assertAll("orderDetailDto",
                () -> assertEquals(1, orderDetailDto.getId()),
                () -> assertEquals(productDto, orderDetailDto.getProduct()),
                () -> assertEquals(2, orderDetailDto.getQuantity()),
                () -> assertEquals(20.0, orderDetailDto.getAmount()));
    }

    @Test
    public void testOrderDetailDto_SettersAndGetters_ShouldWorkAsExpected() {
        OrderDetailDto orderDetailDto = new OrderDetailDto();

        orderDetailDto.setId(2);
        ProductDto productDto = new ProductDto(2, "Another Test Product", "This is another test product description",
                15.0, 2.0, null);
        orderDetailDto.setProduct(productDto);
        orderDetailDto.setQuantity(3);
        orderDetailDto.setAmount(45.0);

        assertAll("orderDetailDto",
                () -> assertEquals(2, orderDetailDto.getId()),
                () -> assertEquals(productDto, orderDetailDto.getProduct()),
                () -> assertEquals(3, orderDetailDto.getQuantity()),
                () -> assertEquals(45.0, orderDetailDto.getAmount()));
    }
}