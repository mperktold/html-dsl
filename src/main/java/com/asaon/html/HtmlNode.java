package com.asaon.html;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public sealed interface HtmlNode {

	record Text(String content) implements HtmlNode {}

	record Element(String tag, Map<String, String> attrs, List<HtmlNode> content) implements HtmlNode {
		public Element {
			attrs = Map.copyOf(attrs);
			content = List.copyOf(content);
		}
	}

	static HtmlBuilder.Root<List<HtmlNode>, ?> builder() {
		return HtmlBuilder.create(new HtmlNodeInterpreter());
	}

	static HtmlInterpreter<List<HtmlNode>> interpreter() {
		return new HtmlNodeInterpreter();
	}

	default void appendTo(Appendable writer) {
		var htmlWriter = new HtmlAppender<>(writer);
		htmlWriter.onNode(this);
	}

	default void writeTo(Writer writer) throws IOException {
		appendTo(writer);
		writer.flush();
	}
}
