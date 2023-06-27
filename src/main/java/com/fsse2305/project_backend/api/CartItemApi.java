package com.fsse2305.project_backend.api;

import com.fsse2305.project_backend.data.cartItem.domainObject.CartItemDetailData;
import com.fsse2305.project_backend.data.cartItem.domainObject.CartItemResponseDto;
import com.fsse2305.project_backend.data.cartItem.dto.SuccessResponseDto;
import com.fsse2305.project_backend.data.user.domainObject.FirebaseUserData;
import com.fsse2305.project_backend.service.CartItemService;
import com.fsse2305.project_backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartItemApi {
    private final CartItemService cartItemService;

    @Autowired
    public CartItemApi(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PutMapping("/{pid}/{quantity}")
    public SuccessResponseDto putCartItem(JwtAuthenticationToken jwtToken, @PathVariable Integer pid, @PathVariable Integer quantity) {
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwtToken);
            if (cartItemService.putCartItem(firebaseUserData, pid, quantity)) {
                return new SuccessResponseDto();
            }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    public List<CartItemResponseDto> getUserCartItem(JwtAuthenticationToken jwtToken) {
        List<CartItemResponseDto> responseDtoList = new ArrayList<>();

        for (CartItemDetailData data : cartItemService.getUserCartItem(JwtUtil.getFirebaseUserData(jwtToken))) {
            responseDtoList.add(new CartItemResponseDto(data));
        }
        return responseDtoList;
    }

    @PatchMapping("/{pid}/{quantity}")
    public CartItemResponseDto patchCartItemQuantity(JwtAuthenticationToken jwtToken, @PathVariable Integer pid, @PathVariable Integer quantity) {
        return new CartItemResponseDto(cartItemService.patchCartItemQuantity(
                JwtUtil.getFirebaseUserData(jwtToken), pid, quantity
        ));
    }

    @DeleteMapping("/{pid}")
    public SuccessResponseDto deleteCartItem(JwtAuthenticationToken jwtToken, @PathVariable Integer pid) {
        cartItemService.deleteCartItem(JwtUtil.getFirebaseUserData(jwtToken), pid);
        return new SuccessResponseDto();
    }
}
