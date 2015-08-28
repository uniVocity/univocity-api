package com.univocity.api.entity.html.builders;

public interface HtmlContentReader {

	void getHeadingText();

	void getHeadingText(int headingRowIndex);

	void getTextAbove();

	void getTextAbove(int numberOfElementsAbove);

	void getText();

	void getText(int numberOfSiblingsToInclude);

	void getPrecedingText();

	void getPrecedingText(int numberOfSiblingsToInclude);

	void getFollowingText();

	void getFollowingText(int numberOfSiblingsToInclude);

	void getAttribute(String attributeName);
}
