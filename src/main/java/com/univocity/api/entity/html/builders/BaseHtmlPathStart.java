package com.univocity.api.entity.html.builders;

public interface BaseHtmlPathStart <T extends BaseHtmlPath<T>> {

	T match(String elementName);
}
