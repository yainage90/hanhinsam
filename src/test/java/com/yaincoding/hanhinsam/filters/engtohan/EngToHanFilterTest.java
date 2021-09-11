package com.yaincoding.hanhinsam.filters.engtohan;

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

public class EngToHanFilterTest {

	private Analyzer analyzer;

	private String getEnglishToHangul(String text) throws IOException {
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
				TokenStream tokenFilter = new EngToHanFilter(tokenizer);
				return new TokenStreamComponents(tokenizer, tokenFilter);
			}
		};
	}

	@Test
	void testOnlyEnglish() throws IOException {
		assertEquals("엘라스틱 서치", getEnglishToHangul("dpffktmxlr tjcl"));
	}

	@Test
	void testContainsHangul() throws IOException {
		assertEquals("엘라스틱 서치", getEnglishToHangul("dpffktmxlr 서치"));
	}

	@Test
	void testContainsSpecialCharacters() throws IOException {
		assertEquals("엘라스틱!@# 서치(*&^$%", getEnglishToHangul("dpffktmxlr!@# tjcl(*&^$%"));
	}

	@Test
	void testContainsStacking() throws IOException {
		assertEquals("값지다", getEnglishToHangul("rkqtwlek"));
		assertEquals("앉다", getEnglishToHangul("dkswek"));
	}
}
