package com.asaon.html;

import com.asaon.html.dsl.Document;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface HtmlInterpreter<T> {
	void onTagStart(String name, List<Attribute> attrs, boolean empty);
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

	default T interpret(UnaryOperator<Document<T>> expr) {
		return expr.apply(document()).end();
	}

	default Document<T> document() {
		return new Document<>(this);
	}
}
