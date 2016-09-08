/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.hierarchical;

import com.univocity.api.common.*;

import java.util.*;

public class PathReaderConfiguration {

	private final Map<String, ElementPaths> entities = new TreeMap<String, ElementPaths>();
	private boolean pathValidationEnabled = false; //TODO use default configuration class instead.

	public static class ElementPaths {
		boolean validatePaths = false;
		final Map<String, Integer> elements = new LinkedHashMap<String, Integer>();
		final Map<String, Integer> elementsOfList = new LinkedHashMap<String, Integer>();
		final Map<String, Integer> elementsOfCumulativeList = new LinkedHashMap<String, Integer>();
		final Map<String, Integer> elementsOfGroup = new LinkedHashMap<String, Integer>();

		public final Map<String, Integer> getElements() {
			return Collections.unmodifiableMap(elements);
		}

		public final Map<String, Integer> getElementsOfList() {
			return Collections.unmodifiableMap(elementsOfList);
		}

		public final Map<String, Integer> getElementsOfCumulativeList() {
			return Collections.unmodifiableMap(elementsOfCumulativeList);
		}

		public final Map<String, Integer> getElementsOfGroup() {
			return Collections.unmodifiableMap(elementsOfGroup);
		}

		public boolean isTargetDefined() {
			int pathCount = elements.size();
			pathCount += elementsOfGroup.size();
			pathCount += elementsOfList.size();
			pathCount += elementsOfCumulativeList.size();
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
		includeElement(entityName, pathToElement, 0);
	}

	public void includeElement(String entityName, String pathToElement, int parentsToInclude) {
		Args.positiveOrZero(parentsToInclude, "Number of parent elements to include");
		getPaths(entityName).elements.put(pathToElement, parentsToInclude);
	}

	public void includeElementsOfList(String entityName, String pathToList) {
		includeElementsOfList(entityName, pathToList, 0);
	}

	public void includeElementsOfList(String entityName, String pathToList, int parentsToInclude) {
		Args.positiveOrZero(parentsToInclude, "Number of parent elements to include");
		getPaths(entityName).elementsOfList.put(pathToList, parentsToInclude);
	}

	public void accumulateElementsOfList(String entityName, String pathToList) {
		accumulateElementsOfList(entityName, pathToList, 0);
	}

	public void accumulateElementsOfList(String entityName, String pathToList, int parentsToInclude) {
		Args.positiveOrZero(parentsToInclude, "Number of parent elements to include");
		getPaths(entityName).elementsOfCumulativeList.put(pathToList, parentsToInclude);
	}

	public void includeElementsOfGroup(String entityName, String pathToElement) {
		includeElementsOfGroup(entityName, pathToElement, 0);
	}

	public void includeElementsOfGroup(String entityName, String pathToElement, int parentsToInclude) {
		Args.positiveOrZero(parentsToInclude, "Number of parent elements to include");
		getPaths(entityName).elementsOfGroup.put(pathToElement, parentsToInclude);
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
