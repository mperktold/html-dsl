package com.asaon.html;

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

public sealed interface HtmlNode {

	record Text(String content) implements HtmlNode {
		@Override
		public <B extends HtmlBuilder<B>> B addTo(HtmlBuilder<B> builder) {
			return builder.text(content);
		}
	}

	record Element(String tag, Map<String, String> attrs, List<? extends HtmlNode> content) implements HtmlNode {
		public Element {
			attrs = Map.copyOf(attrs);
			content = List.copyOf(content);
		}

		@Override
		public <B extends HtmlBuilder<B>> B addTo(HtmlBuilder<B> builder) {
			var inner = builder.tag(tag, attrs);
			content.forEach(c -> c.addTo(builder));
			return inner.tagEnd(tag);
		}
	}

	<B extends HtmlBuilder<B>> B addTo(HtmlBuilder<B> builder);

	static List<HtmlNode> build(UnaryOperator<HtmlBuilder.Root<List<HtmlNode>>> expr) {
		return builder().include(expr).build();
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
