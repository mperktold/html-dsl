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

	@Test
	void testBuilderForString() {
		var htmlString = Html.stringBuilder()
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
			htmlString
		);
	}

	@Test
	void testBuilderViaLambda() {
		var htmlString = Html.toString(builder -> builder
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
			htmlString
		);
	}

	@Test
	void testSubExpression() {
		var htmlString = Html.stringBuilder()
			.html()
				.head()
				.headEnd()
				.body()
					.include(this::subExpression)
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

	<B extends HtmlBuilder<B>> B subExpression(B builder) {
		return builder
			.div(a -> a.id("main"))
				.text("some text")
				.text("more text")
				.tag("custom-element")
				.tagEnd("custom-element")
			.divEnd();
	}

	@Test
	void testSubExpressionAsRoot() {
		Assertions.assertEquals("""
			<div id="main">
				some text
				more text
				<custom-element>
				</custom-element>
			</div>
			""",
			"Doesn't compile because of Root's SELF type param"
			//Html.toString(builder -> subExpression(builder))
		);
	}
}
