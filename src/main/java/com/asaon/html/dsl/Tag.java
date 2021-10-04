package com.asaon.html.dsl;

import com.asaon.html.HtmlInterpreter;

public class Tag<PARENT> extends HtmlDsl<Tag<PARENT>> {

	private final String name;
	private final PARENT parent;

	public Tag(HtmlInterpreter<?> interpreter, String name, PARENT parent) {
		super(interpreter);
		this.name = name;
		this.parent = parent;
	}

	public PARENT _tag(String tag) {
		if (!tag.equals(name))
			throw new IllegalArgumentException("End tag </" + tag + "> doesn't match start tag <" + name + ">");
		interpreter.onTagEnd(tag);
		return parent;
	}
}
