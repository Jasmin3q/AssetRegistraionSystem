package com.apress.ravi.chapter2.Exception;

import com.apress.ravi.chapter2.dto.AssetsDTO;

public class CustomErrorType extends AssetsDTO {

	private String errorMessage;
	 
    public CustomErrorType(final String errorMessage){
        this.errorMessage = errorMessage;
    }
 
    public String getErrorMessage() {
        return errorMessage;
    }
}
