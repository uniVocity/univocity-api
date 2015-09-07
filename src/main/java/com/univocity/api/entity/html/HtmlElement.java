package com.univocity.api.entity.html;

import java.util.*;

public interface HtmlElement {

	boolean isText();
	String tagName();
	HtmlElement parent();
	HtmlElement[] children();
	String attribute(String attributeName);
	Set<String> attributeNames();
	String text();
	HtmlElement nextSibling();
	HtmlElement previousSibling();
	HtmlElement grab();
	String id();
	Set<String> classes();
	boolean containsElementInHierarchy(HtmlElement element);
}
