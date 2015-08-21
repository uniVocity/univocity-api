package com.univocity.api.entity.html.builders;

public interface HtmlContentReader {

	void getHeadingText();

	void getHeadingText(int headingRowIndex);

	void getTextAbove();

	void getTextAbove(int numberOfElementsAbove);

	void getText();

	void getPrecedingText();

	void getFollowingText();

	void getAttribute(String attributeName);
}
