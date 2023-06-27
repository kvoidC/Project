package com.fsse2305.project_backend.repository;

import com.fsse2305.project_backend.data.cartItem.entity.CartItemEntity;
import com.fsse2305.project_backend.data.product.entity.ProductEntity;
import com.fsse2305.project_backend.data.user.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends CrudRepository<CartItemEntity, Integer> {

//    Optional<CartItemEntity> findByUserAndProduct(UserEntityerEntity user, ProductEntity productEntity);

        @Query(value = "SELECT * FROM cart_item WHERE uid=?1 AND pid=?2", nativeQuery = true)
        Optional<CartItemEntity> findByUserUidAndProductPid(Integer uid, Integer pid);

    List<CartItemEntity> findAllByUser(UserEntity user);

    int deleteByUserUidAndProductPid(Integer uid, Integer pid);

//    int deleteByUserAndProduct(UserEntity user, ProductEntity productEntity);
}
