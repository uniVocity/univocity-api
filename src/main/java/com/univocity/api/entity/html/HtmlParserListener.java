package com.univocity.api.entity.html;


public abstract class HtmlParserListener {

	public void parsingStarted(HtmlParsingContext context) {
	}

	public void elementVisited(HtmlElement element, HtmlParsingContext context) {
	}

	public void elementMatched(HtmlElement element, HtmlParsingContext context) {
	}

	public void parsingEnded(HtmlParsingContext context) {
	}
}
