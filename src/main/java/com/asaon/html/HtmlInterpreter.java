package com.asaon.html;

import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface HtmlInterpreter<T> {
	void onTagStart(String name, Map<String, String> attrs, boolean empty);
	void onContent(String content);
	void onTagEnd(String name);

	T onSuccess();

	default <U> HtmlInterpreter<U> map(Function<T, U> fn) {
		return new HtmlInterpreterWrapper<>(this) {
			@Override
			public U onSuccess() {
				return fn.apply(wrapped.onSuccess());
			}
		};
	}

	default T interpret(UnaryOperator<HtmlDsl.Document<T, ?>> expr) {
		return expr.apply(document()).end();
	}

	default HtmlDsl.Document<T, ?> document() {
		return new HtmlDslImpl<>(this);
	}
}
