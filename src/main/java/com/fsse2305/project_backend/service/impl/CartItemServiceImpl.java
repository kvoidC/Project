package com.fsse2305.project_backend.service.impl;

import com.fsse2305.project_backend.data.cartItem.domainObject.CartItemDetailData;
import com.fsse2305.project_backend.data.cartItem.entity.CartItemEntity;
import com.fsse2305.project_backend.data.product.entity.ProductEntity;
import com.fsse2305.project_backend.data.user.domainObject.FirebaseUserData;
import com.fsse2305.project_backend.data.user.entity.UserEntity;
import com.fsse2305.project_backend.exception.cartItem.CartItemException;
import com.fsse2305.project_backend.exception.cartItem.NoStockException;
import com.fsse2305.project_backend.exception.product.ProductNotFoundException;
import com.fsse2305.project_backend.repository.CartItemRepository;
import com.fsse2305.project_backend.service.CartItemService;
import com.fsse2305.project_backend.service.ProductService;
import com.fsse2305.project_backend.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    private Logger logger = LoggerFactory.getLogger(CartItemServiceImpl.class);

    private final UserService userService;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemServiceImpl(UserService userService, ProductService productService, CartItemRepository cartItemRepository) {
        this.userService = userService;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public boolean putCartItem(FirebaseUserData firebaseUserData, Integer pid, Integer quantity){
        try {
//            UserEntity userEntity =  userService.getEntityByFirebaseUserData(firebaseUserData);
            UserEntity userEntity =  getUserEntity(firebaseUserData);
            ProductEntity productEntity =  productService.getProductEntityByPid(pid);

//            Optional<CartItemEntity> optionalCartItemEntity = cartItemRepository.findByUserAndProduct(userEntity, productEntity);
            Optional<CartItemEntity> optionalCartItemEntity = cartItemRepository.findByUserUidAndProductPid(userEntity.getUid(), pid);


            if(optionalCartItemEntity.isEmpty()) {
                if (!checkHasStock(productEntity, quantity)) {
                    logger.warn("Put CartItem failed: Product not found" + pid);
                    throw new NoStockException();
                }
                CartItemEntity newCartItemEntity = new CartItemEntity(productEntity, userEntity, quantity);
                cartItemRepository.save(newCartItemEntity);
//                cartItemRepository.save(new CartItemEntity(productEntity, userEntity, quantity));
                return true;
            } else {
                CartItemEntity cartItemEntity = optionalCartItemEntity.get();
                if (!checkHasStock(productEntity, cartItemEntity.getQuantity() + quantity)) {
                    throw new NoStockException();
                }
                cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
                cartItemRepository.save(cartItemEntity);
                return true;
            }
//            check quantity > stock?
        } catch (ProductNotFoundException ex) {
            logger.warn("Put CartItem failed: Product not found" + pid);
            throw ex;
        }
    }

@Override
    public List<CartItemDetailData> getUserCartItem(FirebaseUserData firebaseUserData) {
//        UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
        UserEntity userEntity = getUserEntity(firebaseUserData);

        List<CartItemEntity> cartItemEntityList = cartItemRepository.findAllByUser(userEntity);

        List<CartItemDetailData> cartItemDetailDataList = new ArrayList<>();
        for (CartItemEntity cartItemEntity : cartItemEntityList) {
            cartItemDetailDataList.add(new CartItemDetailData(cartItemEntity));
        }
        return cartItemDetailDataList;
    }

@Override
    public CartItemDetailData patchCartItemQuantity(FirebaseUserData firebaseUserData, Integer pid, Integer quantity) {
        UserEntity userEntity = getUserEntity(firebaseUserData);

        CartItemEntity cartItemEntity = getEntityByUidAndPid(userEntity.getUid(), pid);

        if (quantity < 0) {
            throw new CartItemException();
        }

        if (!checkHasStock(cartItemEntity.getProduct(), quantity)) {
            throw new CartItemException();
        }

        if (quantity == 0) {
            cartItemRepository.deleteById(cartItemEntity.getCid());
            cartItemEntity.setQuantity(quantity);
            return new CartItemDetailData(cartItemEntity);
        }
    cartItemEntity.setQuantity(quantity);
    return new CartItemDetailData(cartItemRepository.save(cartItemEntity));
    }

    @Override
    @Transactional
    public boolean deleteCartItem(FirebaseUserData firebaseUserData, Integer pid) {
        UserEntity userEntity = getUserEntity(firebaseUserData);

        if(cartItemRepository.deleteByUserUidAndProductPid(userEntity.getUid(), pid) <= 0) {
            throw new CartItemException();
        }
        return true;
    }

    public CartItemEntity getEntityByUidAndPid(Integer uid, Integer pid) {
        Optional<CartItemEntity> optionalCartItemEntity = cartItemRepository.findByUserUidAndProductPid(uid, pid);
        return optionalCartItemEntity.orElseThrow(CartItemException::new);
    }

    public boolean checkHasStock(ProductEntity productEntity, Integer quantity) {
//        if (productEntity.getStock() < quantity) {
//            return false;
//        } else {
//            return true;
//        }
        return productEntity.getStock() >= quantity;
    }

    public UserEntity getUserEntity(FirebaseUserData firebaseUserData ) {
        return userService.getEntityByFirebaseUserData(firebaseUserData);
    }
}
