package com.asaon.html;

import java.io.IOException;
import java.util.Map;

public class HtmlAppender<W extends Appendable> implements HtmlInterpreter<W> {

	protected final W writer;
	protected final String indentation;
	private int level = 0;

	public HtmlAppender(W writer, String indentation) {
		this.writer = writer;
		this.indentation = indentation;
	}

	public HtmlAppender(W writer) {
		this(writer, "\t");
	}

	private void indent() throws IOException {
		for (int i = 0; i < level; i++)
			writer.append(indentation);
	}

	@Override
	public void onTagStart(String name, Map<String, String> attrs, boolean empty) {
		try {
			indent();
			writer.append('<').append(name);
			for (var attr : attrs.entrySet()) {
				writer.append(' ').append(attr.getKey());
				if (attr.getValue() != null)
					writer.append('=').append('"').append(attr.getValue()).append('\"');
			}
			if (empty)
				writer.append('/');
			writer.append('>').append('\n');
			level++;
		}
		catch (IOException e) {
			throw new HtmlException(e);
		}
	}

	@Override
	public void onContent(String content) {
		try {
			indent();
			writer.append(content).append('\n');
		}
		catch (IOException e) {
			throw new HtmlException(e);
		}
	}

	@Override
	public void onTagEnd(String name) {
		level--;
		try {
			indent();
			writer.append("</").append(name).append('>').append('\n');
		}
		catch (IOException e) {
			throw new HtmlException(e);
		}
	}

	@Override
	public W onSuccess() {
		return writer;
	}
}
