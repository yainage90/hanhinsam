package com.yaincoding.hanhinsam.filters.hantoeng;

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

public class HanToEngFilterTest {

	private Analyzer analyzer;

	private String getHangulToEnglish(String text) throws IOException {
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
				TokenStream tokenFilter = new HanToEngFilter(tokenizer);
				return new TokenStreamComponents(tokenizer, tokenFilter);
			}
		};
	}

	@Test
	void testOnlyHangul() throws IOException {
		assertEquals("elasticsearch", getHangulToEnglish("딤ㄴ샻ㄴㄷㅁㄱ초"));
	}

	@Test
	void testContainsEnglish() throws IOException {
		assertEquals("google.com", getHangulToEnglish("해ㅐ힏.채ㅡ"));
	}

	@Test
	void testContainsSpecialCharacters() throws IOException {
		assertEquals("elasticsearch!@#$%^&&**((", getHangulToEnglish("딤ㄴ샻ㄴㄷㅁㄱ초!@#$%^&&**(("));
	}

	@Test
	void testContainsStacking() throws IOException {
		assertEquals("sword", getHangulToEnglish("ㄴ잭ㅇ"));
	}
}
