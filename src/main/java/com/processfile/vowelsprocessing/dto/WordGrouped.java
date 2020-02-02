package com.processfile.vowelsprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordGrouped implements Serializable {

    private int wordSize;

    private String vowels;

    private BigDecimal average;

    private int numberWords;

    private int totalVowels;

    public String getKey() {
        return (isNotBlank(vowels)) ? vowels.concat(Integer.toString(wordSize)) : EMPTY;
    }

}
