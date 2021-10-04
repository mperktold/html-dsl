package com.asaon.html;

import java.util.Objects;

public record Attribute(String name, String value) {

	public Attribute {
		Objects.requireNonNull(name);
		Objects.requireNonNull(value);
	}
}
