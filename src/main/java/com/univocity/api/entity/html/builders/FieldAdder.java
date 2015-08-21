package com.univocity.api.entity.html.builders;

public interface FieldAdder {

	HtmlPathStart addField(String fieldName);

	HtmlPathStart addPersistentField(String fieldName);
}
