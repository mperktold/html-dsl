package com.asaon.html;

import java.util.Map;

public abstract class HtmlInterpreterWrapper<T, U> implements HtmlInterpreter<T> {

	protected final HtmlInterpreter<U> wrapped;

	public HtmlInterpreterWrapper(HtmlInterpreter<U> wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public void onTagStart(String name, Map<String, String> attrs, boolean empty) {
		wrapped.onTagStart(name, attrs, empty);
	}

	@Override
	public void onContent(String content) {
		wrapped.onContent(content);
	}

	@Override
	public void onTagEnd(String name) {
		wrapped.onTagEnd(name);
	}
}
