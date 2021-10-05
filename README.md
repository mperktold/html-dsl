# html-dsl
DSL for generating HTML in Java

**Note:** This project is in a draft status. Don't use it in production!

## Introduction

This library gives you a fluent DSL for generating HTML like this:

```java
String htmlString = Html.intoString()
  .document()
    .html()
      .body()
        .div( id("sidenav") )
          .text("Sidenav content goes here")
        ._div()
        .div( id("main") )
          .text("Main content goes here")
        ._div()
      ._body()
    ._html()
  .end();
```

This results in the following HTML string:

```html
<html>
    <body>
        <div id="sidenav">
          Sidenav content goes here
        </div>
        <div id="main">
          Main main goes here
        </div>
    </body>
</html>
```

## Main features

### Similar to Actual HTML

As you can see in the previous example, the code defining the HTML looks very similar to the resulting HTML, so you can 'see' the HTML in your code.

### Flexible Interpretation

The definition of the HTML structure is decoupled from its interpretation. In the previous example, we used `Html.intoString()`, which interprets the HTML defined using the DSL into a string, which is returned when calling `end()`. There are other built-in interpreters for streaming to a `Writer`/`Appendable` and for generating an in-memory representation of the node tree. You can also define your own interpreter by implementing the `HtmlInterpreter` interface.

### Composable

The DSL offers an `include()` method which accepts a function defining some inner HTML. Thus, you can split large HTML documents into smaller functions and compose them using `include()`:

```java

<D extends HtmlDsl<D>> D bodyContent(D dsl) {
  return dsl
    .div( id("sidenav") )
      .text("Sidenav content goes here")
    ._div()
    .div( id("main") )
      .text("Main content goes here")
    ._div();
}

Html.intoString()
  .document()
    .body()
      .include(this::bodyContent)
    ._body()
  .end()
```

### Typesafe

The DSL helps you to get the HTML structure correct, i.e. not to forget end tags. Every opening tag returns a nested type, and every end tag returns the "parent" type. The type returned by the tag opening method only provides one tag ending method for exactly that tag, and only the outermost type provides the `end()` method which you should invoke to complete the HTML document. This means that as long as you have a single chain of method calls that ends with `end()`, all tags are guaranteed to be ended correctly.

```java
String htmlString = Html.intoString()
  .document()       // : Document<String, ...>
    .body()         // : Body<Document<...>>
      .div()        // : Div<Body<Document<...>>>
        .text()     // : Div<Body<Document<...>>>
    //._body()      // not possible, since type Div does not provide method _body(), only Body does
    //._document()  // not possible, since type Div does not provide method _document(), only Document does
      ._div()       // : Body<Document<...>>
    ._body()        // : Document<String, ...>
  .end();           // : String
```

### Efficient

One big design driver of the API was to make it memory efficient. When streaming a HTML to the network through the DSL, there is no need to keep all the HTML structure in memory. The API does create an object per element, but as soon as the tag is closed via `_tag` and the like, the object is not referenced anymore and can be garbage collected. This should allow to create huge HTML documents without out-of-memory problems.

### In-memory API included

Although the main purpose of this library is to offer A DSL for writing HTML to the network, it also comes with an in-memory immutable representation of HTML nodes, including factory methods for constructing them:

```java
var node = html().content(
  head(),
  body().content(
    div( id("main") ).content(
      text("Main content goes here")
    )
  )
);
```

You may prefer this API over the generic DSL for small HTML fragments. However, it builds the whole HTML tree in memory, which could be a problem for large HTML documents.

The in-memory API is fully interoperable with the DSL.

Meaning, you can use the DSL to build the in-memory representation:

```java
List<HtmlNode> nodes = HtmlNode.interpreter()
  .document()
    .div( id("one") ).text("Div one")._div()
    .div( id("two") ).text("Div two")._div()
  .end();
```

Plus, you can pipe nodes through the DSL using `include` overloads for varargs, `Iterable` and `Stream` of nodes:

```
Html.intoString()
  .document()
    .body()
      .include(
        div( id("one") ).content("Div one"),
        div( id("two") ).content("Div two")
      )
    ._body()
  .end();
```

## Inspiration

The main inspiration for this project was [kotlinx.html](https://github.com/Kotlin/kotlinx.html). It's a typesafe Kotlin DSL for generating HTML, which also can be streamed to a Writer or otherwise interpreted on the fly.

There is also [j2html](https://github.com/tipsy/j2html) which does something similar in Java, but has one main APIs for building an in-memory representation and a distinct API for streaming.

So the goal for this project was to come up with a Java DSL with similar capabilities as the one for Kotlin while being limited to Java language features.


