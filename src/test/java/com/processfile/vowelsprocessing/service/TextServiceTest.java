package com.processfile.vowelsprocessing.service;

import com.processfile.vowelsprocessing.dto.WordGrouped;
import com.processfile.vowelsprocessing.dto.WordInfoDto;
import com.processfile.vowelsprocessing.enumerator.Vowels;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class TextServiceTest {

    private static final String COUNT_CHAR_MATCHES = "countCharMatches";

    @InjectMocks
    private TextService textService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSplitLineWithSuccess() {
        assertEquals("[Platon, made, bamboo, boats]", ReflectionTestUtils.invokeMethod(textService,
                "splitLineInWordsList", new Object[]{"Platon made bamboo boats."}).toString());
        assertEquals("[Platon, made, bamboo, boats, Goal, soccer]", ReflectionTestUtils.invokeMethod(textService,
                "splitLineInWordsList", new Object[]{"Platon made bamboo boats. Goal.soccer"}).toString());
    }

    @Test
    public void shouldCountMatchesWithSuccess() {
        assertEquals(java.util.Optional.of(2), java.util.Optional.of(ReflectionTestUtils.invokeMethod(textService, COUNT_CHAR_MATCHES, new Object[]{"bambooA", Vowels.A.getVowel()})));
        assertEquals(java.util.Optional.of(4), java.util.Optional.of(ReflectionTestUtils.invokeMethod(textService, COUNT_CHAR_MATCHES, new Object[]{"eaeeoEA", Vowels.E.getVowel()})));
        assertEquals(java.util.Optional.of(0), java.util.Optional.of(ReflectionTestUtils.invokeMethod(textService, COUNT_CHAR_MATCHES, new Object[]{"bambooA", Vowels.I.getVowel()})));
        assertEquals(java.util.Optional.of(3), java.util.Optional.of(ReflectionTestUtils.invokeMethod(textService, COUNT_CHAR_MATCHES, new Object[]{"baOmbooA", Vowels.O.getVowel()})));
        assertEquals(java.util.Optional.of(1), java.util.Optional.of(ReflectionTestUtils.invokeMethod(textService, COUNT_CHAR_MATCHES, new Object[]{"ubambooA", Vowels.U.getVowel()})));
    }

    @Test
    public void shouldCalculateAverageWithSuccess() {
        assertEquals("2.5", ReflectionTestUtils.invokeMethod(textService,
                "calculateAverage", new Object[]{5, 2}).toString());

        assertEquals("5.0", ReflectionTestUtils.invokeMethod(textService,
                "calculateAverage", new Object[]{10, 2}).toString());
    }

    @Test
    public void shouldBuildWordInfoDtoWithSuccess() {
        WordInfoDto wordInfoDto = ReflectionTestUtils.invokeMethod(textService, "buildWordInfoDtoPerVowel", new Object[]{new WordInfoDto("bamboIooa"), Vowels.A.getVowel()});
        assertEquals("bamboIooa", wordInfoDto.getWord());
        assertEquals(2, wordInfoDto.getTotalVowels());
        assertEquals("a", wordInfoDto.getVowels().toString());

        wordInfoDto = ReflectionTestUtils.invokeMethod(textService, "buildWordInfoDtoPerVowel", new Object[]{wordInfoDto, Vowels.O.getVowel()});
        assertEquals("bamboIooa", wordInfoDto.getWord());
        assertEquals(5, wordInfoDto.getTotalVowels());
        assertEquals("ao", wordInfoDto.getVowels().toString());

        wordInfoDto = ReflectionTestUtils.invokeMethod(textService, "buildWordInfoDtoPerVowel", new Object[]{wordInfoDto, Vowels.I.getVowel()});
        assertEquals("bamboIooa", wordInfoDto.getWord());
        assertEquals(6, wordInfoDto.getTotalVowels());
        assertEquals("aoi", wordInfoDto.getVowels().toString());
    }

    @Test
    public void shouldCreateWordGroupedWithSuccess() {
        WordGrouped wordGrouped = ReflectionTestUtils.invokeMethod(textService, "createWordGrouped", new Object[]{"ePElatoIn"});

        assertEquals(9, wordGrouped.getWordSize());
        assertEquals("a,e,i,o", wordGrouped.getVowels());
        assertEquals(1, wordGrouped.getNumberWords());
        assertEquals(5, wordGrouped.getTotalVowels());
        assertEquals(BigDecimal.valueOf(5).setScale(1), wordGrouped.getAverage());

        wordGrouped = ReflectionTestUtils.invokeMethod(textService, "createWordGrouped", new Object[]{"A"});
        assertEquals(1, wordGrouped.getWordSize());
        assertEquals("a", wordGrouped.getVowels());
        assertEquals(1, wordGrouped.getNumberWords());
        assertEquals(BigDecimal.valueOf(1).setScale(1), wordGrouped.getAverage());
    }

    @Test
    public void shouldUpdateWordGroupedWithSuccess() {
        WordGrouped wordGrouped = ReflectionTestUtils.invokeMethod(textService, "createWordGrouped", new Object[]{"bamboo"});
        wordGrouped = ReflectionTestUtils.invokeMethod(textService, "updateWordGrouped", new Object[]{wordGrouped, "platon"});
        assertEquals(6, wordGrouped.getWordSize());
        assertEquals("a,o", wordGrouped.getVowels());
        assertEquals(2, wordGrouped.getNumberWords());
        assertEquals(5, wordGrouped.getTotalVowels());
        assertEquals(BigDecimal.valueOf(2.5).setScale(1), wordGrouped.getAverage());
    }

    @Test
    public void shouldBuildWordGroupedMapWithSuccess() {
        Map<String, WordGrouped> wordGroupedMap = ReflectionTestUtils.invokeMethod(textService, "buildWordGroupedMap",
                new Object[]{Arrays.asList("Platon", "made", "bamboo", "boats")});
        assertEquals(3, wordGroupedMap.size());

        assertEquals(6, wordGroupedMap.get("a,o6").getWordSize());
        assertEquals("a,o", wordGroupedMap.get("a,o6").getVowels());
        assertEquals(BigDecimal.valueOf(2.5).setScale(1), wordGroupedMap.get("a,o6").getAverage());

        assertEquals(5, wordGroupedMap.get("a,o5").getWordSize());
        assertEquals("a,o", wordGroupedMap.get("a,o5").getVowels());
        assertEquals(BigDecimal.valueOf(2.0).setScale(1), wordGroupedMap.get("a,o5").getAverage());

        assertEquals(4, wordGroupedMap.get("a,e4").getWordSize());
        assertEquals("a,e", wordGroupedMap.get("a,e4").getVowels());
        assertEquals(BigDecimal.valueOf(2.0).setScale(1), wordGroupedMap.get("a,e4").getAverage());
    }

    @Test
    public void shouldBuildMapKeyWithSuccess() {
        assertEquals("a,o6", ReflectionTestUtils.invokeMethod(textService, "buildMapKey", new Object[]{"platon"}));
        assertEquals("a,e4", ReflectionTestUtils.invokeMethod(textService, "buildMapKey", new Object[]{"made"}));
        assertEquals("a,o6", ReflectionTestUtils.invokeMethod(textService, "buildMapKey", new Object[]{"bamboo"}));
        assertEquals("a,o5", ReflectionTestUtils.invokeMethod(textService, "buildMapKey", new Object[]{"boats"}));
    }

    @Test
    public void shouldBuildWordList() {
        List<String> words = ReflectionTestUtils.invokeMethod(textService, "buildWordsList",
                new Object[]{Arrays.asList("Platon made bamboo boats.")});
        assertEquals("[Platon, made, bamboo, boats]", words.toString());
        words = ReflectionTestUtils.invokeMethod(textService, "buildWordsList",
                new Object[]{Arrays.asList("Platon made bamboo boats.", "O que foi, I have  teste it, again.")});
        assertEquals("[Platon, made, bamboo, boats, O, que, foi, I, have, teste, it, again]", words.toString());
    }

}
