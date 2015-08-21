package com.univocity.api.entity.html.builders;

public interface HtmlGroup extends BaseHtmlPath<HtmlGroup>, BaseHtmlPathStart<HtmlGroup>, FieldAdder{

	PartialHtmlPath buildPartialPath();

	HtmlGroup setConstantField(String constantFieldName, String constantValue);
}