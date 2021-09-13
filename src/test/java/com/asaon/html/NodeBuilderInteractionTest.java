package com.asaon.html;

import static com.asaon.html.HtmlNodes.body;
import static com.asaon.html.HtmlNodes.div;
import static com.asaon.html.HtmlNodes.head;
import static com.asaon.html.HtmlNodes.html;
import static com.asaon.html.HtmlNodes.tag;
import static com.asaon.html.HtmlNodes.text;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NodeBuilderInteractionTest {

	@Test
	void testIncludeNodes() {
		var subNode = div(Map.of("id", "main"),
			text("some text"),
			text("more text"),
			tag("custom-element")
		);
		var htmlString = Html.intoString()
			.document()
				.html()
					.head()
					.headEnd()
					.body()
						.include(subNode)
					.bodyEnd()
				.htmlEnd()
			.documentEnd();
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

	<B extends HtmlDsl<B>> B subExpression(HtmlDsl<B> builder) {
		return builder
			.div(a -> a.id("main"))
				.text("some text")
				.text("more text")
				.tag("custom-element")
				.tagEnd("custom-element")
			.divEnd();
	}
}
