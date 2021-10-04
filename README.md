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
        .div(Map.of("id", "sidenav"))
          .text("Sidenav content goes here")
        ._div()
        .div(Map.of("id", "main"))
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

<D extends HtmlDsl<D>> D bodyContent(HtmlDsl<D> dsl) {
  return dsl
    .div(Map.of("id", "sidenav"))
      .text("Sidenav content goes here")
    ._div()
    .div(Map.of("id", "main"))
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

Due to some generic quirks, `bodyContent` must declare the parameter as `HtmlDsl<D>` and not just `D`.

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

One big design driver of the API was to make it memory efficient. When streaming a HTML to the network through the DSL, there is no need to create an object per element. It achieves this by implementing the [Step Builder Pattern](https://java-design-patterns.com/patterns/step-builder/): The DSL only forwards the instructions to the interpreter and returns itself. The nested types which ensure correct structure do not reflect the actual return value, it's always the same instance.

### In-memory API included

Although the main purpose of this library is to offer A DSL for writing HTML to the network, it also comes with an in-memory representation of HTML nodes. This API is fully interoperable with the DSL, meaning you can use the DSL to build the in-memory representation, and also pipe in-memory through the DSL using `include`. Additionally, there are factory methods to construct the in-memory nodes directly, without going over the generic DSL:

```java
var node = html(
  head(),
  body(
    div(Map.of("id", "main"),
      text("Main content goes here")
    )
  )
);
```

You may prefer this API over the generic DSL for small HTML fragments. However, keep in mind that this is less efficient, especially for large HTML documents, because the whole HTML is built in memory.

### Experimental: Attribute Builder Lambdas

There is an experimental alternative style of defining element attributes by providing a lambda that receives an attribute builder. There are taylered attribute builder types for several elements, depending on which attribute a certain element supports.

```java
Html.intoString()
  .document()
    .div(a -> a.id("main")                    // every element supports "id"
      .input(a -> a.type("text").required())  // NOT every element supports "type" and "required", input does
    ._div()
  .end();
```

## Inspiration

The main inspiration for this project was [kotlinx.html](https://github.com/Kotlin/kotlinx.html). It's a typesafe Kotlin DSL for generating HTML, which also can be streamed to a Writer or otherwise interpreted on the fly.

There is also [j2html](https://github.com/tipsy/j2html) which does something similar in Java, but has one main APIs for building an in-memory representation and a distinct API for streaming.

So the goal for this project was to come up with a Java DSL with similar capabilities as the one for Kotlin while being limited to Java language features.


