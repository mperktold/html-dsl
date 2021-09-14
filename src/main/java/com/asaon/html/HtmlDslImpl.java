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
	@Override public HtmlDslImpl<T> _tag(String name) {
		interpreter.onTagEnd(name);
		return this;
	}

	@Override
	public HtmlDslImpl<T> text(String content) {
		interpreter.onContent(content);
		return this;
	}

	@Override public HtmlDslImpl<T> html() { return tag("html"); }
	@Override public HtmlDslImpl<T> _html() { return _tag("html"); }

	@Override public HtmlDslImpl<T> head() { return tag("head"); }
	@Override public HtmlDslImpl<T> _head() { return _tag("head"); }

	@Override public HtmlDslImpl<T> meta(Map<String, String> attrs) { return tag("meta"); }
	@Override public HtmlDslImpl<T> _meta() { return _tag("meta"); }

	@Override public HtmlDslImpl<T> body() { return tag("body"); }
	@Override public HtmlDslImpl<T> _body() { return _tag("body"); }

	@Override public HtmlDslImpl<T> div(Map<String, String> attrs) { return tag("div", attrs); }
	@Override public HtmlDslImpl<T> _div() { return _tag("div"); }

	@Override public HtmlDslImpl<T> span(Map<String, String> attrs) { return tag("span", attrs); }
	@Override public HtmlDslImpl<T> _span() { return _tag("span"); }

	@Override public T end() { return interpreter.onSuccess(); }
}
