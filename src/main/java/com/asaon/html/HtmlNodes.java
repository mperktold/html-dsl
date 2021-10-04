package com.asaon.html;

import java.util.List;

public class HtmlNodes {

	public static HtmlNode.Text text(String content) {
		return new HtmlNode.Text(content);
	}

	public static HtmlNode.Element tag(String name, Attribute... attrs) {
		return new HtmlNode.Element(name, List.of(attrs), List.of());
	}
	public static HtmlNode.Element tag(String name) {
		return new HtmlNode.Element(name, List.of(), List.of());
	}

	public static HtmlNode.Element html() {
		return tag("html");
	}

	public static HtmlNode.Element head() {
		return tag("head");
	}

	public static HtmlNode.Element meta(Attribute... attrs) {
		return tag("meta", attrs);
	}

	public static HtmlNode.Element body() {
		return tag("body");
	}

	public static HtmlNode.Element div(Attribute... attrs) {
		return tag("div", attrs);
	}

	public static HtmlNode.Element span(Attribute... attrs) {
		return tag("span", attrs);
	}
}
