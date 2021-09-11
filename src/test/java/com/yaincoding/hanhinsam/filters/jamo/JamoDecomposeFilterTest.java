package com.yaincoding.hanhinsam.filters.jamo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JamoDecomposeFilterTest {

	private Analyzer analyzer;

	private String getChosungString(String text) throws IOException {
		TokenStream stream = analyzer.tokenStream("field", text);

		CharTermAttribute charAttr = stream.addAttribute(CharTermAttribute.class);

		stream.reset();

		List<String> tokenStrs = new ArrayList<>();
		while (stream.incrementToken()) {
			tokenStrs.add(charAttr.toString());
		}
		stream.close();

		String result = String.join(" ", tokenStrs);
		System.out.println(result);

		return result;
	}

	@BeforeEach
	public void setup() {
		analyzer = new Analyzer(Analyzer.PER_FIELD_REUSE_STRATEGY) {
			@Override
			protected TokenStreamComponents createComponents(String fieldName) {
				Tokenizer tokenizer = new KeywordTokenizer();
				TokenStream tokenFilter = new JamoDecomposeFilter(tokenizer);
				return new TokenStreamComponents(tokenizer, tokenFilter);
			}
		};
	}

	@Test
	void testOnlyHangul() throws IOException {
		assertEquals("ㅇㅔㄹㄹㅏㅅㅡㅌㅣㄱ ㅅㅓㅊㅣ", getChosungString("엘라스틱 서치"));
	}

	@Test
	void testContainsEnglish() throws IOException {
		assertEquals("ㅇㅔㄹㄹㅏㅅㅡㅌㅣㄱ search", getChosungString("엘라스틱 search"));
	}

	@Test
	void testContainsSpecialCharacters() throws IOException {
		assertEquals("ㅇㅔㄹㄹㅏㅅㅡㅌㅣㄱ!@# ㅅㅓㅊㅣ(*&^$%", getChosungString("엘라스틱!@# 서치(*&^$%"));
	}

	@Test
	void testContainsJamo() throws IOException {
		assertEquals("ㅇㅔㄹㄹㅏㅅㅡㅌㅣㄱ ㅅㅓㅊㅣ", getChosungString("엘라스ㅌㅣㄱ ㅅㅓ치"));
	}

	@Test
	void testContainsStacking() throws IOException {
		assertEquals("ㄱㅏㅂㅅㅈㅣㄷㅏ", getChosungString("값지다"));
		assertEquals("ㅇㅏㄴㅈㄷㅏ", getChosungString("앉다"));
	}
}
