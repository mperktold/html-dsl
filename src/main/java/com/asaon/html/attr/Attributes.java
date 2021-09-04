package com.asaon.html.attr;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Attributes {

	private Attributes() {}

	public static Map<String, String> globalAttrs(Consumer<AttributesBuilder<?>> consumer) {
		return buildAttrs(consumer);
	}

	public static Map<String, String> metaAttrs(Consumer<MetaAttributesBuilder<?>> consumer) {
		return buildAttrs(consumer);
	}

	public static Map<String, String> inputAttrs(Consumer<InputAttributesBuilder<?>> consumer) {
		return buildAttrs(consumer);
	}

	private static Map<String, String> buildAttrs(Consumer<? super BuilderImpl> consumer) {
		var builder = new BuilderImpl();
		consumer.accept(builder);
		return builder.build();
	}

	private static class BuilderImpl implements
		MetaAttributesBuilder<BuilderImpl>,
		InputAttributesBuilder<BuilderImpl>
	{
		private final LinkedHashMap<String, String> attrs = new LinkedHashMap<>();

		@Override
		public BuilderImpl set(String name, String value) {
			attrs.put(name, value);
			return this;
		}

		@Override
		public BuilderImpl name(String value) {
			return MetaAttributesBuilder.super.name(value);
		}

		@Override
		public Map<String, String> build() {
			return Map.copyOf(attrs);
		}
	}
}
