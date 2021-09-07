package com.yaincoding.hanhinsam.hangul_util;

public class HanEngUtil {

    private char[] engHanKeyMap;
    private String[] hanEngKeyMap;

    private final JamoUtil jamoUtil;

    public HanEngUtil() {
        jamoUtil = new JamoUtil();
        initialize();
    }

    private void initialize() {
        initializeEngKeyToHanKeyMap();
        initializeHanKeyToEngKeyMap();
    }

    private void initializeEngKeyToHanKeyMap() {
        engHanKeyMap = new char['z' + 1];
        engHanKeyMap['a'] = 'ㅁ';
        engHanKeyMap['A'] = 'ㅁ';
        engHanKeyMap['b'] = 'ㅠ';
        engHanKeyMap['B'] = 'ㅠ';
        engHanKeyMap['c'] = 'ㅊ';
        engHanKeyMap['C'] = 'ㅊ';
        engHanKeyMap['d'] = 'ㅇ';
        engHanKeyMap['D'] = 'ㅇ';
        engHanKeyMap['e'] = 'ㄷ';
        engHanKeyMap['E'] = 'ㄸ';
        engHanKeyMap['f'] = 'ㄹ';
        engHanKeyMap['F'] = 'ㄹ';
        engHanKeyMap['g'] = 'ㅎ';
        engHanKeyMap['G'] = 'ㅎ';
        engHanKeyMap['h'] = 'ㅗ';
        engHanKeyMap['H'] = 'ㅗ';
        engHanKeyMap['i'] = 'ㅑ';
        engHanKeyMap['I'] = 'ㅑ';
        engHanKeyMap['j'] = 'ㅓ';
        engHanKeyMap['J'] = 'ㅓ';
        engHanKeyMap['k'] = 'ㅏ';
        engHanKeyMap['K'] = 'ㅏ';
        engHanKeyMap['l'] = 'ㅣ';
        engHanKeyMap['L'] = 'ㅣ';
        engHanKeyMap['m'] = 'ㅡ';
        engHanKeyMap['M'] = 'ㅡ';
        engHanKeyMap['n'] = 'ㅜ';
        engHanKeyMap['N'] = 'ㅜ';
        engHanKeyMap['o'] = 'ㅐ';
        engHanKeyMap['O'] = 'ㅒ';
        engHanKeyMap['p'] = 'ㅔ';
        engHanKeyMap['P'] = 'ㅖ';
        engHanKeyMap['q'] = 'ㅂ';
        engHanKeyMap['Q'] = 'ㅃ';
        engHanKeyMap['r'] = 'ㄱ';
        engHanKeyMap['R'] = 'ㄲ';
        engHanKeyMap['s'] = 'ㄴ';
        engHanKeyMap['S'] = 'ㄴ';
        engHanKeyMap['t'] = 'ㅅ';
        engHanKeyMap['T'] = 'ㅆ';
        engHanKeyMap['u'] = 'ㅕ';
        engHanKeyMap['U'] = 'ㅕ';
        engHanKeyMap['v'] = 'ㅍ';
        engHanKeyMap['V'] = 'ㅍ';
        engHanKeyMap['w'] = 'ㅈ';
        engHanKeyMap['W'] = 'ㅉ';
        engHanKeyMap['x'] = 'ㅌ';
        engHanKeyMap['X'] = 'ㅌ';
        engHanKeyMap['y'] = 'ㅛ';
        engHanKeyMap['Y'] = 'ㅛ';
        engHanKeyMap['z'] = 'ㅋ';
        engHanKeyMap['Z'] = 'ㅋ';
    }

    private void initializeHanKeyToEngKeyMap() {
        hanEngKeyMap = new String['ㅣ' + 1];

        for (int i = 0; i < hanEngKeyMap.length; i++) {
            hanEngKeyMap[i] = String.valueOf((char) i);
        }

        hanEngKeyMap['ㄱ'] = "r";
        hanEngKeyMap['ㄲ'] = "R";
        hanEngKeyMap['ㄴ'] = "s";
        hanEngKeyMap['ㄷ'] = "e";
        hanEngKeyMap['ㄸ'] = "E";
        hanEngKeyMap['ㄹ'] = "f";
        hanEngKeyMap['ㅁ'] = "a";
        hanEngKeyMap['ㅂ'] = "q";
        hanEngKeyMap['ㅃ'] = "Q";
        hanEngKeyMap['ㅅ'] = "t";
        hanEngKeyMap['ㅆ'] = "T";
        hanEngKeyMap['ㅇ'] = "d";
        hanEngKeyMap['ㅈ'] = "w";
        hanEngKeyMap['ㅉ'] = "W";
        hanEngKeyMap['ㅊ'] = "c";
        hanEngKeyMap['ㅋ'] = "z";
        hanEngKeyMap['ㅌ'] = "x";
        hanEngKeyMap['ㅍ'] = "v";
        hanEngKeyMap['ㅎ'] = "g";
        hanEngKeyMap['ㅏ'] = "k";
        hanEngKeyMap['ㅐ'] = "o";
        hanEngKeyMap['ㅑ'] = "i";
        hanEngKeyMap['ㅒ'] = "O";
        hanEngKeyMap['ㅓ'] = "j";
        hanEngKeyMap['ㅔ'] = "p";
        hanEngKeyMap['ㅕ'] = "u";
        hanEngKeyMap['ㅖ'] = "P";
        hanEngKeyMap['ㅗ'] = "h";
        hanEngKeyMap['ㅛ'] = "y";
        hanEngKeyMap['ㅜ'] = "n";
        hanEngKeyMap['ㅠ'] = "b";
        hanEngKeyMap['ㅡ'] = "m";
        hanEngKeyMap['ㅣ'] = "l";
    }

    public String transformHangulToEnglish(String hangul) {
        hangul = jamoUtil.decompose(hangul, true);

        StringBuilder englishBuilder = new StringBuilder();
        for (char ch : hangul.toCharArray()) {
            if (isHangulCharacter(ch)) {
                englishBuilder.append(hanEngKeyMap[ch]);
            } else {
                englishBuilder.append(ch);
            }
        }

        return englishBuilder.toString();
    }

    public String transformEnglishToHangul(String english) {
        StringBuilder hangulBuilder = new StringBuilder();
        for (char ch : english.toCharArray()) {
            if (isEnglishCharacter(ch)) {
                hangulBuilder.append(engHanKeyMap[ch]);
            } else {
                hangulBuilder.append(ch);
            }
        }

        return jamoUtil.compose(hangulBuilder.toString());
    }

    private boolean isHangulCharacter(char ch) {
        return ch >= 'ㄱ' && ch <= 'ㅣ';
    }

    private boolean isEnglishCharacter(char ch) {
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
    }
}
