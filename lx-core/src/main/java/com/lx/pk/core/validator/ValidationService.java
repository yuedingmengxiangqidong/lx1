package com.lx.pk.core.validator;


import com.lx.pk.core.exception.DubboServiceException;

public interface ValidationService {

    ValidationResult validate(Object param, boolean fastMode);

    void validate(Object param) throws DubboServiceException, DubboServiceException;
}
