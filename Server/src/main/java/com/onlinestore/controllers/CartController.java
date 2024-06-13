package com.onlinestore.controllers;


import com.onlinestore.payload.ApiResponse;
import com.onlinestore.payload.CartDto;
import com.onlinestore.payload.CartHelp;
import com.onlinestore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public ResponseEntity<CartDto> GetCart(Principal principal){
        String userEmail = principal.getName();
        CartDto cartDto = this.cartService.getCart(userEmail);

        return new ResponseEntity<>(cartDto, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/addproduct")
    public ResponseEntity<CartDto> addProduct(@RequestBody CartHelp cartHelp, Principal principal){
        String userEmail = principal.getName();
        cartHelp.setUserEmail(userEmail);
        CartDto cartDto = this.cartService.addProductToCart(cartHelp);
        return new ResponseEntity<>(cartDto, HttpStatusCode.valueOf(200));
    }


    @DeleteMapping("/detail/{cartDetailId}")
    public ResponseEntity<ApiResponse> DeleteItem(Principal principal, @PathVariable String cartDetailId){
        Integer intCartDetailId = Integer.parseInt(cartDetailId);
        String userEmail = principal.getName();
        this.cartService.removeCartDetailFromCart(intCartDetailId, userEmail);

        return new ResponseEntity<>(new ApiResponse("remove"), HttpStatusCode.valueOf(200));
    }
}
