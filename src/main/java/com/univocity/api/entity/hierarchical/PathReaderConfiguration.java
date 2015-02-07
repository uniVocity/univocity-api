/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.hierarchical;

import java.util.*;

public class PathReaderConfiguration {

	private final Map<String, ElementPaths> entities = new TreeMap<String, ElementPaths>();
	private boolean pathValidationEnabled = false; //TODO use default configuration class instead.
	
	public static class ElementPaths {
		boolean validatePaths = false;
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

		public boolean isPathValidationEnabled() {
			return this.validatePaths;
		}
	}

	public final boolean isPathValidationEnabled() {
		return pathValidationEnabled;
	}

	public final void setPathValidationEnabled(boolean pathValidationEnabled) {
		this.pathValidationEnabled = pathValidationEnabled;
	}

	public void setPathValidationEnabled(String entityName, boolean validatePaths) {
		getPaths(entityName).validatePaths = validatePaths;
	}

	public boolean isPathValidationEnabled(String entityName) {
		return getPaths(entityName).validatePaths;
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
		paths.validatePaths = pathValidationEnabled;
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

	void validateNamesAreUnique(PathReaderConfiguration other) {
		Set<String> myNames = toSetOfNames(this.entities);
		Set<String> otherNames = toSetOfNames(other.entities);

		myNames.retainAll(otherNames);
		if (!myNames.isEmpty()) {
		}
	}
}
