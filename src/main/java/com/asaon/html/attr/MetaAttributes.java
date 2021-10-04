package com.asaon.html.attr;

import static com.asaon.html.attr.Attributes.attr;

import com.asaon.html.Attribute;

import java.nio.charset.Charset;

public class MetaAttributes {

	/** Specifies the character encoding for the HTML document. */
	public static Attribute charset(String value) { return attr("charset", value); }
	public static Attribute charset(Charset charset) { return charset(charset.name()); }

	/** Specifies the value associated with the http-equiv or name attribute. */
	public static Attribute content(String value) { return attr("content", value); }

	/** Provides an HTTP header for the information/value of the content attribute. */
	public static Attribute httpEquiv(String value) { return attr("http-equiv", value); }

	/** Specifies a name for the metadata. */
	public static Attribute name(String value) { return attr("name", value); }
}
