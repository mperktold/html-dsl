package com.asaon.html.dsl;

import com.asaon.html.HtmlInterpreter;

public class Div<PARENT> extends HtmlDsl<Div<PARENT>> {

	private final PARENT parent;

	public Div(HtmlInterpreter<?> interpreter, PARENT parent) {
		super(interpreter);
		this.parent = parent;
	}

	public PARENT _div() {
		interpreter.onTagEnd("div");
		return parent;
	}
}
