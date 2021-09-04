package com.asaon.html;

import com.asaon.html.HtmlNode.Element;
import com.asaon.html.HtmlNode.Text;

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

public class Html {

	private Html() {}

	public static <A extends Appendable> HtmlBuilder.Root<A, ?> appendTo(A sink) {
		return HtmlBuilder.create(new HtmlAppender<>(sink));
	}

	public static <A extends Appendable> A appendTo(A sink, HtmlExpression expr) {
		return expr.evaluate(new HtmlAppender<>(sink));
	}

	public static HtmlBuilder.Root<String, ?> stringBuilder() {
		return HtmlBuilder.create(new HtmlAppender<>(new StringBuilder()).map(StringBuilder::toString));
	}

	public static String toString(UnaryOperator<HtmlBuilder.Root<String, ?>> expr) {
		return expr.apply(stringBuilder()).build();
	}

	public static String toString(HtmlNode node) {
		var sb = new StringBuilder();
		node.appendTo(sb);
		return sb.toString();
	}

	public static Text text(String content) {
		return new Text(content);
	}
	public static Element tag(String name, Map<String, String> attrs, HtmlNode... children) {
		return new Element(name, attrs, List.of(children));
	}
	public static Element tag(String name, HtmlNode... children) {
		return tag(name, Map.of(), children);
	}
	public static Element tag(String name, Map<String, String> attrs, String content) {
		return new Element(name, attrs, List.of(text(content)));
	}
	public static Element tag(String name, String content) {
		return tag(name, Map.of(), content);
	}

	public static Element html(Element... children) { return tag("html", children); }
	public static Element head(Element... children) { return tag("head", children); }
	public static Element meta(Map<String, String> attrs, HtmlNode... children) { return tag("meta", attrs, children); }
	public static Element meta(HtmlNode... children) { return meta(Map.of(), children); }
	public static Element body(HtmlNode... children) { return tag("body", children); }
	public static Element div(Map<String, String> attrs, HtmlNode... children) { return tag("div", attrs, children); }
	public static Element div(HtmlNode... children) { return div(Map.of(), children); }
	public static Element span(Map<String, String> attrs, HtmlNode... children) { return tag("span", attrs, children); }
	public static Element span(HtmlNode... children) { return span(Map.of(), children); }
}
