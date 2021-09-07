package com.yaincoding.hanhinsam.plugin;

import java.util.HashMap;
import java.util.Map;
import com.yaincoding.hanhinsam.filters.chosung.ChosungFilterFactory;
import com.yaincoding.hanhinsam.filters.engtohan.EngToHanFilterFactory;
import com.yaincoding.hanhinsam.filters.hantoeng.HanToEngFilterFactory;
import com.yaincoding.hanhinsam.filters.jamo.JamoDecomposeFilterFactory;
import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.indices.analysis.AnalysisModule.AnalysisProvider;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

public class HanHinSamPlugin extends Plugin implements AnalysisPlugin {

	@Override
	public Map<String, AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
		Map<String, AnalysisProvider<TokenFilterFactory>> extra = new HashMap<>();
		extra.put("chosung", ChosungFilterFactory::new);
		extra.put("jamo", JamoDecomposeFilterFactory::new);
		extra.put("engtohan", EngToHanFilterFactory::new);
		extra.put("hantoeng", HanToEngFilterFactory::new);

		return extra;
	}
}
