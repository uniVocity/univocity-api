package com.univocity.api.entity.html.builders;

public interface PartialHtmlGroup extends BaseHtmlPath<PartialHtmlGroup>, BaseHtmlPathStart<PartialHtmlGroup>{

	HtmlGroup endAt(String elementName);

}
