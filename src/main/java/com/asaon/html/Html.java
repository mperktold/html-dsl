package com.asaon.html;

import java.util.function.UnaryOperator;

public class Html {

	private Html() {}

	public static <A extends Appendable> HtmlAppender<A> appendingTo(A sink) {
		return new HtmlAppender<>(sink);
	}

	public static <W extends Appendable> W appendTo(W writer, UnaryOperator<HtmlDsl.Document<W, ?>> expr) {
		return new HtmlAppender<>(writer).interpret(expr);
	}

	public static HtmlInterpreter<String> intoString() {
		return new HtmlAppender<>(new StringBuilder()).map(StringBuilder::toString);
	}

	public static String buildString(UnaryOperator<HtmlDsl.Document<String, ?>> expr) {
		return intoString().interpret(expr);
	}
}
