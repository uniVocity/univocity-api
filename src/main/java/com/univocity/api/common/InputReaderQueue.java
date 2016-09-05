package com.univocity.api.common;

import java.io.*;
import java.util.*;

public class InputReaderQueue extends ReaderProvider {

	private final Queue<ReaderProvider> inputQueue = new LinkedList<ReaderProvider>();

	public InputReaderQueue() {

	}

	public boolean isEmpty() {
		return inputQueue.isEmpty();
	}

	public int size() {
		return inputQueue.size();
	}

	public void add(final ReaderProvider readerProvider) {
		inputQueue.add(readerProvider);
	}

	public void add(final Reader reader) {
		inputQueue.add(new ReaderProvider() {
			@Override
			public Reader getResource() {
				return reader;
			}
		});
	}

	protected ReaderProvider poll(){
		return inputQueue.poll();
	}

	@Override
	public Reader getResource() {
		ReaderProvider readerProvider = poll();
		if (readerProvider == null) {
			throw new IllegalStateException("No input to process");
		}
		return readerProvider.getResource();
	}
}
