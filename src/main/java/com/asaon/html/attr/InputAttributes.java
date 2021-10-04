package com.asaon.html.attr;

import static com.asaon.html.attr.Attributes.attr;

import com.asaon.html.Attribute;

public class InputAttributes {

	public static Attribute type(String value) { return attr("type", value); }

	public static Attribute name(String value) { return attr("name", value); }

	public static Attribute value(String value) { return attr("value", value); }

	public static Attribute readonly() { return attr("readonly"); }

	public static Attribute disabled() { return attr("disabled"); }

	public static Attribute required() { return attr("required"); }
}
