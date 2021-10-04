package com.asaon.html.dsl;

import com.asaon.html.HtmlInterpreter;

public class Span<PARENT> extends HtmlDsl<Span<PARENT>> {

	private final PARENT parent;

	public Span(HtmlInterpreter<?> interpreter, PARENT parent) {
		super(interpreter);
		this.parent = parent;
	}

	public PARENT _span() {
		interpreter.onTagEnd("span");
		return parent;
	}
}
