package com.yaincoding.hanhinsam.hangul_util;

import java.util.HashMap;
import java.util.Map;


public class JamoUtil {

    // 초성 19개
    private final Map<Character, Integer> CHOSUNG_MAP = new HashMap<>(19);
    private final char[] CHOSUNG_LIST = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ',
            'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};

    // 중성 21개
    private final Map<Character, Integer> JUNGSUNG_MAP = new HashMap<>(21);
    private final char[] JUNGSUNG_LIST = {'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ',
            'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'};

    // 종성 28개(없는 경우 - 공백 포함)
    private final Map<Character, Integer> JONGSUNG_MAP = new HashMap<>(28);
    private final char[] JONGSUNG_LIST = {' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ',
            'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};

    private final Map<String, String> DUAL_JUNGSUNG_MAP = new HashMap<>(7);
    private final Map<String, String> DUAL_JONGSUNG_MAP = new HashMap<>(9);

    private final Map<Character, String> LAYER_CHARACTER_MAP = new HashMap<>(16);


    public JamoUtil() {
        initialize();
    }

    private void initialize() {

        initializeChoSungMap();
        initializeJungSungMap();
        initializeJongSungMap();

        initializeDualJungSungMap();
        initializeDualJongSungMap();

        initializeLayerCharacterMap();
    }


    private void initializeLayerCharacterMap() {

        LAYER_CHARACTER_MAP.put('ㄳ', "ㄱㅅ");
        LAYER_CHARACTER_MAP.put('ㄵ', "ㄴㅈ");
        LAYER_CHARACTER_MAP.put('ㄶ', "ㄴㅎ");
        LAYER_CHARACTER_MAP.put('ㄺ', "ㄹㄱ");
        LAYER_CHARACTER_MAP.put('ㄻ', "ㄹㅁ");
        LAYER_CHARACTER_MAP.put('ㄼ', "ㄹㅂ");
        LAYER_CHARACTER_MAP.put('ㄽ', "ㄹㅅ");
        LAYER_CHARACTER_MAP.put('ㄾ', "ㄹㅌ");
        LAYER_CHARACTER_MAP.put('ㅀ', "ㄹㅎ");
        LAYER_CHARACTER_MAP.put('ㅄ', "ㅂㅅ");
        LAYER_CHARACTER_MAP.put('ㅘ', "ㅗㅏ");
        LAYER_CHARACTER_MAP.put('ㅙ', "ㅗㅐ");
        LAYER_CHARACTER_MAP.put('ㅚ', "ㅗㅣ");
        LAYER_CHARACTER_MAP.put('ㅝ', "ㅜㅓ");
        LAYER_CHARACTER_MAP.put('ㅞ', "ㅜㅔ");
        LAYER_CHARACTER_MAP.put('ㅟ', "ㅜㅣ");
        LAYER_CHARACTER_MAP.put('ㅢ', "ㅡㅣ");
    }

    private void initializeChoSungMap() {
        CHOSUNG_MAP.put('ㄱ', 0);
        CHOSUNG_MAP.put('ㄲ', 1);
        CHOSUNG_MAP.put('ㄴ', 2);
        CHOSUNG_MAP.put('ㄷ', 3);
        CHOSUNG_MAP.put('ㄸ', 4);
        CHOSUNG_MAP.put('ㄹ', 5);
        CHOSUNG_MAP.put('ㅁ', 6);
        CHOSUNG_MAP.put('ㅂ', 7);
        CHOSUNG_MAP.put('ㅃ', 8);
        CHOSUNG_MAP.put('ㅅ', 9);
        CHOSUNG_MAP.put('ㅆ', 10);
        CHOSUNG_MAP.put('ㅇ', 11);
        CHOSUNG_MAP.put('ㅈ', 12);
        CHOSUNG_MAP.put('ㅉ', 13);
        CHOSUNG_MAP.put('ㅊ', 14);
        CHOSUNG_MAP.put('ㅋ', 15);
        CHOSUNG_MAP.put('ㅌ', 16);
        CHOSUNG_MAP.put('ㅍ', 17);
        CHOSUNG_MAP.put('ㅎ', 18);
    }

    private void initializeJungSungMap() {

        JUNGSUNG_MAP.put('ㅏ', 0);
        JUNGSUNG_MAP.put('ㅐ', 1);
        JUNGSUNG_MAP.put('ㅑ', 2);
        JUNGSUNG_MAP.put('ㅒ', 3);
        JUNGSUNG_MAP.put('ㅓ', 4);
        JUNGSUNG_MAP.put('ㅔ', 5);
        JUNGSUNG_MAP.put('ㅕ', 6);
        JUNGSUNG_MAP.put('ㅖ', 7);
        JUNGSUNG_MAP.put('ㅗ', 8);
        JUNGSUNG_MAP.put('ㅘ', 9);
        JUNGSUNG_MAP.put('ㅙ', 10);
        JUNGSUNG_MAP.put('ㅚ', 11);
        JUNGSUNG_MAP.put('ㅛ', 12);
        JUNGSUNG_MAP.put('ㅜ', 13);
        JUNGSUNG_MAP.put('ㅝ', 14);
        JUNGSUNG_MAP.put('ㅞ', 15);
        JUNGSUNG_MAP.put('ㅟ', 16);
        JUNGSUNG_MAP.put('ㅠ', 17);
        JUNGSUNG_MAP.put('ㅡ', 18);
        JUNGSUNG_MAP.put('ㅢ', 19);
        JUNGSUNG_MAP.put('ㅣ', 20);
    }

    private void initializeJongSungMap() {

        JONGSUNG_MAP.put(' ', 0);
        JONGSUNG_MAP.put('ㄱ', 1);
        JONGSUNG_MAP.put('ㄲ', 2);
        JONGSUNG_MAP.put('ㄳ', 3);
        JONGSUNG_MAP.put('ㄴ', 4);
        JONGSUNG_MAP.put('ㄵ', 5);
        JONGSUNG_MAP.put('ㄶ', 6);
        JONGSUNG_MAP.put('ㄷ', 7);
        JONGSUNG_MAP.put('ㄹ', 8);
        JONGSUNG_MAP.put('ㄺ', 9);
        JONGSUNG_MAP.put('ㄻ', 10);
        JONGSUNG_MAP.put('ㄼ', 11);
        JONGSUNG_MAP.put('ㄽ', 12);
        JONGSUNG_MAP.put('ㄾ', 13);
        JONGSUNG_MAP.put('ㄿ', 14);
        JONGSUNG_MAP.put('ㅀ', 15);
        JONGSUNG_MAP.put('ㅁ', 16);
        JONGSUNG_MAP.put('ㅂ', 17);
        JONGSUNG_MAP.put('ㅄ', 18);
        JONGSUNG_MAP.put('ㅅ', 19);
        JONGSUNG_MAP.put('ㅆ', 20);
        JONGSUNG_MAP.put('ㅇ', 21);
        JONGSUNG_MAP.put('ㅈ', 22);
        JONGSUNG_MAP.put('ㅊ', 23);
        JONGSUNG_MAP.put('ㅋ', 24);
        JONGSUNG_MAP.put('ㅌ', 25);
        JONGSUNG_MAP.put('ㅍ', 26);
        JONGSUNG_MAP.put('ㅎ', 27);
    }

    private void initializeDualJungSungMap() {

        DUAL_JUNGSUNG_MAP.put("ㅜㅣ", "ㅟ");
        DUAL_JUNGSUNG_MAP.put("ㅡㅣ", "ㅢ");
        DUAL_JUNGSUNG_MAP.put("ㅗㅏ", "ㅘ");
        DUAL_JUNGSUNG_MAP.put("ㅜㅓ", "ㅝ");
        DUAL_JUNGSUNG_MAP.put("ㅜㅔ", "ㅞ");
        DUAL_JUNGSUNG_MAP.put("ㅗㅣ", "ㅚ");
        DUAL_JUNGSUNG_MAP.put("ㅗㅐ", "ㅙ");
    }

    private void initializeDualJongSungMap() {

        DUAL_JONGSUNG_MAP.put("ㄱㅅ", "ㄳ");
        DUAL_JONGSUNG_MAP.put("ㄴㅈ", "ㄵ");
        DUAL_JONGSUNG_MAP.put("ㄴㅎ", "ㄶ");
        DUAL_JONGSUNG_MAP.put("ㄹㄱ", "ㄺ");
        DUAL_JONGSUNG_MAP.put("ㄹㅁ", "ㄻ");
        DUAL_JONGSUNG_MAP.put("ㄹㅎ", "ㅀ");
        DUAL_JONGSUNG_MAP.put("ㄹㅂ", "ㄼ");
        DUAL_JONGSUNG_MAP.put("ㄹㅌ", "ㄾ");
        DUAL_JONGSUNG_MAP.put("ㅂㅅ", "ㅄ");
    }

    // 문자열 분해
    public String decompose(String hangulString, boolean delayer) {

        StringBuilder jasoBuilder = new StringBuilder();
        for (char ch : hangulString.toCharArray()) {
            String jaso = decompose(ch);

            if (delayer) {
                jaso = deLayer(jaso);
            }

            jasoBuilder.append(jaso);
        }

        return jasoBuilder.toString();
    }

    // 한 글자 분해
    private String decompose(char hangul) {

        if (hangul < '가' || hangul > '힣')
            return String.valueOf(hangul);

        StringBuilder jasoBuilder = new StringBuilder();

        int GA = '\uAC00'; // '가'
        int diff = hangul - GA;

        final int chosungIndex = diff / (JUNGSUNG_MAP.size() * JONGSUNG_MAP.size());
        jasoBuilder.append(CHOSUNG_LIST[chosungIndex]);

        final int jungsungIndex =
                (diff - ((JONGSUNG_MAP.size() * JUNGSUNG_MAP.size()) * chosungIndex))
                        / JONGSUNG_MAP.size();
        jasoBuilder.append(JUNGSUNG_LIST[jungsungIndex]);

        final int jongsungIndex =
                (diff - ((JONGSUNG_MAP.size() * JUNGSUNG_MAP.size()) * chosungIndex)
                        - (JONGSUNG_MAP.size() * jungsungIndex));
        if (jongsungIndex > 0) {
            jasoBuilder.append(JONGSUNG_LIST[jongsungIndex]);
        }

        return jasoBuilder.toString();
    }

    // 겹받침('ㄺ', 'ㄼ', ...등), 이중모음('ㅘ', 'ㅞ', ...등) 분리
    private String deLayer(String str) {

        StringBuilder jasoBuilder = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (LAYER_CHARACTER_MAP.containsKey(ch)) {
                jasoBuilder.append(LAYER_CHARACTER_MAP.get(ch));
            } else {
                jasoBuilder.append(ch);
            }
        }

        return jasoBuilder.toString();
    }

    public String compose(String str) {

        int start = 0;
        StringBuilder result = new StringBuilder();

        while (start < str.length()) {
            int result_char = 0xAC00;

            // 초성인지 체크
            if (CHOSUNG_MAP.containsKey(str.charAt(start))) {
                int chosungIdx = CHOSUNG_MAP.get(str.charAt(start));
                result_char += chosungIdx * JUNGSUNG_MAP.size() * JONGSUNG_MAP.size();
                start++;

                // 중성인지 체크
                if (start < str.length() && JUNGSUNG_MAP.containsKey(str.charAt(start))) {
                    int jungsungIdx = JUNGSUNG_MAP.get(str.charAt(start));
                    result_char += JONGSUNG_MAP.size() * jungsungIdx;
                    start++;

                    // 종성인지 체크
                    if (start < str.length() && JONGSUNG_MAP.containsKey(str.charAt(start))
                            && !(start + 1 < str.length()
                                    && JUNGSUNG_MAP.containsKey(str.charAt(start + 1)))) {
                        int jongsungIdx = JONGSUNG_MAP.get(str.charAt(start));
                        result_char += jongsungIdx;
                        if (str.charAt(start) != ' ') {
                            start++;
                            // 종성이 겹받침인지 체크
                            if (start < str.length() && JONGSUNG_MAP.containsKey(str.charAt(start))
                                    && !(start + 1 < str.length()
                                            && JUNGSUNG_MAP.containsKey(str.charAt(start + 1)))) {
                                String dualJongSung =
                                        String.valueOf(str.charAt(start - 1)) + str.charAt(start);
                                if (DUAL_JONGSUNG_MAP.containsKey(dualJongSung)) {
                                    result_char -= jongsungIdx;
                                    jongsungIdx = JONGSUNG_MAP
                                            .get(DUAL_JONGSUNG_MAP.get(dualJongSung).charAt(0));
                                    result_char += jongsungIdx;
                                    start++;
                                }
                            }
                        }

                    }
                    // 중성이 이중모음인지 체크
                    else if (start < str.length() && JUNGSUNG_MAP.containsKey(str.charAt(start))) {
                        String dualJungSung =
                                String.valueOf(str.charAt(start - 1)) + str.charAt(start);
                        if (DUAL_JUNGSUNG_MAP.containsKey(dualJungSung)) {
                            result_char -= JONGSUNG_MAP.size() * jungsungIdx;
                            jungsungIdx =
                                    JUNGSUNG_MAP.get(DUAL_JUNGSUNG_MAP.get(dualJungSung).charAt(0));
                            result_char += JONGSUNG_MAP.size() * jungsungIdx;
                            start++;

                            // 이중모음 이후 종성이 있는지 체크
                            if (start < str.length() && JONGSUNG_MAP.containsKey(str.charAt(start))
                                    && !(start + 1 < str.length()
                                            && JUNGSUNG_MAP.containsKey(str.charAt(start + 1)))) {
                                int jongsungIdx = JONGSUNG_MAP.get(str.charAt(start));
                                result_char += jongsungIdx;
                                start++;

                                // 이중모음 + 겹받침 종성 체크
                                if (start < str.length()
                                        && JONGSUNG_MAP.containsKey(str.charAt(start))
                                        && !(start + 1 < str.length() && JUNGSUNG_MAP
                                                .containsKey(str.charAt(start + 1)))) {
                                    String dualJongSung = String.valueOf(str.charAt(start - 1))
                                            + str.charAt(start);
                                    if (DUAL_JONGSUNG_MAP.containsKey(dualJongSung)) {
                                        result_char -= jongsungIdx;
                                        jongsungIdx = JONGSUNG_MAP
                                                .get(DUAL_JONGSUNG_MAP.get(dualJongSung).charAt(0));
                                        result_char += jongsungIdx;
                                        start++;
                                    }
                                }
                            }
                        }
                    }

                    result.append((char) result_char);
                } else {
                    result.append(str.charAt(start - 1));
                }
            } else if (JUNGSUNG_MAP.containsKey(str.charAt(start)) && (start + 1 < str.length()
                    && JUNGSUNG_MAP.containsKey(str.charAt(start + 1)))) {
                String dualJungSung = String.valueOf(str.charAt(start)) + str.charAt(start + 1);
                if (DUAL_JUNGSUNG_MAP.containsKey(dualJungSung)) {
                    result.append(DUAL_JUNGSUNG_MAP.get(dualJungSung));
                    start += 2;
                } else {
                    result.append(str.charAt(start));
                    start++;
                }
            } else {
                result.append(str.charAt(start));
                start++;
            }
        }

        return result.toString();
    }

    public String chosung(String str) {
        StringBuilder chosungBuilder = new StringBuilder();
        for (char ch : str.toCharArray()) {
            chosungBuilder.append(decompose(ch).charAt(0));
        }

        return chosungBuilder.toString();
    }
}
