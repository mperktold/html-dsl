package com.asaon.html;

import java.util.Arrays;
import java.util.function.Function;

public interface HtmlDsl<SELF extends HtmlDsl<SELF>> {
	Tag<SELF, ?> tag(String tag, Attribute... attrs);

	Html<SELF, ?> html();
	Head<SELF, ?> head();
	Body<SELF, ?> body();
	Meta<SELF, ?> meta(Attribute... attrs);

	Div<SELF, ?> div(Attribute... attrs);
	Div<SELF, ?> span(Attribute... attrs);

	default SELF include(HtmlNode... nodes) {
		return include(Arrays.asList(nodes));
	}
	default SELF include(Iterable<HtmlNode> nodes) {
		@SuppressWarnings("unchecked") SELF self = (SELF)this;
		for (HtmlNode n : nodes) n.addTo(self);
		return self;
	}

	default <R> R include(Function<? super SELF, ? extends R> subExpr) {
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
	interface Document<T, SELF extends Document<T, SELF>> extends HtmlDsl<SELF> { T end(); }
}
