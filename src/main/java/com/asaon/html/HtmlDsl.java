package com.asaon.html;

import static com.asaon.html.attr.Attributes.globalAttrs;
import static com.asaon.html.attr.Attributes.metaAttrs;

import com.asaon.html.attr.AttributesBuilder;
import com.asaon.html.attr.MetaAttributesBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public interface HtmlDsl<SELF extends HtmlDsl<SELF>> {
	Tag<SELF, ?> tag(String tag, Map<String, String> attrs);
	default Tag<SELF, ?> tag(String tag, Consumer<AttributesBuilder<?>> attrs) { return tag(tag, globalAttrs(attrs)); }
	default Tag<SELF, ?> tag(String tag) { return tag(tag, Map.of()); }

	Html<SELF, ?> html();
	Head<SELF, ?> head();
	Body<SELF, ?> body();
	Meta<SELF, ?> meta(Map<String, String> attrs);
	default Meta<SELF, ?> meta(Consumer<MetaAttributesBuilder<?>> attrs) { return meta(metaAttrs(attrs)); }
	default Meta<SELF, ?> meta() { return meta(Map.of()); }

	Div<SELF, ?> div(Map<String, String> attrs);
	default Div<SELF, ?> div(Consumer<AttributesBuilder<?>> attrs) { return div(globalAttrs(attrs)); }
	default Div<SELF, ?> div() { return div(Map.of()); }
	Div<SELF, ?> span(Map<String, String> attrs);
	default Div<SELF, ?> span(Consumer<AttributesBuilder<?>> attrs) { return span(globalAttrs(attrs)); }
	default Div<SELF, ?> span() { return div(Map.of()); }

	default SELF include(HtmlNode... nodes) {
		return include(Arrays.asList(nodes));
	}
	default SELF include(Iterable<HtmlNode> nodes) {
		@SuppressWarnings("unchecked") SELF self = (SELF)this;
		for (HtmlNode n : nodes) n.addTo(self);
		return self;
	}

	default SELF include(UnaryOperator<SELF> subExpr) {
		@SuppressWarnings("unchecked") SELF self = (SELF)this;
		return subExpr.apply(self);
	}

	SELF text(String content);

	interface Tag<PARENT, SELF extends Tag<PARENT, SELF>> extends HtmlDsl<SELF> { PARENT _tag(String tag); }
	interface Html<PARENT, SELF extends Html<PARENT, SELF>> extends HtmlDsl<SELF> { PARENT _html(); }
	interface Head<PARENT, SELF extends Head<PARENT, SELF>> extends HtmlDsl<SELF> { PARENT _head(); }
	interface Body<PARENT, SELF extends Body<PARENT, SELF>> extends HtmlDsl<SELF> { PARENT _body(); }
	interface Meta<PARENT, SELF extends Meta<PARENT, SELF>> extends HtmlDsl<SELF> { PARENT _meta(); }
	interface Div<PARENT, SELF extends Div<PARENT, SELF>> extends HtmlDsl<SELF> { PARENT _div(); }
	interface Span<PARENT, SELF extends Span<PARENT, SELF>> extends HtmlDsl<SELF> { PARENT _span(); }
	interface Document<T, SELF extends Document<T, SELF>> extends HtmlDsl<SELF> { T _document(); }
}