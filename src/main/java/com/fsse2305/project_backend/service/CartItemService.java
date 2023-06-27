package com.fsse2305.project_backend.service;

import com.fsse2305.project_backend.data.cartItem.domainObject.CartItemDetailData;
import com.fsse2305.project_backend.data.user.domainObject.FirebaseUserData;

import java.util.List;

public interface CartItemService {
    boolean putCartItem(FirebaseUserData firebaseUserData, Integer pid, Integer quantity);

    List<CartItemDetailData> getUserCartItem(FirebaseUserData firebaseUserData);

    CartItemDetailData patchCartItemQuantity(FirebaseUserData firebaseUserData, Integer pid, Integer quantity);

    boolean deleteCartItem(FirebaseUserData firebaseUserData, Integer pid);
}
