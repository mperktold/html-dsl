package com.asaon.html.dsl;

import com.asaon.html.HtmlInterpreter;

public class Body<PARENT> extends HtmlDsl<Body<PARENT>> {

	private final PARENT parent;

	public Body(HtmlInterpreter<?> interpreter, PARENT parent) {
		super(interpreter);
		this.parent = parent;
	}

	public PARENT _body() {
		interpreter.onTagEnd("body");
		return parent;
	}
}
