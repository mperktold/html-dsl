package com.asaon.html.dsl;

import com.asaon.html.HtmlInterpreter;

public class Document<T> extends HtmlDsl<Document<T>> {

	public Document(HtmlInterpreter<T> interpreter) {
		super(interpreter);
	}

	@SuppressWarnings("unchecked")
	public T end() {
		return (T)interpreter.onSuccess();
	}
}
