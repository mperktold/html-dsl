package com.asaon.html;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NodeBuilderInteractionTest {

	@Test
	void testIncludeNodes() {
		var subNode = HtmlNodes.div(Map.of("id", "main"),
			HtmlNodes.text("some text"),
			HtmlNodes.text("more text"),
			HtmlNodes.tag("custom-element")
		);
		var htmlString = Html.stringBuilder()
			.html()
				.head()
				.headEnd()
				.body()
					.include(subNode)
				.bodyEnd()
				.htmlEnd()
			.build();
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
		var html = HtmlNodes.html(
			HtmlNodes.head(),
			HtmlNodes.body(
				HtmlNode.builder()
					.include(this::subExpression)
					.build()
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
			Html.toString(html)
		);
	}

	<B extends HtmlBuilder<B>> B subExpression(B builder) {
		return builder
			.div(a -> a.id("main"))
				.text("some text")
				.text("more text")
				.tag("custom-element")
				.tagEnd("custom-element")
			.divEnd();
	}
}
