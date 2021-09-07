package com.asaon.html;

import java.lang.ref.WeakReference;
import java.util.Map;

sealed abstract class HtmlBuilderImpl<T, SELF extends HtmlBuilder<SELF>> implements HtmlBuilder<SELF> {

	final HtmlInterpreter<T> interpreter;
	private WeakReference<NestedImpl<T, SELF>> nestedRef;

	HtmlBuilderImpl(HtmlInterpreter<T> interpreter) {
		this.interpreter = interpreter;
	}

	@SuppressWarnings("unchecked")
	private SELF self() { return (SELF)this; }

	private NestedImpl<T, SELF> nested() {
		var nested = nestedRef != null ? nestedRef.get() : null;
		if (nested == null)
			nestedRef = new WeakReference<>(nested = new NestedImpl<>(interpreter, self()));
		return nested;
	}

	@Override
	public NestedImpl<T, SELF> tag(String tag, Map<String, String> attrs) {
		interpreter.onTagStart(tag, attrs, false);
		return nested();
	}
	@Override
	public NestedImpl<T, SELF> tag(String tag) {
		return tag(tag, Map.of());
	}

	@Override
	public SELF text(String content) {
		interpreter.onContent(content);
		return self();
	}

	@Override
	public NestedImpl<T, SELF> html() { return tag("html"); }

	@Override
	public NestedImpl<T, SELF> head() { return tag("head"); }

	@Override
	public NestedImpl<T, SELF> meta(Map<String, String> attrs) { return tag("meta", attrs); }

	@Override
	public NestedImpl<T, SELF> body() { return tag("body"); }

	@Override
	public NestedImpl<T, SELF> div(Map<String, String> attrs) { return tag("div", attrs); }

	@Override
	public NestedImpl<T, SELF> span(Map<String, String> attrs) { return tag("span", attrs); }

	final static class RootImpl<T> extends HtmlBuilderImpl<T, Root<T>> implements HtmlBuilder.Root<T> {

		RootImpl(HtmlInterpreter<T> interpreter) {
			super(interpreter);
		}

		@Override
		public T build() { return interpreter.onSuccess(); }
	}

	final static class NestedImpl<T, PARENT extends HtmlBuilder<?>>
		extends HtmlBuilderImpl<T, NestedImpl<T, PARENT>>
		implements
			HtmlBuilder.Tag<PARENT, NestedImpl<T, PARENT>>,
			HtmlBuilder.Html<PARENT, NestedImpl<T, PARENT>>,
			HtmlBuilder.Head<PARENT, NestedImpl<T, PARENT>>,
			HtmlBuilder.Meta<PARENT, NestedImpl<T, PARENT>>,
			HtmlBuilder.Body<PARENT, NestedImpl<T, PARENT>>,
			HtmlBuilder.Div<PARENT, NestedImpl<T, PARENT>>,
			HtmlBuilder.Span<PARENT, NestedImpl<T, PARENT>>
	{
		final PARENT parent;

		NestedImpl(HtmlInterpreter<T> interpreter, PARENT parent) {
			super(interpreter);
			this.parent = parent;
		}

		@Override public PARENT tagEnd(String name) {
			interpreter.onTagEnd(name);
			return parent;
		}
		@Override public PARENT htmlEnd() { return tagEnd("html"); }
		@Override public PARENT headEnd() { return tagEnd("head"); }
		@Override public PARENT metaEnd() { return tagEnd("meta"); }
		@Override public PARENT bodyEnd() { return tagEnd("body"); }
		@Override public PARENT divEnd() { return tagEnd("div"); }
		@Override public PARENT spanEnd() { return tagEnd("span"); }
	}
}
