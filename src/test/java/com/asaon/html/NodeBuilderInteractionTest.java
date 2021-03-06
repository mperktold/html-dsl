package com.asaon.html;

import static com.asaon.html.HtmlNodes.body;
import static com.asaon.html.HtmlNodes.div;
import static com.asaon.html.HtmlNodes.head;
import static com.asaon.html.HtmlNodes.html;
import static com.asaon.html.HtmlNodes.tag;
import static com.asaon.html.HtmlNodes.text;
import static com.asaon.html.attr.Attributes.id;

import com.asaon.html.dsl.HtmlDsl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NodeBuilderInteractionTest {

	@Test
	void testIncludeNodes() {
		var subNode = div( id("main") ).content(
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
		var html = html().content(
			head(),
			body().content(
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

	<D extends HtmlDsl<D>> D subExpression(D dsl) {
		return dsl
			.div( id("main") )
				.text("some text")
				.text("more text")
				.tag("custom-element")
				._tag("custom-element")
			._div();
	}
}
