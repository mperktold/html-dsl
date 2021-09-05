package com.asaon.html;

import java.util.function.UnaryOperator;

public class Html {

	private Html() {}

	public static <T> HtmlBuilder.Root<T, ?> builder(HtmlInterpreter<T> interpreter) {
		return HtmlBuilder.create(interpreter);
	}

	public static <A extends Appendable> HtmlBuilder.Root<A, ?> appendTo(A sink) {
		return HtmlBuilder.create(new HtmlAppender<>(sink));
	}

	public static <W extends Appendable> W appendTo(W writer, UnaryOperator<HtmlBuilder.Root<W, ?>> expr) {
		return HtmlBuilder.interpret(new HtmlAppender<>(writer), expr);
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
}
