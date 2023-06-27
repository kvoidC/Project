package com.fsse2305.project_backend.service;


import com.fsse2305.project_backend.data.product.domainObject.ProductDetailData;
import com.fsse2305.project_backend.data.product.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    List<ProductDetailData> getAllProducts();

    ProductDetailData getProductByPid(Integer pid);

    ProductEntity getProductEntityByPid(Integer pid);
}