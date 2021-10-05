package com.asaon.html;

import static com.asaon.html.HtmlNodes.div;
import static com.asaon.html.attr.Attributes.id;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.asaon.html.dsl.Div;
import com.asaon.html.dsl.HtmlDsl;

import java.util.stream.Stream;

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
		assertEquals("""
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

	@Test
	void testIncludeNodeStream() {
		var htmlString = Html.intoString()
			.document()
				.html()
					.head()
					._head()
					.body()
						.include(Stream.of("one", "two", "three")
							.map(s -> div( id(s) ).content(s))
						)
					._body()
				._html()
			.end();
		assertEquals("""
			<html>
				<head>
				</head>
				<body>
					<div id="one">
						one
					</div>
					<div id="two">
						two
					</div>
					<div id="three">
						three
					</div>
				</body>
			</html>
			""",
			htmlString
		);
	}

	@Test
	void testIncludeLoop() {
		var htmlString = Html.intoString()
			.document()
				.html()
					.head()
					._head()
					.body()
						.include(dsl -> {
							String[] strings = { "one", "two", "three" };
							for (String s : strings)
								dsl = dsl.div( id(s) ).text(s)._div();
							return dsl;
						})
					._body()
				._html()
			.end();
		assertEquals("""
			<html>
				<head>
				</head>
				<body>
					<div id="one">
						one
					</div>
					<div id="two">
						two
					</div>
					<div id="three">
						three
					</div>
				</body>
			</html>
			""",
			htmlString
		);
	}
}
