package com.asaon.html;

import static com.asaon.html.Html.body;
import static com.asaon.html.Html.div;
import static com.asaon.html.Html.head;
import static com.asaon.html.Html.html;
import static com.asaon.html.Html.tag;
import static com.asaon.html.Html.text;
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
			Html.toString(
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
