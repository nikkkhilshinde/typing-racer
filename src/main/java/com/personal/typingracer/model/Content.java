package com.personal.typingracer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author nikhilshinde on 05/10/22
 */
@AllArgsConstructor
@Data
public class Content{
    private List<WordDetails> content;
}
