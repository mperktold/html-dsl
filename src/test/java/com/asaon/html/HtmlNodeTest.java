package com.asaon.html;

import static com.asaon.html.HtmlNodes.text;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class HtmlNodeTest {

	@Test
	void testNodeBuilder() {
		List<HtmlNode> nodes = HtmlNode.builder()
			.html()
				.head()
				.headEnd()
				.body()
					.div(Map.of("id", "main"))
						.text("some text")
						.text("more text")
						.tag("custom-element")
						.tagEnd("custom-element")
					.divEnd()
				.bodyEnd()
				.htmlEnd()
			.build();
		assertEquals(
			List.of(HtmlNodes.html(
				HtmlNodes.head(),
				HtmlNodes.body(
					HtmlNodes.div(Map.of("id", "main"),
						HtmlNodes.text("some text"),
						HtmlNodes.text("more text"),
						HtmlNodes.tag("custom-element")
					)
				)
			)),
			nodes
		);
	}

	@Test
	void testNodesToString() {
		assertEquals("""
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
			Html.toString(
				HtmlNodes.html(
					HtmlNodes.head(),
					HtmlNodes.body(
						HtmlNodes.div(Map.of("id", "main"),
							text("some text"),
							text("more text"),
							HtmlNodes.tag("custom-element")
						)
					)
				)
			)
		);
	}
}
