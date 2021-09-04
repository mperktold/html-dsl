package com.asaon.html.attr;

public interface InputAttributesBuilder<SELF extends InputAttributesBuilder<?>> extends AttributesBuilder<SELF> {

	default SELF type(String value) { return set("type", value); }

	default SELF name(String value) { return set("name", value); }

	default SELF value(String value) { return set("value", value); }

	default SELF readonly() { return set("readonly"); }

	default SELF disabled() { return set("disabled"); }

	default SELF required() { return set("required"); }
}
