package com.asaon.html;

import static com.asaon.html.HtmlNodes.body;
import static com.asaon.html.HtmlNodes.div;
import static com.asaon.html.HtmlNodes.head;
import static com.asaon.html.HtmlNodes.html;
import static com.asaon.html.HtmlNodes.tag;
import static com.asaon.html.HtmlNodes.text;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class HtmlNodeTest {

	@Test
	void testNodeBuilder() {
		List<HtmlNode> nodes = HtmlNode.interpreter()
			.document()
				.html()
					.head()
					._head()
					.body()
						.div(Map.of("id", "main"))
							.text("some text")
							.text("more text")
							.tag("custom-element")
							._tag("custom-element")
						._div()
					._body()
				._html()
			._document();
		assertEquals(
			List.of(html(
				head(),
				body(
					div(Map.of("id", "main"),
						text("some text"),
						text("more text"),
						tag("custom-element")
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
			HtmlNode.toString(
				html(
					head(),
					body(
						div(Map.of("id", "main"),
							text("some text"),
							text("more text"),
							tag("custom-element")
						)
					)
				)
			)
		);
	}
}
