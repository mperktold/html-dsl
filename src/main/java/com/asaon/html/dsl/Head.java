package com.asaon.html.dsl;

import com.asaon.html.HtmlInterpreter;

public class Head<PARENT> extends HtmlDsl<Head<PARENT>> {

	private final PARENT parent;

	public Head(HtmlInterpreter<?> interpreter, PARENT parent) {
		super(interpreter);
		this.parent = parent;
	}

	public PARENT _head() {
		interpreter.onTagEnd("head");
		return parent;
	}
}
