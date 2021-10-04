package com.asaon.html;

import static com.asaon.html.attr.Attributes.id;

import com.asaon.html.dsl.Div;
import com.asaon.html.dsl.HtmlDsl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HtmlDslTest {

	@Test
	void testAppendToStringBuilder() {
		var sb = new StringBuilder();
		Html.appendingTo(sb)
			.document()
				.html()
					.head()
					._head()
					.body()
						.div( id("main") )
							.text("some text")
							.text("more text")
							.tag("custom-element")
							._tag("custom-element")
						._div()
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
			sb.toString()
		);
	}

	@Test
	void testBuilderForString() {
		var htmlString = Html.intoString()
			.document()
				.html()
					.head()
					._head()
					.body()
						.div( id("main") )
							.text("some text")
							.text("more text")
							.tag("custom-element")
							._tag("custom-element")
						._div()
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
	void testBuilderViaLambda() {
		var htmlString = Html.buildString(builder -> builder
			.html()
				.head()
				._head()
				.body()
					.div( id("main") )
						.text("some text")
						.text("more text")
						.tag("custom-element")
						._tag("custom-element")
					._div()
				._body()
			._html()
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
		var htmlString = Html.intoString()
			.document()
				.html()
					.head()
					._head()
					.body()
						.include(this::subExpression)
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

	<D extends HtmlDsl<D>> D subExpression(D builder) {
		return builder
			.div( id("main") )
				.text("some text")
				.text("more text")
				.tag("custom-element")
				._tag("custom-element")
			._div();
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
			Html.buildString(this::subExpression)
		);
	}

	@Test
	void testUnbalancedSubExpression() {
		var htmlString = Html.intoString()
			.document()
				.html()
					.head()
					._head()
					.body()
						.include(this::openSubExpression)
							.text("some text")
							.text("more text")
							.tag("custom-element")
							._tag("custom-element")
						.include(this::closeSubExpression)
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

	<D extends HtmlDsl<D>> Div<D> openSubExpression(D dsl) {
		return dsl
			.div( id("main") );
	}

	<D extends HtmlDsl<D>> D closeSubExpression(Div<D> dsl) {
		return dsl
			._div();
	}
}
