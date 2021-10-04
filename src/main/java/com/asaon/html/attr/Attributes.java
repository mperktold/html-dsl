package com.asaon.html.attr;

import com.asaon.html.Attribute;

public class Attributes {

	private Attributes() {}

	public static Attribute attr(String name, String value) {
		return new Attribute(name, value);
	}

	public static Attribute attr(String name) {
		return new Attribute(name, name);
	}

	/**	Specifies a shortcut key to activate/focus an element. */
	public static Attribute accesskey(String value) { return attr("accesskey", value); }

	/**	Specifies one or more classnames for an element (refers to a class in a style sheet). */
	public static Attribute classes(String value) { return attr("class", value); }

	/** Specifies whether the content of an element is editable or not. */
	public static Attribute contenteditable() { return attr("contenteditable"); }

	/**	Used to store custom data private to the page or application. */
	public static Attribute data(String name, String value) { return attr("data-" + name, value); }

	/** Specifies the text direction for the content in an element. */
	public static Attribute dir(String value) { return attr("dir", value); }

	/**	Specifies whether an element is draggable or not. */
	public static Attribute draggable() { return attr("draggable"); }

	/** Specifies that an element is not yet, or is no longer, relevant. */
	public static Attribute hidden() { return attr("hidden"); }

	/** Specifies a unique id for an element. */
	public static Attribute id(String value) { return attr("id", value); }

	/** Specifies the language of the element's content. */
	public static Attribute lang(String value) { return attr("lang", value); }

	/** Specifies whether the element is to have its spelling and grammar checked or not. */
	public static Attribute spellcheck() { return attr("spellcheck"); }

	/** Specifies an inline CSS style for an element. */
	public static Attribute style(String value) { return attr("style", value); }

	/** Specifies the tabbing order of an element. */
	public static Attribute tabindex(int index) { return attr("tabindex", Integer.toString(index)); }

	/** Specifies extra information about an element. */
	public static Attribute title(String value) { return attr("title", value); }

	/** Specifies whether the content of an element should be translated or not. */
	public static Attribute translate() { return attr("translate"); }
}
