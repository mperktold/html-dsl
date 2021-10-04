package com.asaon.html.dsl;

import com.asaon.html.HtmlInterpreter;

public class Meta<PARENT> extends HtmlDsl<Meta<PARENT>> {

	private final PARENT parent;

	public Meta(HtmlInterpreter<?> interpreter, PARENT parent) {
		super(interpreter);
		this.parent = parent;
	}

	public PARENT _meta() {
		interpreter.onTagEnd("meta");
		return parent;
	}
}
