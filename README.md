# html-dsl
DSL for generating HTML in Java

**Note:** This project is in a draft status. Don't use it in production!

## Introduction

This library gives you a fluent DSL for generating HTML like this:

```java
String htmlString = Html
  .stringBuilder()
    .html()
      .body()
        .div(Map.of("id", "sidenav"))
          .text("Sidenav content goes here")
        .divEnd()
        .div(Map.of("id", "main"))
          .text("Main content goes here")
        .divEnd()
      .bodyEnd()
    .htmlEnd()
  .build();
```

## Main features

### Typesafety

The DSL helps you to get the HTML structure correct, i.e. not to forget end tags. Every opening tag returns a nested type, and every end tag returns the "parent" type. The type returned by the tag opening method only provides one tag ending method for exactly that tag, and only the outermost type provides the `build()` method which you should invoke to complete the HTML. This means that as long as you have a single chain of method calls that ends with `build()`, all tags are guaranteed to be ended correctly.

```java
String htmlString = Html
  .stringBuilder()  // : Root<String, ...>
    .body()         // : Body<Root<...>>
      .div()        // : Div<Body<Root<...>>>
        .text()     // : Div<Body<Root<...>>>
    //.bodyEnd()    // not possible, since type Div does not provide method bodyEnd(), only Body does
    //.build()      // not possible, since type Div does not provide method build(), only Root does
      .divEnd()     // : Body<Root<...>>
    .bodyEnd()      // : Root<String, ...>
  .build();         // : String
```

## Inspiration

The main inspiration for this project was [kotlinx.html](https://github.com/Kotlin/kotlinx.html). It's a typesafe Kotlin DSL for generating HTML, which also can be streamed to a Writer or otherwise interpreted on the fly.

There is also [j2html](https://github.com/tipsy/j2html) which does something similar in Java, but has one main APIs for building an in-memory representation and a distinct API for streaming.

So the goal for this project was to come up with a Java DSL with similar capabilities as the one for Kotlin while being limited to Java language features.


