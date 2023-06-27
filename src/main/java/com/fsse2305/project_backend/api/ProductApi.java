package com.fsse2305.project_backend.api;

import com.fsse2305.project_backend.data.product.domainObject.ProductDetailData;
import com.fsse2305.project_backend.data.product.dto.GetAllProductResponseDto;
import com.fsse2305.project_backend.data.product.dto.ProductDetailResponseDto;
import com.fsse2305.project_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public/product")
public class ProductApi {
    private final ProductService productService;

    @Autowired
    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<GetAllProductResponseDto> getAllProducts() {
        List<GetAllProductResponseDto> responseDtoList = new ArrayList<>();

        for (ProductDetailData data : productService.getAllProducts()) {
            responseDtoList.add(new GetAllProductResponseDto(data));
        }
        return responseDtoList;
    }

    @GetMapping("/{id}")
    public ProductDetailResponseDto getProductByPid(@PathVariable("id") Integer pid) {
        return new ProductDetailResponseDto(productService.getProductByPid(pid));
    }

}
