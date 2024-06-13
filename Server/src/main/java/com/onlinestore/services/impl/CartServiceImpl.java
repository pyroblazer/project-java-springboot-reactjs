package com.onlinestore.services.impl;

import com.onlinestore.entities.*;
import com.onlinestore.payload.*;
import com.onlinestore.repositories.CartDetailsRepo;
import com.onlinestore.repositories.CartRepo;
import com.onlinestore.repositories.ProductRepo;
import com.onlinestore.repositories.UserRepo;
import com.onlinestore.services.CartDetailsService;
import com.onlinestore.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.onlinestore.utils.MiscUtils.decompressBytes;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartDetailsRepo cartDetailsRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartDetailsService cartDetailsService;

    @Override
    public CartDto addProductToCart(CartHelp cartHelp) {

        int productId = cartHelp.getProductId();
        int quantity = cartHelp.getQuantity();
        String userEmail = cartHelp.getUserEmail();
        double total = 0;
        AtomicReference<Double> totalAmount = new AtomicReference<>(0.0);

        User user = this.userRepo.findByEmail(userEmail);

        Product product = this.productRepo.findById(productId).orElseThrow();

        Cart cart = user.getCart();

        if (cart == null) {
            Cart cart1 = new Cart();
            cart.setUser(user);

            double totalAmount2 = 0;

            CartDetails cartDetails1 = new CartDetails();
            cartDetails1.setQuantity(quantity);
            cartDetails1.setProduct(product);
            cartDetails1.setAmount((double) (product.getPrice() * quantity));
            totalAmount2 = cartDetails1.getAmount();

            List<CartDetails> pro = cart.getCartDetails();
            pro.add(cartDetails1);
            cart1.setCartDetails(pro);
            cart1.setTotalAmount(totalAmount2);
            cartDetails1.setCart(cart1);

            CartDto map = this.modelMapper.map(cart1, CartDto.class);
            List<CartDetailDto> cartDetails2 = map.getCartDetails();

            for (CartDetailDto i : cartDetails2) {
                ProductDto p = i.getProduct();
                p.setImg(decompressBytes(p.getImg()));
            }
            map.setCartDetails(cartDetails2);
            return map;
        }

        // create cart detail

        int cartId = cart.getId();
        CartDetails cartDetails = this.cartDetailsRepo.findByProductIdAndCartId(productId, cartId);

        if (cartDetails == null) {
            cartDetails = new CartDetails();
        }
        if (quantity == 0) {
            quantity = cartDetails.getQuantity() + 1; // Add to cart button-like function
        }

        cartDetails.setProduct(product);
        cartDetails.setQuantity(quantity);
        cartDetails.setAmount((double) (product.getPrice() * quantity));

        cartDetails.setCart(cart);

        List<CartDetails> list = cart.getCartDetails();

        AtomicReference<Boolean> flag = new AtomicReference<>(false);

        int finalQuantity = quantity;
        List<CartDetails> newProduct = list.stream().map((i) -> {
            if (i.getProduct().getId() == productId) {
                i.setQuantity(finalQuantity);
                i.setAmount((double) (i.getQuantity() * product.getPrice()));
                flag.set(true);
            }
            totalAmount.set(totalP(i.getAmount(), totalAmount.get()));

            return i;
        }).toList();

        if (flag.get()) {
            list.clear();
            list.addAll(newProduct);

        } else {

            cartDetails.setCart(cart);
            totalAmount.set(totalAmount.get() + (double) (quantity * product.getPrice()));
            list.add(cartDetails);

        }
        cart.setCartDetails(list);
        cart.setTotalAmount(totalAmount.get());
        System.out.println(cart.getTotalAmount());
        Cart save = this.cartRepo.save(cart);

        CartDto map = this.modelMapper.map(save, CartDto.class);
        List<CartDetailDto> cartDetails1 = map.getCartDetails();

        for (CartDetailDto i : cartDetails1) {
            ProductDto p = i.getProduct();
            p.setImg(decompressBytes(p.getImg()));
        }
        map.setCartDetails(cartDetails1);
        return map;
    }

    @Override
    public CartDto getCart(String userEmail) {
        User user = this.userRepo.findByEmail(userEmail);
        Cart byUser = this.cartRepo.findByUser(user);

        byUser.getCartDetails().size();

        CartDto map = this.modelMapper.map(byUser, CartDto.class);
        List<CartDetailDto> cartDetails1 = map.getCartDetails();

        for (CartDetailDto i : cartDetails1) {
            ProductDto p = i.getProduct();
            p.setImg(decompressBytes(p.getImg()));
        }
        map.setCartDetails(cartDetails1);
        return map;
    }

    @Override
    @Transactional
    public void removeCartDetailFromCart(Integer cartDetailId, String userEmail) {
        User user = this.userRepo.findByEmail(userEmail);
        Cart cart = this.cartRepo.findByUser(user);

        CartDetails cartDetail = this.cartDetailsRepo.findById(cartDetailId).orElseThrow();
        double amount = cartDetail.getAmount();
        cart.setTotalAmount(cart.getTotalAmount() - amount);

        cart.getCartDetails().remove(cartDetail);

        this.cartRepo.save(cart);
        this.cartDetailsRepo.delete(cartDetail);
    }

    @Override
    public CartDto updateCart(CartDto cartDto) throws Exception {
        Cart existingCart = cartRepo.findById(cartDto.getId())
                .orElseThrow(() -> new Exception("Cart with ID " + cartDto.getId() + " not found"));

        existingCart.setTotalAmount(cartDto.getTotalAmount());

        List<CartDetails> cartDetailsList = cartDto.getCartDetails().stream()
                .map(cartDetailDto -> modelMapper.map(cartDetailDto, CartDetails.class))
                .collect(Collectors.toList());
        existingCart.setCartDetails(cartDetailsList);

        existingCart.setUser(modelMapper.map(cartDto.getUser(), User.class));

        cartRepo.save(existingCart);

        return modelMapper.map(existingCart, CartDto.class);
    }

    @Override
    public void emptyCart(Integer cartId) throws Exception {
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new Exception("Cart with ID " + cartId + " not found"));
        cartDetailsService.deleteAllCartDetailsByCartId(cartId);
        cart.setCartDetails(new ArrayList<>());
        cart.setTotalAmount(0.00);
        cartRepo.save(cart);
    }

    public Product changeImg(Product product) {
        product.setImg(decompressBytes(product.getImg()));
        return product;
    }

    public double totalP(double t1, double total) {
        return total + t1;
    }
}
