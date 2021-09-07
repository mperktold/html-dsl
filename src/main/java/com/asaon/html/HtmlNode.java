package com.asaon.html;

import java.util.List;
import java.util.Map;

public sealed interface HtmlNode {

	record Text(String content) implements HtmlNode {}

	record Element(String tag, Map<String, String> attrs, List<? extends HtmlNode> content) implements HtmlNode {
		public Element {
			attrs = Map.copyOf(attrs);
			content = List.copyOf(content);
		}
	}

	default <B extends HtmlBuilder<?>> B addTo(B builder) {
		if (this instanceof Text t)
			builder.text(t.content());
		else if (this instanceof Element e) {
			var inner = builder.tag(e.tag(), e.attrs());
			e.content().forEach(c -> c.addTo(builder));
			inner.tagEnd(e.tag());
		}
		return builder;
	}

	static HtmlBuilder.Root<List<HtmlNode>> builder() {
		return Html.builder(interpreter());
	}

	static HtmlInterpreter<List<HtmlNode>> interpreter() {
		return new HtmlNodeInterpreter();
	}

	default void appendTo(Appendable writer) {
		addTo(HtmlBuilder.root(new HtmlAppender<>(writer)));
	}
}
