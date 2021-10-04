package com.asaon.html;

import com.asaon.html.HtmlNode.Element;
import com.asaon.html.HtmlNode.Text;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class HtmlNodeInterpreter implements HtmlInterpreter<List<HtmlNode>> {

	private record MutableElement(String tag, Attribute[] attrs, ArrayList<HtmlNode> content) {
		MutableElement(String tag, Attribute[] attrs) {
			this(tag, attrs, new ArrayList<>());
		}
	}

	private final ArrayDeque<MutableElement> stack = new ArrayDeque<>();
	private final ArrayList<HtmlNode> rootNodes = new ArrayList<>();

	@Override
	public void onTagStart(String name, Attribute[] attrs, boolean empty) {
		stack.push(new MutableElement(name, attrs));
	}

	@Override
	public void onContent(String content) {
		onNodeEnd(new Text(content));
	}

	@Override
	public void onTagEnd(String name) {
		MutableElement elem = stack.pop();
		onNodeEnd(new Element(elem.tag, List.of(elem.attrs), elem.content));
	}

	private void onNodeEnd(HtmlNode node) {
		MutableElement cur = stack.peekFirst();
		var list = cur != null ? cur.content : rootNodes;
		list.add(node);
	}

	@Override
	public List<HtmlNode> onSuccess() {
		return List.copyOf(rootNodes);
	}
}
