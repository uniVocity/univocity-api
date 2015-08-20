package com.univocity.api.entity.html.builders;

public interface HtmlContentReader {

	void mapHeadingText();

	void mapHeadingText(int headingRowIndex);

	void mapTextAbove();

	void mapTextAbove(int numberOfElementsAbove);

	void getText();

	void mapPrecedingText();

	void mapFollowingText();

	void getAttribute(String attributeName);
}
