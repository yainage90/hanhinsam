package com.yaincoding.hanhinsam.filters.jamo;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;

public class JamoDecomposeFilterFactory extends AbstractTokenFilterFactory {

	@Inject
	public JamoDecomposeFilterFactory(IndexSettings indexSettings, Environment env, String name,
			Settings settings) {
		super(indexSettings, name, settings);
	}

	@Override
	public TokenStream create(TokenStream tokenStream) {
		return new JamoDecomposeFilter(tokenStream);
	}
}
