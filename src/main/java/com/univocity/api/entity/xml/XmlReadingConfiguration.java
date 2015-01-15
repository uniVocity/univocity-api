/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.xml;

import java.util.*;

public class XmlReadingConfiguration {

	private final Map<String, ElementPaths> entities = new TreeMap<String, ElementPaths>();

	public static class ElementPaths {
		final Set<String> elements = new LinkedHashSet<String>();
		final Set<String> elementsOfList = new LinkedHashSet<String>();
		final Set<String> elementsOfGroup = new LinkedHashSet<String>();

		public final Set<String> getElements() {
			return Collections.unmodifiableSet(elements);
		}

		public final Set<String> getElementsOfList() {
			return Collections.unmodifiableSet(elementsOfList);
		}

		public final Set<String> getElementsOfGroup() {
			return Collections.unmodifiableSet(elementsOfGroup);
		}

		public boolean isTargetDefined() {
			int pathCount = elements.size();
			pathCount += elementsOfGroup.size();
			pathCount += elementsOfList.size();
			return pathCount > 0;

		}
	}

	public void includeElement(String entityName, String pathToElement) {
		getPaths(entityName).elements.add(pathToElement);
	}

	public void includeElementsOfList(String entityName, String pathToList) {
		getPaths(entityName).elementsOfList.add(pathToList);
	}

	public void includeElementsOfGroup(String entityName, String pathToElement) {
		getPaths(entityName).elementsOfGroup.add(pathToElement);
	}

	private ElementPaths getPaths(String entityName) {
		ElementPaths paths = entities.get(entityName);
		if (paths == null) {
			paths = new ElementPaths();
			entities.put(entityName, paths);
		}
		return paths;
	}

	public final Map<String, ElementPaths> getEntities() {
		return Collections.unmodifiableMap(entities);
	}

	private Set<String> toSetOfNames(Map<String, ElementPaths> map) {
		Set<String> out = new TreeSet<String>();
		for (String name : map.keySet()) {
			out.add(name.trim().toLowerCase());
		}
		return out;
	}

	void validateNamesAreUnique(XmlReadingConfiguration other) {
		Set<String> myNames = toSetOfNames(this.entities);
		Set<String> otherNames = toSetOfNames(other.entities);

		myNames.retainAll(otherNames);
		if (!myNames.isEmpty()) {
			throw new IllegalArgumentException("Duplicate XML entity/query names: " + myNames);
		}
	}
}
