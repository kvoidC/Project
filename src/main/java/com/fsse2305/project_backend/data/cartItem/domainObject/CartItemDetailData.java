package com.fsse2305.project_backend.data.cartItem.domainObject;

import com.fsse2305.project_backend.data.cartItem.entity.CartItemEntity;
import com.fsse2305.project_backend.data.product.domainObject.ProductDetailData;
import com.fsse2305.project_backend.data.user.domainObject.UserDetailData;


public class CartItemDetailData {
    private Integer cid;
    private ProductDetailData product;
    private UserDetailData user;
    private Integer quantity;

    public CartItemDetailData(CartItemEntity entity) {
        this.cid = entity.getCid();
        this.product = new ProductDetailData(entity.getProduct());
        this.user = new UserDetailData(entity.getUser());
        this.quantity = entity.getQuantity();

    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public ProductDetailData getProduct() {
        return product;
    }

    public void setProduct(ProductDetailData product) {
        this.product = product;
    }

    public UserDetailData getUser() {
        return user;
    }

    public void setUser(UserDetailData user) {
        this.user = user;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
