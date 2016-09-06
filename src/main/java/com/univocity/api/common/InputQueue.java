package com.univocity.api.common;

import java.io.*;
import java.util.*;

/**
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public abstract class InputQueue<T> extends ReaderProvider {

	private final Queue<T> inputQueue = new LinkedList<T>();

	public InputQueue() {

	}

	public final boolean isEmpty() {
		return inputQueue.isEmpty();
	}

	public final int size() {
		return inputQueue.size();
	}

	protected final void offer(T input){
		inputQueue.offer(input);
	}

	@Override
	public final Reader getResource() {
		T input = inputQueue.poll();

		if (input == null) {
			throw new IllegalStateException("No input to process");
		}
		return open(input);
	}

	protected abstract Reader open(T input);
}
