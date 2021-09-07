package com.yaincoding.hanhinsam.filters.hantoeng;

import java.io.IOException;
import com.yaincoding.hanhinsam.hangul_util.HanEngUtil;
import com.yaincoding.hanhinsam.hangul_util.JamoUtil;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class HanToEngFilter extends TokenFilter {

	private final CharTermAttribute charAttr;
	private final JamoUtil jamoUtil;
	private final HanEngUtil hanEngUtil;

	public HanToEngFilter(TokenStream input) {
		super(input);
		jamoUtil = new JamoUtil();
		hanEngUtil = new HanEngUtil();
		charAttr = addAttribute(CharTermAttribute.class);
	}

	@Override
	public final boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
			String hanToEng =
					hanEngUtil.transformHangulToEnglish(jamoUtil.decompose(charAttr.toString(), true));
			charAttr.setEmpty().append(hanToEng);
			return true;
		}

		return false;
	}
}
