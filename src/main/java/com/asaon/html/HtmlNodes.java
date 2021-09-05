package com.asaon.html;

import java.util.List;
import java.util.Map;

public class HtmlNodes {

	public static HtmlNode.Text text(String content) {
		return new HtmlNode.Text(content);
	}

	public static HtmlNode.Element tag(String name, Map<String, String> attrs, List<? extends HtmlNode> children) {
		return new HtmlNode.Element(name, attrs, children);
	}
	public static HtmlNode.Element tag(String name, Map<String, String> attrs, HtmlNode... children) {
		return tag(name, attrs, List.of(children));
	}
	public static HtmlNode.Element tag(String name, List<? extends HtmlNode> children) {
		return tag(name, Map.of(), children);
	}
	public static HtmlNode.Element tag(String name, HtmlNode... children) {
		return tag(name, List.of(children));
	}
	public static HtmlNode.Element tag(String name, Map<String, String> attrs, String content) {
		return tag(name, attrs, List.of(text(content)));
	}
	public static HtmlNode.Element tag(String name, String content) {
		return tag(name, Map.of(), content);
	}

	public static HtmlNode.Element html(List<HtmlNode.Element> children) {
		return tag("html", children);
	}
	public static HtmlNode.Element html(HtmlNode.Element... children) {
		return html(List.of(children));
	}

	public static HtmlNode.Element head(List<HtmlNode.Element> children) {
		return tag("head", children);
	}
	public static HtmlNode.Element head(HtmlNode.Element... children) {
		return head(List.of(children));
	}

	public static HtmlNode.Element meta(Map<String, String> attrs, List<? extends HtmlNode> children) {
		return tag("meta", attrs, children);
	}
	public static HtmlNode.Element meta(Map<String, String> attrs, HtmlNode... children) {
		return meta(attrs, List.of(children));
	}
	public static HtmlNode.Element meta(List<? extends HtmlNode> children) {
		return meta(Map.of(), children);
	}
	public static HtmlNode.Element meta(HtmlNode... children) {
		return meta(List.of(children));
	}

	public static HtmlNode.Element body(List<? extends HtmlNode> children) {
		return tag("body", children);
	}
	public static HtmlNode.Element body(HtmlNode... children) {
		return body(List.of(children));
	}

	public static HtmlNode.Element div(Map<String, String> attrs, List<? extends HtmlNode> children) {
		return tag("div", attrs, children);
	}
	public static HtmlNode.Element div(Map<String, String> attrs, HtmlNode... children) {
		return div(attrs, List.of(children));
	}
	public static HtmlNode.Element div(List<? extends HtmlNode> children) {
		return div(Map.of(), children);
	}
	public static HtmlNode.Element div(HtmlNode... children) {
		return div(List.of(children));
	}

	public static HtmlNode.Element span(Map<String, String> attrs, List<? extends HtmlNode> children) {
		return tag("span", attrs, children);
	}
	public static HtmlNode.Element span(Map<String, String> attrs, HtmlNode... children) {
		return span(attrs, List.of(children));
	}
	public static HtmlNode.Element span(List<? extends HtmlNode> children) {
		return span(Map.of(), children);
	}
	public static HtmlNode.Element span(HtmlNode... children) {
		return span(List.of(children));
	}
}
