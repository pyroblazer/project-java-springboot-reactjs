package com.onlinestore.controllers;

import com.onlinestore.payload.ApiResponse;
import com.onlinestore.payload.CartDto;
import com.onlinestore.payload.CartHelp;
import com.onlinestore.services.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @Mock
    private Principal principal;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCart() {
        String userEmail = "user@example.com";
        when(principal.getName()).thenReturn(userEmail);
        CartDto cartDto = new CartDto();
        when(cartService.getCart(userEmail)).thenReturn(cartDto);

        ResponseEntity<CartDto> response = cartController.GetCart(principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartDto, response.getBody());
        verify(cartService, times(1)).getCart(userEmail);
    }

    @Test
    public void testAddProductToCart() {
        String userEmail = "user@example.com";
        when(principal.getName()).thenReturn(userEmail);
        CartHelp cartHelp = new CartHelp(userEmail, 1, 2);
        CartDto cartDto = new CartDto();
        when(cartService.addProductToCart(any(CartHelp.class))).thenReturn(cartDto);

        ResponseEntity<CartDto> response = cartController.addProduct(cartHelp, principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartDto, response.getBody());
        verify(cartService, times(1)).addProductToCart(cartHelp);
    }

    @Test
    public void testDeleteItem() {
        String userEmail = "user@example.com";
        String cartDetailId = "1";
        when(principal.getName()).thenReturn(userEmail);

        ResponseEntity<ApiResponse> response = cartController.DeleteItem(principal, cartDetailId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("remove", response.getBody().getMessage());
        verify(cartService, times(1)).removeCartDetailFromCart(Integer.parseInt(cartDetailId), userEmail);
    }
}