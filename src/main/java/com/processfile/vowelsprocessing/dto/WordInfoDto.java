package com.processfile.vowelsprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordInfoDto implements Serializable {

    private String word;

    private int totalVowels;

    private StringBuffer vowels;

    public WordInfoDto(final String word){
        this.word = word;
        this.totalVowels = 0;
        this.vowels = new StringBuffer();
    }

}
