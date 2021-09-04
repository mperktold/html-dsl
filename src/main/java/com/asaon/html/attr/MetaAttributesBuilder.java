package com.asaon.html.attr;

import java.nio.charset.Charset;

public interface MetaAttributesBuilder<SELF extends MetaAttributesBuilder<?>> extends AttributesBuilder<SELF> {

	/** Specifies the character encoding for the HTML document. */
	default SELF charset(String value) { return set("charset", value); }
	default SELF charset(Charset charset) { return charset(charset.name()); }

	/** Specifies the value associated with the http-equiv or name attribute. */
	default SELF content(String value) { return set("content", value); }

	/** Provides an HTTP header for the information/value of the content attribute. */
	default SELF httpEquiv(String value) { return set("http-equiv", value); }

	/** Specifies a name for the metadata. */
	default SELF name(String value) { return set("name", value); }
}
