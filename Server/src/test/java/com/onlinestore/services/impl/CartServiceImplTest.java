package com.onlinestore.services.impl;

import com.onlinestore.entities.*;
import com.onlinestore.payload.*;
import com.onlinestore.repositories.*;
import com.onlinestore.services.CartDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private CartRepo cartRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private CartDetailsRepo cartDetailsRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CartDetailsService cartDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProductToCart_NewCart() {
        String userEmail = "user@example.com";
        int productId = 1;
        int quantity = 2;
        User user = new User();
        user.setEmail(userEmail);

        Product product = new Product();
        product.setId(productId);
        product.setPrice(10.0);

        CartHelp cartHelp = new CartHelp(userEmail, productId, quantity);

        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);

        when(userRepo.findByEmail(userEmail)).thenReturn(user);
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));

        when(cartRepo.save(any(Cart.class))).thenReturn(cart);

        CartDto expectedCartDto = new CartDto();
        when(modelMapper.map(any(Cart.class), eq(CartDto.class))).thenReturn(expectedCartDto);

        CartDto result = cartService.addProductToCart(cartHelp);

        assertEquals(expectedCartDto, result);
        verify(cartRepo, times(1)).save(any(Cart.class));
    }

    @Test
    public void testGetCart() {
        String userEmail = "user@example.com";
        User user = new User();
        user.setEmail(userEmail);

        Cart cart = new Cart();
        cart.setUser(user);

        when(userRepo.findByEmail(userEmail)).thenReturn(user);
        when(cartRepo.findByUser(user)).thenReturn(cart);

        CartDto expectedCartDto = new CartDto();
        when(modelMapper.map(any(Cart.class), eq(CartDto.class))).thenReturn(expectedCartDto);

        CartDto result = cartService.getCart(userEmail);

        assertEquals(expectedCartDto, result);
        verify(userRepo, times(1)).findByEmail(userEmail);
        verify(cartRepo, times(1)).findByUser(user);
    }

    @Test
    public void testRemoveCartDetailFromCart() {
        String userEmail = "user@example.com";
        int cartDetailId = 1;

        User user = new User();
        user.setEmail(userEmail);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalAmount(100.0);

        Product product = new Product();
        product.setId(1);

        CartDetails cartDetails = new CartDetails();
        cartDetails.setId(cartDetailId);
        cartDetails.setProduct(product);
        cartDetails.setCart(cart);
        cartDetails.setAmount(50.0);

        List<CartDetails> cartDetailsList = new ArrayList<>();
        cartDetailsList.add(cartDetails);
        cart.setCartDetails(cartDetailsList);

        when(userRepo.findByEmail(userEmail)).thenReturn(user);
        when(cartRepo.findByUser(user)).thenReturn(cart);
        when(cartDetailsRepo.findById(cartDetailId)).thenReturn(Optional.of(cartDetails));

        cartService.removeCartDetailFromCart(cartDetailId, userEmail);

        assertEquals(50.0, cart.getTotalAmount());
        assertFalse(cart.getCartDetails().contains(cartDetails));
        verify(cartRepo, times(1)).save(cart);
        verify(cartDetailsRepo, times(1)).delete(cartDetails);
    }

    @Test
    public void testEmptyCart() throws Exception {
        int cartId = 1;
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setTotalAmount(100.0);
        cart.setCartDetails(new ArrayList<>());

        when(cartRepo.findById(cartId)).thenReturn(Optional.of(cart));

        cartService.emptyCart(cartId);

        assertEquals(0.0, cart.getTotalAmount());
        assertEquals(0, cart.getCartDetails().size());
        verify(cartDetailsService, times(1)).deleteAllCartDetailsByCartId(cartId);
        verify(cartRepo, times(1)).save(cart);
    }

    @Test
    public void testUpdateCart() throws Exception {
        int cartId = 1;
        CartDto cartDto = new CartDto();
        cartDto.setId(cartId);
        cartDto.setTotalAmount(150.0);

        UserDto userDto = new UserDto();
        userDto.setId(1);
        cartDto.setUser(userDto);

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();
        CartDetailDto cartDetailDto = new CartDetailDto();
        cartDetailDtoList.add(cartDetailDto);
        cartDto.setCartDetails(cartDetailDtoList);

        Cart existingCart = new Cart();
        existingCart.setId(cartId);

        when(cartRepo.findById(cartId)).thenReturn(Optional.of(existingCart));
        when(modelMapper.map(any(CartDetailDto.class), eq(CartDetails.class))).thenReturn(new CartDetails());
        when(modelMapper.map(any(UserDto.class), eq(User.class))).thenReturn(new User());

        Cart updatedCart = new Cart();
        updatedCart.setId(cartId);
        updatedCart.setTotalAmount(150.0);

        when(cartRepo.save(existingCart)).thenReturn(updatedCart);
        when(modelMapper.map(any(Cart.class), eq(CartDto.class))).thenReturn(cartDto);

        CartDto result = cartService.updateCart(cartDto);

        assertEquals(cartDto, result);
        verify(cartRepo, times(1)).save(existingCart);
    }

//    @Test
//    public void testCreateCart() {
//        String userEmail = "user@example.com";
//        int productId = 1;
//        int quantity = 2;
//        User user = new User();
//        user.setEmail(userEmail);
//
//        Product product = new Product();
//        product.setId(productId);
//        product.setPrice(10.0);
//
//        CartHelp cartHelp = new CartHelp(userEmail, productId, quantity);
//
//        when(userRepo.findByEmail(userEmail)).thenReturn(user);
//        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
//
//        Cart cart = new Cart();
//        cart.setUser(user);
//        user.setCart(cart);
//
//        when(cartRepo.save(any(Cart.class))).thenReturn(cart);
//
//        CartDto expectedCartDto = new CartDto();
//        when(modelMapper.map(any(Cart.class), eq(CartDto.class))).thenReturn(expectedCartDto);
//
//        CartDto result = cartService.createCart(cartHelp);
//
//        assertEquals(expectedCartDto, result);
//        verify(cartRepo, times(1)).save(any(Cart.class));
//    }
}