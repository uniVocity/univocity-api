package com.univocity.api.entity.xml;

import com.univocity.api.common.*;

public class XmlQueryConfiguration extends XmlReadingConfiguration {

	private final String query;

	public XmlQueryConfiguration(String queryURL) {
		Args.notBlank(queryURL, "XML query URL");
		this.query = queryURL;
	}

	public final String getQuery() {
		return query;
	}

}
