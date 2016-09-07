package com.univocity.api.common;

import java.io.*;
import java.util.*;

/**
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public abstract class InputQueue<T> extends ReaderProvider {

	private final Map<T, Map<String, Object>> variables = new HashMap<T, Map<String, Object>>();

	private final Queue<T> inputQueue = new LinkedList<T>();

	private Map<String, Object> currentVariables = null;
	private T lastEntry;

	public InputQueue() {

	}

	public final boolean isEmpty() {
		return inputQueue.isEmpty();
	}

	public final int size() {
		return inputQueue.size();
	}

	protected final void offer(T input) {
		inputQueue.offer(input);
		this.lastEntry = input;
	}

	public void assignVariableToLastEntry(String variable, Object value) {
		if (lastEntry == null) {
			throw new IllegalArgumentException("Can't assign value '" + value + "' to variable '" + variable + "' bound to last entry of input queue. Input queue is empty.");
		}
		assignVariableToEntry(lastEntry, variable, value);
	}

	public void assignVariablesToLastEntry(Map<String, Object> variables) {
		if (lastEntry == null) {
			throw new IllegalArgumentException("Can't assign variables " + variables + " to last entry of input queue. Input queue is empty.");
		}
		for (Map.Entry<String, Object> e : variables.entrySet()) {
			assignVariableToEntry(lastEntry, e.getKey(), e.getValue());
		}
	}

	private void assignVariableToEntry(T entry, String variable, Object value) {
		Map<String, Object> entryVars = variables.get(entry);
		if (entryVars == null) {
			entryVars = new HashMap<String, Object>();
			variables.put(entry, entryVars);
		}
		entryVars.put(variable, value);
	}

	@Override
	public final Reader getResource() {
		T input = inputQueue.poll();

		if (input == null) {
			throw new IllegalStateException("No input to process");
		}

		currentVariables = variables.remove(input);

		return open(input);
	}

	public Map<String, Object> getCurrentVariables() {
		if (currentVariables == null) {
			return Collections.emptyMap();
		}
		return currentVariables;
	}

	protected abstract Reader open(T input);

}
