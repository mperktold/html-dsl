package com.asaon.html.dsl;

import com.asaon.html.HtmlInterpreter;

public class Html<PARENT> extends HtmlDsl<Html<PARENT>> {

	private final PARENT parent;

	public Html(HtmlInterpreter<?> interpreter, PARENT parent) {
		super(interpreter);
		this.parent = parent;
	}

	public PARENT _html() {
		interpreter.onTagEnd("html");
		return parent;
	}
}
