package com.fsse2305.project_backend.service.impl;

import com.fsse2305.project_backend.data.product.domainObject.ProductDetailData;
import com.fsse2305.project_backend.data.product.entity.ProductEntity;
import com.fsse2305.project_backend.exception.product.ProductNotFoundException;
import com.fsse2305.project_backend.repository.ProductRepository;
import com.fsse2305.project_backend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDetailData> getAllProducts() {
        List<ProductDetailData> productDetailDataList = new ArrayList<>();
        for (ProductEntity entity : productRepository.findAll()) {
            productDetailDataList.add(new ProductDetailData(entity));
        }
        return productDetailDataList;
    }

    @Override
    public ProductDetailData getProductByPid(Integer pid) {
        try {
            return new ProductDetailData((getProductEntityByPid(pid)));
        } catch (ProductNotFoundException ex){
            logger.warn(("GetProductByPid failed: Product not found" + pid));
            throw ex;
        }
    }

    @Override
    public ProductEntity getProductEntityByPid(Integer pid) {
//        Optional<ProductEntity> optionalEntity = productRepository.findById(pid);
//        if (optionalEntity.isEmpty()) {
//            throw new ProductNotFoundException();
//        }
//        return optionalEntity.get();
        return productRepository.findById(pid).orElseThrow(ProductNotFoundException::new);
    }
}
