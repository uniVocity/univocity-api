package com.univocity.api.entity.html.builders;

public interface PartialHtmlPath extends BaseHtmlPath<PartialHtmlPath>, PartialHtmlPathStart {

	HtmlPathStart addField(String fieldName);

	PartialHtmlPath copyPath();

}
