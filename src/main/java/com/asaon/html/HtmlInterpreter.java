package com.asaon.html;

import java.util.Map;
import java.util.function.Function;

public interface HtmlInterpreter<T> {
	void onTagStart(String name, Map<String, String> attrs, boolean empty);
	void onContent(String content);
	void onTagEnd(String name);

	T onSuccess();

	default T interpret(HtmlExpression expr) {
		return expr.evaluate(this);
	}

	default <U> HtmlInterpreter<U> map(Function<T, U> fn) {
		return new HtmlInterpreterWrapper<>(this) {
			@Override
			public U onSuccess() {
				return fn.apply(wrapped.onSuccess());
			}
		};
	}

	static HtmlInterpreter<String> asString() {
		return new HtmlAppender<>(new StringBuilder()).map(StringBuilder::toString);
	}
}
