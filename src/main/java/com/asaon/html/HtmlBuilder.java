package com.asaon.html;

import java.util.Map;
import java.util.function.UnaryOperator;

public interface HtmlBuilder<SELF extends HtmlBuilder<SELF>> {
	Tag<SELF, ?> tag(String tag, Map<String, String> attrs);
	default Tag<SELF, ?> tag(String tag) { return tag(tag, Map.of()); }

	Html<SELF, ?> html();
	Head<SELF, ?> head();
	Body<SELF, ?> body();
	Meta<SELF, ?> meta(Map<String, String> attrs);
	default Meta<SELF, ?> meta() { return meta(Map.of()); }

	Div<SELF, ?> div(Map<String, String> attrs);
	default Div<SELF, ?> div() { return div(Map.of()); }
	Div<SELF, ?> span(Map<String, String> attrs);
	default Div<SELF, ?> span() { return div(Map.of()); }

	SELF include(HtmlNode... nodes);
	SELF include(Iterable<HtmlNode> nodes);
	SELF include(UnaryOperator<SELF> subExpr);

	SELF text(String content);

	interface Tag<PARENT, SELF extends Tag<PARENT, SELF>> extends HtmlBuilder<SELF> { PARENT tagEnd(String tag); }
	interface Html<PARENT, SELF extends Html<PARENT, SELF>> extends HtmlBuilder<SELF> { PARENT htmlEnd(); }
	interface Head<PARENT, SELF extends Head<PARENT, SELF>> extends HtmlBuilder<SELF> { PARENT headEnd(); }
	interface Body<PARENT, SELF extends Body<PARENT, SELF>> extends HtmlBuilder<SELF> { PARENT bodyEnd(); }
	interface Meta<PARENT, SELF extends Meta<PARENT, SELF>> extends HtmlBuilder<SELF> { PARENT metaEnd(); }
	interface Div<PARENT, SELF extends Div<PARENT, SELF>> extends HtmlBuilder<SELF> { PARENT divEnd(); }
	interface Span<PARENT, SELF extends Span<PARENT, SELF>> extends HtmlBuilder<SELF> { PARENT spanEnd(); }

	interface Root<T, SELF extends Root<T, SELF>> extends HtmlBuilder<SELF> { T build(); }

	static <T> Root<T, ?> create(HtmlInterpreter<T> interpreter) {
		return new HtmlBuilderImpl<>(interpreter);
	}

	static Root<String, ?> forString() {
		return create(new HtmlAppender<>(new StringBuilder()).map(StringBuilder::toString));
	}
}
