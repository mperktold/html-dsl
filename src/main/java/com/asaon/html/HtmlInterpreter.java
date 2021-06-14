package com.asaon.html;

import com.asaon.html.HtmlNode.Element;
import com.asaon.html.HtmlNode.Text;

import java.util.Map;
import java.util.function.Function;

public interface HtmlInterpreter<T> {
	void onTagStart(String name, Map<String, String> attrs, boolean empty);
	void onContent(String content);
	void onTagEnd(String name);

	default void onNode(HtmlNode node) {
		if (node instanceof Text t)
			onContent(t.content());
		else if (node instanceof Element e) {
			onTagStart(e.tag(), e.attrs(), false);
			e.content().forEach(this::onNode);
			onTagEnd(e.tag());
		}
	}

	T onSuccess();

	default T interpret(HtmlExpression expr) {
		return expr.evaluate(this);
	}

	default <U> HtmlInterpreter<U> map(Function<T, U> fn) {
		return new HtmlInterpreter.Wrapper<>(this) {
			@Override
			public U onSuccess() {
				return fn.apply(interpreter.onSuccess());
			}
		};
	}

	abstract class Wrapper<T, U> implements HtmlInterpreter<T> {

		protected final HtmlInterpreter<U> interpreter;

		public Wrapper(HtmlInterpreter<U> interpreter) {
			this.interpreter = interpreter;
		}

		@Override
		public void onTagStart(String name, Map<String, String> attrs, boolean empty) {
			interpreter.onTagStart(name, attrs, empty);
		}

		@Override
		public void onContent(String content) {
			interpreter.onContent(content);
		}

		@Override
		public void onTagEnd(String name) {
			interpreter.onTagEnd(name);
		}
	}

	static HtmlInterpreter<String> asString() {
		return new HtmlAppender<>(new StringBuilder()).map(StringBuilder::toString);
	}
}
