package com.fsse2305.project_backend.data.cartItem.dto;

public class SuccessResponseDto {
    private String result;

    public SuccessResponseDto() {
        this.result = "Success";
    }

    public String getResult() {
        return result;
    }

    private void setResult(String result) {
        this.result = result;
    }
}
