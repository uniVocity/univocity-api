package com.univocity.api.entity.html;

import java.util.*;

public abstract class HtmlMatchingContext extends HtmlParsingContext{

	public abstract Map<String, String> getMatchedData();

	public abstract String getEntityName();

	public abstract String getDataStoreName() ;
}
