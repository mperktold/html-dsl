package com.asaon.html.attr;

import java.util.Map;

public interface AttributesBuilder<SELF extends AttributesBuilder<?>> {

	SELF set(String name, String value);

	default SELF set(String name) { return set(name, name); }

	// Global Attributes
	// https://www.w3schools.com/tags/ref_standardattributes.asp

	/**	Specifies a shortcut key to activate/focus an element. */
	default SELF accesskey(String value) { return set("accesskey", value); }

	/**	Specifies one or more classnames for an element (refers to a class in a style sheet). */
	default SELF classes(String value) { return set("class", value); }

	/** Specifies whether the content of an element is editable or not. */
	default SELF contenteditable() { return set("contenteditable"); }

	/**	Used to store custom data private to the page or application. */
	default SELF data(String name, String value) { return set("data-" + name, value); }

	/** Specifies the text direction for the content in an element. */
	default SELF dir(String value) { return set("dir", value); }

	/**	Specifies whether an element is draggable or not. */
	default SELF draggable() { return set("draggable"); }

	/** Specifies that an element is not yet, or is no longer, relevant. */
	default SELF hidden() { return set("hidden"); }

	/** Specifies a unique id for an element. */
	default SELF id(String value) { return set("id", value); }

	/** Specifies the language of the element's content. */
	default SELF lang(String value) { return set("lang", value); }

	/** Specifies whether the element is to have its spelling and grammar checked or not. */
	default SELF spellcheck() { return set("spellcheck"); }

	/** Specifies an inline CSS style for an element. */
	default SELF style(String value) { return set("style", value); }

	/** Specifies the tabbing order of an element. */
	default SELF tabindex(int index) { return set("tabindex", Integer.toString(index)); }

	/** Specifies extra information about an element. */
	default SELF title(String value) { return set("title", value); }

	/** Specifies whether the content of an element should be translated or not. */
	default SELF translate() { return set("translate"); }

	/** Returns the attributes as a Map. */
	Map<String, String> build();
}
