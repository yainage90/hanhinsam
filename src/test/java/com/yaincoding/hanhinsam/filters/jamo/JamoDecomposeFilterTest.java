package com.yaincoding.hanhinsam.filters.jamo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ko.KoreanTokenizer;
import org.apache.lucene.analysis.ko.KoreanTokenizer.DecompoundMode;
import org.apache.lucene.analysis.ko.tokenattributes.PartOfSpeechAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JamoDecomposeFilterTest {

	private Analyzer analyzer;

	@BeforeEach
	public void setup() {
		analyzer = new Analyzer(Analyzer.PER_FIELD_REUSE_STRATEGY) {
			@Override
			protected TokenStreamComponents createComponents(String fieldName) {
				Tokenizer tokenizer = new KoreanTokenizer(AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY, null,
						DecompoundMode.NONE, false);
				TokenStream tokenFilter = new JamoDecomposeFilter(tokenizer);
				return new TokenStreamComponents(tokenizer, tokenFilter);
			}
		};
	}

	@Test
	void testAllTokensStringSumIsWordJamos() throws IOException {

		TokenStream stream = analyzer.tokenStream("fieldName", "태권도");

		CharTermAttribute charAttr = stream.addAttribute(CharTermAttribute.class);
		PartOfSpeechAttribute posAttr = stream.addAttribute(PartOfSpeechAttribute.class);
		OffsetAttribute offsetAttr = stream.addAttribute(OffsetAttribute.class);

		stream.reset();

		StringBuilder fullTextBuilder = new StringBuilder();
		while (stream.incrementToken()) {
			fullTextBuilder.append(charAttr.toString());
			System.out.println(charAttr.toString());
			System.out.println(posAttr.getLeftPOS() + "/" + posAttr.getRightPOS());
			System.out.println(offsetAttr.startOffset() + ", " + offsetAttr.endOffset());
			System.out.println();
		}

		assertTrue(fullTextBuilder.toString().contains("ㅌㅐㄱㅜㅓㄴㄷㅗ"));
	}
}
