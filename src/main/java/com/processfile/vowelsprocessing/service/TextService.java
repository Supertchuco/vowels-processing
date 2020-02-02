package com.processfile.vowelsprocessing.service;

import com.processfile.vowelsprocessing.dto.WordGrouped;
import com.processfile.vowelsprocessing.dto.WordInfoDto;
import com.processfile.vowelsprocessing.enumerator.ErrorMessages;
import com.processfile.vowelsprocessing.enumerator.Vowels;
import com.processfile.vowelsprocessing.exception.BuildInfoDtoException;
import com.processfile.vowelsprocessing.exception.CalculateAverageException;
import com.processfile.vowelsprocessing.exception.CreateWordGroupedException;
import com.processfile.vowelsprocessing.exception.UpdateWordGroupedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static java.util.Locale.ENGLISH;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.join;

@Slf4j
@Service
public class TextService {

    private List<String> splitLineInWordsList(final String line) {
        log.info("Split line in words");
        return Arrays.asList(line.split("\\W+"));
    }

    private int countCharMatches(final String word, final char vowel) {
        return StringUtils.countMatches(word.toLowerCase(ENGLISH), vowel);
    }

    private WordInfoDto buildWordInfoDtoPerVowel(final WordInfoDto wordInfoDto, final char vowel) {
        log.info("Build word info Dto");
        final int count = countCharMatches(wordInfoDto.getWord(), vowel);
        if (count > 0) {
            wordInfoDto.getVowels().append(vowel);
            wordInfoDto.setTotalVowels(wordInfoDto.getTotalVowels() + count);

        }

        return wordInfoDto;
    }

    private WordInfoDto buildWordInfoDto(final String word) {
        log.info("Build word info dto");
        try {
            WordInfoDto wordInfoDto = new WordInfoDto(word);
            for (Vowels vowel : Vowels.values()) {
                wordInfoDto = buildWordInfoDtoPerVowel(wordInfoDto, vowel.getVowel());
            }
            return wordInfoDto;
        } catch (Exception exception) {
            log.error(ErrorMessages.ERROR_BUILD_WORD_INFO_DTO.getMessage(), exception);
            throw new BuildInfoDtoException(ErrorMessages.ERROR_BUILD_WORD_INFO_DTO.getMessage());
        }
    }

    private WordGrouped createWordGrouped(final String word) {
        log.info("Create word grouped object");
        final WordInfoDto wordInfoDto = buildWordInfoDto(word);
        try {
            return new WordGrouped(word.length(), join(wordInfoDto.getVowels().toString().toCharArray(), ','),
                    BigDecimal.valueOf(wordInfoDto.getTotalVowels()).setScale(1), 1, wordInfoDto.getTotalVowels());
        } catch (Exception exception) {
            log.error(ErrorMessages.ERROR_CREATE_WORD_GROUPED.getMessage(), exception);
            throw new CreateWordGroupedException(ErrorMessages.ERROR_CREATE_WORD_GROUPED.getMessage());
        }
    }

    private WordGrouped updateWordGrouped(final WordGrouped wordGrouped, final String word) {
        log.info("Update word grouped object");
        final WordInfoDto wordInfoDto = buildWordInfoDto(word);
        try {
            wordGrouped.setNumberWords(wordGrouped.getNumberWords() + 1);
            wordGrouped.setTotalVowels(wordGrouped.getTotalVowels() + wordInfoDto.getTotalVowels());
            wordGrouped.setAverage(calculateAverage(wordGrouped.getTotalVowels(), wordGrouped.getNumberWords()));
            return wordGrouped;
        } catch (CalculateAverageException calculateAverageException) {
            throw calculateAverageException;
        } catch (Exception exception) {
            log.error(ErrorMessages.ERROR_UPDATE_WORD_GROUPED.getMessage(), exception);
            throw new UpdateWordGroupedException(ErrorMessages.ERROR_UPDATE_WORD_GROUPED.getMessage());
        }
    }

    private BigDecimal calculateAverage(final int totalVowels, final int totalWords) {
        try {
            log.info("Calculate average");
            return BigDecimal.valueOf(totalVowels).divide(BigDecimal.valueOf(totalWords), 1, RoundingMode.HALF_EVEN);
        } catch (Exception exception) {
            log.error(ErrorMessages.ERROR_CALCULATE_AVERAGE.getMessage(), exception);
            throw new CalculateAverageException(ErrorMessages.ERROR_CALCULATE_AVERAGE.getMessage());
        }
    }


    private Map<String, WordGrouped> buildWordGroupedMap(final List<String> words) {
        log.info("Build word grouped map");
        final Map<String, WordGrouped> wordsMap = new HashMap<String, WordGrouped>();
        WordGrouped wordGrouped;
        for (String word : words) {
            wordGrouped = wordsMap.get(buildMapKey(word));
            wordGrouped = (isNull(wordGrouped)) ? createWordGrouped(word) : updateWordGrouped(wordGrouped, word);
            wordsMap.put(wordGrouped.getKey(), wordGrouped);
        }
        return wordsMap;
    }

    private String buildMapKey(final String word) {
        log.info("Build map key");
        WordInfoDto wordInfoDto = buildWordInfoDto(word);
        return join(wordInfoDto.getVowels().toString().toCharArray(), ',').concat(Integer.toString(wordInfoDto.getWord().length()));
    }

    private List<String> buildWordsList(final List<String> lines) {
        final List<String> words = new ArrayList<>();
        String[] wordsArray = null;
        for (String line : lines) {
            wordsArray = line.split("\\W+");
            words.addAll(Arrays.asList(wordsArray));
        }
        return words;
    }

    public Map<String, WordGrouped> processLines(final List<String> lines) {
        log.info("Process file started");
        if (CollectionUtils.isEmpty(lines)) {
            log.info("File is empty");
            return new HashMap<String, WordGrouped>();
        }
        final List<String> words = buildWordsList(lines);
        Map<String, WordGrouped> WordGrouped = buildWordGroupedMap(words);
        return WordGrouped;
    }

}
