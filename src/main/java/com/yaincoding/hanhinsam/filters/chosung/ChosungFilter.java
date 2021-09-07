package com.yaincoding.hanhinsam.filters.chosung;

import java.io.IOException;
import com.yaincoding.hanhinsam.hangul_util.JamoUtil;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class ChosungFilter extends TokenFilter {

	private final CharTermAttribute charAttr;
	private final JamoUtil jamoUtil;

	public ChosungFilter(TokenStream input) {
		super(input);
		jamoUtil = new JamoUtil();
		charAttr = addAttribute(CharTermAttribute.class);
	}

	@Override
	public final boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
			String chosung = jamoUtil.chosung(charAttr.toString());
			charAttr.setEmpty().append(chosung);
			return true;
		}

		return false;
	}
}
