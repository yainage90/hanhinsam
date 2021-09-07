package com.yaincoding.hanhinsam.filters.jamo;

import java.io.IOException;
import com.yaincoding.hanhinsam.hangul_util.JamoUtil;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class JamoDecomposeFilter extends TokenFilter {

	private final CharTermAttribute charAttr;
	private final JamoUtil jamoUtil;

	public JamoDecomposeFilter(TokenStream input) {
		super(input);
		jamoUtil = new JamoUtil();
		charAttr = addAttribute(CharTermAttribute.class);
	}

	@Override
	public final boolean incrementToken() throws IOException {

		if (input.incrementToken()) {
			String jamo = jamoUtil.decompose(charAttr.toString(), true);
			charAttr.setEmpty().append(jamo);
			return true;
		}

		return false;
	}
}
