package com.univocity.api.entity.html.builders;

interface BaseHtmlPath<T extends BaseHtmlPath<T>>{

	T followedByText(String text);

	T precededByText(String text);

	T followedBy(String elementName);

	T followedBy(String elementName, int distance);

	T followedImmediatelyBy(String elementName);

	T precededBy(String elementName);

	T precededBy(String elementName, int distance);

	T precededImmediatelyBy(String elementName);

	T childOf(String elementName);

	T containedBy(String elementName);

	T containedBy(String elementName, int depthLimit);

	T underHeader(String headerElementName);

	T under(String elementName);

	T parentOf(String elementName);

	T containing(String ... pathOfElementNames);

	T containing(String elementName);

	T containing(String elementName, int depthLimit);

	T withText(String textContent);

	T withMatchingText(String textContent);

	T classes(String firstCssClass, String... otherCssClasses);

	T attribute(String attributeName, String attributeValue);

	T id(String idValue);

}
