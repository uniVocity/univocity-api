package com.univocity.api.entity.html.builders;

public interface HtmlGroup extends BaseHtmlPath<HtmlGroup>, BaseHtmlPathStart<HtmlGroup>{

	PartialHtmlPath buildPartialPath();

	HtmlPath buildPathToField(String fieldName);

	HtmlGroup setConstantField(String constantFieldName, String constantValue);
}