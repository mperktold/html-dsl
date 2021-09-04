package com.asaon.html;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HtmlBuilderTest {

	@Test
	void testAppendToStringBuilder() {
		var sb = new StringBuilder();
		HtmlBuilder.create(new HtmlAppender<>(sb))
			.html()
				.head()
				.headEnd()
				.body()
					.div(a -> a.id("main"))
						.text("some text")
						.text("more text")
						.tag("custom-element")
						.tagEnd("custom-element")
					.divEnd()
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
			sb.toString()
		);
	}
}
