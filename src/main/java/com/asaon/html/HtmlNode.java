package com.asaon.html;

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

public sealed interface HtmlNode {

	record Text(String content) implements HtmlNode {
		@Override
		public <B extends HtmlDsl<B>> B addTo(HtmlDsl<B> builder) {
			return builder.text(content);
		}
	}

	record Element(String tag, Map<String, String> attrs, List<? extends HtmlNode> content) implements HtmlNode {
		public Element {
			attrs = Map.copyOf(attrs);
			content = List.copyOf(content);
		}

		@Override
		public <B extends HtmlDsl<B>> B addTo(HtmlDsl<B> builder) {
			var inner = builder.tag(tag, attrs);
			content.forEach(c -> c.addTo(builder));
			return inner._tag(tag);
		}
	}

	<B extends HtmlDsl<B>> B addTo(HtmlDsl<B> builder);

	static List<HtmlNode> build(UnaryOperator<HtmlDsl.Document<List<HtmlNode>, ?>> expr) {
		return interpreter().interpret(expr);
	}

	static HtmlInterpreter<List<HtmlNode>> interpreter() {
		return new HtmlNodeInterpreter();
	}

	default void appendTo(Appendable writer) {
		addTo(new HtmlAppender<>(writer).document());
	}

	static String toString(HtmlNode node) {
		var sb = new StringBuilder();
		node.appendTo(sb);
		return sb.toString();
	}
}
