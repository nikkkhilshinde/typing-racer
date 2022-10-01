package com.personal.typingracer.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author nikhilshinde on 28/09/22
 */
@Getter
@AllArgsConstructor
public class BaseResponse<T> {
    private boolean success;
    private T data;
    @JsonInclude(NON_NULL)
    private String error;
}
