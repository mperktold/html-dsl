package com.asaon.html;

import java.util.List;
import java.util.function.UnaryOperator;

public sealed interface HtmlNode {

	record Text(String content) implements HtmlNode {
		@Override
		public <D extends HtmlDsl<D>> D addTo(HtmlDsl<D> builder) {
			return builder.text(content);
		}
	}

	record Element(String tag, List<Attribute> attrs, List<? extends HtmlNode> content) implements HtmlNode {
		public Element {
			attrs = List.copyOf(attrs);
			content = List.copyOf(content);
		}

		@Override
		public <D extends HtmlDsl<D>> D addTo(HtmlDsl<D> builder) {
			var inner = builder.tag(tag, attrs.toArray(Attribute[]::new));
			content.forEach(c -> c.addTo(builder));
			return inner._tag(tag);
		}
	}

	<D extends HtmlDsl<D>> D addTo(HtmlDsl<D> builder);

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
