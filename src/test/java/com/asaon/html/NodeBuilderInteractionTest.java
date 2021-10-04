package com.asaon.html;

import static com.asaon.html.HtmlNodes.body;
import static com.asaon.html.HtmlNodes.div;
import static com.asaon.html.HtmlNodes.head;
import static com.asaon.html.HtmlNodes.html;
import static com.asaon.html.HtmlNodes.tag;
import static com.asaon.html.HtmlNodes.text;
import static com.asaon.html.attr.Attributes.id;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NodeBuilderInteractionTest {

	@Test
	void testIncludeNodes() {
		var subNode = div(List.of(id("main")),
			text("some text"),
			text("more text"),
			tag("custom-element")
		);
		var htmlString = Html.intoString()
			.document()
				.html()
					.head()
					._head()
					.body()
						.include(subNode)
					._body()
				._html()
			.end();
		Assertions.assertEquals("""
			<html>
				<head>
				</head>
				<body>
					<div id="main">
						some text
						more text
						<custom-element>
						</custom-element>
					</div>
				</body>
			</html>
			""",
			htmlString
		);
	}

	@Test
	void testNodesWithSubBuilder() {
		var html = html(
			head(),
			body(
				HtmlNode.build(this::subExpression)
			)
		);
		Assertions.assertEquals("""
			<html>
				<head>
				</head>
				<body>
					<div id="main">
						some text
						more text
						<custom-element>
						</custom-element>
					</div>
				</body>
			</html>
			""",
			HtmlNode.toString(html)
		);
	}

	<D extends HtmlDsl<D>> D subExpression(HtmlDsl<D> builder) {
		return builder
			.div( id("main") )
				.text("some text")
				.text("more text")
				.tag("custom-element")
				._tag("custom-element")
			._div();
	}
}
