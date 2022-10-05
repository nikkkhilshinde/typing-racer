package com.personal.typingracer.model.thirdparty;

import lombok.Data;

import java.util.List;

/**
 * @author nikhilshinde on 05/10/22
 */
@Data
public class ContentResponse {
    private List<String> facts;
    private boolean success;
}
