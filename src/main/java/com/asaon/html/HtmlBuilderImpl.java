package com.asaon.html;

import java.util.Map;
import java.util.function.UnaryOperator;

record HtmlBuilderImpl<T>(HtmlInterpreter<T> interpreter) implements
	HtmlBuilder.Root<T, HtmlBuilderImpl<T>>,
	HtmlBuilder.Html<HtmlBuilderImpl<T>, HtmlBuilderImpl<T>>,
	HtmlBuilder.Head<HtmlBuilderImpl<T>, HtmlBuilderImpl<T>>,
	HtmlBuilder.Meta<HtmlBuilderImpl<T>, HtmlBuilderImpl<T>>,
	HtmlBuilder.Body<HtmlBuilderImpl<T>, HtmlBuilderImpl<T>>,
	HtmlBuilder.Div<HtmlBuilderImpl<T>, HtmlBuilderImpl<T>>,
	HtmlBuilder.Span<HtmlBuilderImpl<T>, HtmlBuilderImpl<T>>,
	HtmlBuilder.Tag<HtmlBuilderImpl<T>, HtmlBuilderImpl<T>>
{

	@Override public HtmlBuilderImpl<T> tag(String tag, Map<String, String> attrs) {
		interpreter.onTagStart(tag, attrs, false);
		return this;
	}
	@Override public HtmlBuilderImpl<T> tag(String tag) { return tag(tag, Map.of()); }
	@Override public HtmlBuilderImpl<T> tagEnd(String name) {
		interpreter.onTagEnd(name);
		return this;
	}

	@Override
	public HtmlBuilderImpl<T> text(String content) {
		interpreter.onContent(content);
		return this;
	}

	@Override public HtmlBuilderImpl<T> html() { return tag("html"); }
	@Override public HtmlBuilderImpl<T> htmlEnd() { return tagEnd("html"); }

	@Override public HtmlBuilderImpl<T> head() { return tag("head"); }
	@Override public HtmlBuilderImpl<T> headEnd() { return tagEnd("head"); }

	@Override public HtmlBuilderImpl<T> meta(Map<String, String> attrs) { return tag("meta"); }
	@Override public HtmlBuilderImpl<T> metaEnd() { return tagEnd("meta"); }

	@Override public HtmlBuilderImpl<T> body() { return tag("body"); }
	@Override public HtmlBuilderImpl<T> bodyEnd() { return tagEnd("body"); }

	@Override public HtmlBuilderImpl<T> div(Map<String, String> attrs) { return tag("div", attrs); }
	@Override public HtmlBuilderImpl<T> divEnd() { return tagEnd("div"); }

	@Override public HtmlBuilderImpl<T> span(Map<String, String> attrs) { return tag("span", attrs); }
	@Override public HtmlBuilderImpl<T> spanEnd() { return tagEnd("span"); }

	@Override public HtmlBuilderImpl<T> include(HtmlNode... nodes) {
		for (var n : nodes) interpreter.onNode(n);
		return this;
	}
	@Override public HtmlBuilderImpl<T> include(Iterable<HtmlNode> nodes) {
		nodes.forEach(interpreter::onNode);
		return this;
	}
	@Override public HtmlBuilderImpl<T> include(UnaryOperator<HtmlBuilderImpl<T>> subExpr) {
		return subExpr.apply(this);
	}

	@Override public T build() { return interpreter.onSuccess(); }
}
