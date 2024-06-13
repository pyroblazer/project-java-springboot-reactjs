package com.onlinestore.services.impl;

import com.onlinestore.entities.Cart;
import com.onlinestore.entities.CartDetails;
import com.onlinestore.entities.Product;
import com.onlinestore.entities.User;
import com.onlinestore.payload.*;
import com.onlinestore.repositories.CartDetailsRepo;
import com.onlinestore.repositories.CartRepo;
import com.onlinestore.repositories.ProductRepo;
import com.onlinestore.repositories.UserRepo;
import com.onlinestore.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

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

    @Override
    public CartDto CreateCart(CartHelp cartHelp) {
        return null;
    }

    @Override
    public CartDto addProductToCart(CartHelp cartHelp) {

        int productId = cartHelp.getProductId();
        int quantity = cartHelp.getQuantity();
        String userEmail = cartHelp.getUserEmail();
        int total = 0;
        AtomicReference<Integer> totalAmount = new AtomicReference<>(0);

        User user = this.userRepo.findByEmail(userEmail);

        Product product = this.productRepo.findById(productId).orElseThrow();

        // create cart detail

        CartDetails cartDetails = new CartDetails();
        cartDetails.setProducts(product);
        cartDetails.setQuantity(quantity);
        cartDetails.setAmount((int) (product.getPrice() * quantity));

        Cart cart = user.getCart();

        if (cart == null) {
            Cart cart1 = new Cart();
            cart.setUser(user);

            int totalAmount2 = 0;

            CartDetails cartDetails1 = new CartDetails();
            cartDetails1.setQuantity(quantity);
            cartDetails1.setProducts(product);
            cartDetails1.setAmount((int) (product.getPrice() * quantity));
            totalAmount2 = cartDetails1.getAmount();

            List<CartDetails> pro = cart.getCartDetails();
            pro.add(cartDetails1);
            cart1.setCartDetails(pro);
            cart1.setTotalAmount(totalAmount2);
            cartDetails1.setCart(cart1);

            CartDto map = this.modelMapper.map(cart1, CartDto.class);
            List<CartDetailDto> cartDetails2 = map.getCartDetails();

            for (CartDetailDto i : cartDetails2) {
                ProductDto p = i.getProducts();
                p.setImg(decompressBytes(p.getImg()));
            }
            map.setCartDetails(cartDetails2);
            return map;

        }

        cartDetails.setCart(cart);

        List<CartDetails> list = cart.getCartDetails();

        AtomicReference<Boolean> flag = new AtomicReference<>(false);

        List<CartDetails> newProduct = list.stream().map((i) -> {
            if (i.getProducts().getProductId() == productId) {
                i.setQuantity(quantity);
                i.setAmount((int) (i.getQuantity() * product.getPrice()));
                flag.set(true);
            }
            totalAmount.set(totalP(i.getAmount(), totalAmount.get()));

            return i;
        }).collect(Collectors.toList());

        if (flag.get()) {
            list.clear();
            list.addAll(newProduct);

        } else {

            cartDetails.setCart(cart);
            totalAmount.set(totalAmount.get() + (int) (quantity * product.getPrice()));
            list.add(cartDetails);

        }
        cart.setCartDetails(list);
        cart.setTotalAmount(totalAmount.get());
        System.out.println(cart.getTotalAmount());
        Cart save = this.cartRepo.save(cart);

        CartDto map = this.modelMapper.map(save, CartDto.class);
        List<CartDetailDto> cartDetails1 = map.getCartDetails();

        for (CartDetailDto i : cartDetails1) {
            ProductDto p = i.getProducts();
            p.setImg(decompressBytes(p.getImg()));
        }
        map.setCartDetails(cartDetails1);
        return map;
    }

    @Override
    public CartDto GetCart(String userEmail) {
        User user = this.userRepo.findByEmail(userEmail);
        Cart byUser = this.cartRepo.findByUser(user);

        // img decompressBytes
        CartDto map = this.modelMapper.map(byUser, CartDto.class);
        List<CartDetailDto> cartDetails1 = map.getCartDetails();

        for (CartDetailDto i : cartDetails1) {
            ProductDto p = i.getProducts();
            p.setImg(decompressBytes(p.getImg()));
        }
        map.setCartDetails(cartDetails1);
        return map;
    }

    @Override
    public void RemoveById(Integer ProductId, String userEmail) {
        User user = this.userRepo.findByEmail(userEmail);

        Product product = this.productRepo.findById(ProductId).orElseThrow();
        Cart cart = this.cartRepo.findByUser(user);

        CartDetails byProductsAndCart = this.cartDetailsRepo.findByProductsAndCart(product, cart);
        int amount = byProductsAndCart.getAmount();
        cart.setTotalAmount(cart.getTotalAmount() - amount);
        this.cartRepo.save(cart);

        this.cartDetailsRepo.delete(byProductsAndCart);

    }

    public Product changeImg(Product product) {

        product.setImg(decompressBytes(product.getImg()));

        System.out.println("hello");
        return product;
    }

    public int totalP(int t1, int total) {
        return total + t1;
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
