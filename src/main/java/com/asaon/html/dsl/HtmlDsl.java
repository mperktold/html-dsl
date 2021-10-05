package com.asaon.html.dsl;

import com.asaon.html.Attribute;
import com.asaon.html.HtmlInterpreter;
import com.asaon.html.HtmlNode;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class HtmlDsl<SELF extends HtmlDsl<SELF>> {

	protected final HtmlInterpreter<?> interpreter;

	public HtmlDsl(HtmlInterpreter<?> interpreter) {
		this.interpreter = interpreter;
	}

	@SuppressWarnings("unchecked")
	protected SELF self() { return (SELF)this; }

	public Tag<SELF> tag(String tag, Attribute... attrs) {
		interpreter.onTagStart(tag, List.of(attrs), false);
		return new Tag<>(interpreter, tag, self());
	}

	public Html<SELF> html() {
		interpreter.onTagStart("html", List.of(), false);
		return new Html<>(interpreter, self());
	}

	public Head<SELF> head() {
		interpreter.onTagStart("head", List.of(), false);
		return new Head<>(interpreter, self());
	}

	public Body<SELF> body() {
		interpreter.onTagStart("body", List.of(), false);
		return new Body<>(interpreter, self());
	}

	public Meta<SELF> meta(Attribute... attrs) {
		interpreter.onTagStart("meta", List.of(attrs), false);
		return new Meta<>(interpreter, self());
	}

	public Div<SELF> div(Attribute... attrs) {
		interpreter.onTagStart("div", List.of(attrs), false);
		return new Div<>(interpreter, self());
	}

	public Span<SELF> span(Attribute... attrs) {
		interpreter.onTagStart("span", List.of(attrs), false);
		return new Span<>(interpreter, self());
	}

	public SELF include(HtmlNode... nodes) {
		return include(Arrays.asList(nodes));
	}
	public SELF include(Iterable<? extends HtmlNode> nodes) {
		for (HtmlNode n : nodes) n.addTo(this);
		return self();
	}
	public SELF include(Stream<? extends HtmlNode> nodes) {
		nodes.forEach(n -> n.addTo(this));
		return self();
	}

	public <R> R include(Function<? super SELF, ? extends R> subExpr) {
		return subExpr.apply(self());
	}

	public SELF text(String content) {
		interpreter.onContent(content);
		return self();
	}
}
