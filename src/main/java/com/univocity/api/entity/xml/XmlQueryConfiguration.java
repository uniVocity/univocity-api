package com.univocity.api.entity.xml;

import com.univocity.api.common.*;
import com.univocity.api.entity.xml.XmlDataStoreConfiguration.ElementPaths;

public class XmlQueryConfiguration {

	private final String query;

	private final ElementPaths queryResultPaths = new ElementPaths();

	private final String name;
	
	
	public XmlQueryConfiguration(String name, String queryURL) {
		Args.notBlank(name, "XML query name");
		Args.notBlank(name, "XML query URL");
		this.name = name;
		this.query = queryURL;
	}

	public void includeElement(String entityName, String pathToElement) {
		queryResultPaths.elements.add(pathToElement);
	}

	public void includeElementsOfList(String entityName, String pathToList) {
		queryResultPaths.elementsOfList.add(pathToList);
	}

	public void includeElementsOfGroup(String entityName, String pathToElement) {
		queryResultPaths.elementsOfGroup.add(pathToElement);
	}

	public final String getQuery() {
		return query;
	}

	public final ElementPaths getQueryResultPaths() {
		return queryResultPaths;
	}

	public final String getName() {
		return name;
	}

}
