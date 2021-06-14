package com.asaon.html;

@FunctionalInterface
public interface HtmlExpression {

	HtmlBuilder.Root<?, ?> form(HtmlBuilder.Root<?, ?> builder);

	default <T> T evaluate(HtmlInterpreter<T> interpreter) {
		form(HtmlBuilder.create(interpreter));
		return interpreter.onSuccess();
	}

	static HtmlExpression of(HtmlExpression expr) {
		return expr;
	}

	static <T> T evaluate(HtmlExpression expr, HtmlInterpreter<T> interpreter) {
		return expr.evaluate(interpreter);
	}
}
