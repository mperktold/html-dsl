package com.asaon.html;

import java.util.Map;

record HtmlDslImpl<T>(HtmlInterpreter<T> interpreter) implements
	HtmlDsl.Document<T, HtmlDslImpl<T>>,
	HtmlDsl.Html<HtmlDslImpl<T>, HtmlDslImpl<T>>,
	HtmlDsl.Head<HtmlDslImpl<T>, HtmlDslImpl<T>>,
	HtmlDsl.Meta<HtmlDslImpl<T>, HtmlDslImpl<T>>,
	HtmlDsl.Body<HtmlDslImpl<T>, HtmlDslImpl<T>>,
	HtmlDsl.Div<HtmlDslImpl<T>, HtmlDslImpl<T>>,
	HtmlDsl.Span<HtmlDslImpl<T>, HtmlDslImpl<T>>,
	HtmlDsl.Tag<HtmlDslImpl<T>, HtmlDslImpl<T>>
{

	@Override public HtmlDslImpl<T> tag(String tag, Map<String, String> attrs) {
		interpreter.onTagStart(tag, attrs, false);
		return this;
	}
	@Override public HtmlDslImpl<T> tag(String tag) { return tag(tag, Map.of()); }
	@Override public HtmlDslImpl<T> tagEnd(String name) {
		interpreter.onTagEnd(name);
		return this;
	}

	@Override
	public HtmlDslImpl<T> text(String content) {
		interpreter.onContent(content);
		return this;
	}

	@Override public HtmlDslImpl<T> html() { return tag("html"); }
	@Override public HtmlDslImpl<T> htmlEnd() { return tagEnd("html"); }

	@Override public HtmlDslImpl<T> head() { return tag("head"); }
	@Override public HtmlDslImpl<T> headEnd() { return tagEnd("head"); }

	@Override public HtmlDslImpl<T> meta(Map<String, String> attrs) { return tag("meta"); }
	@Override public HtmlDslImpl<T> metaEnd() { return tagEnd("meta"); }

	@Override public HtmlDslImpl<T> body() { return tag("body"); }
	@Override public HtmlDslImpl<T> bodyEnd() { return tagEnd("body"); }

	@Override public HtmlDslImpl<T> div(Map<String, String> attrs) { return tag("div", attrs); }
	@Override public HtmlDslImpl<T> divEnd() { return tagEnd("div"); }

	@Override public HtmlDslImpl<T> span(Map<String, String> attrs) { return tag("span", attrs); }
	@Override public HtmlDslImpl<T> spanEnd() { return tagEnd("span"); }

	@Override public T documentEnd() { return interpreter.onSuccess(); }
}
