package com.univocity.api.common;

import java.io.*;

public class InputReaderQueue extends InputQueue<ReaderProvider> {

	public void add(final ReaderProvider readerProvider) {
		offer(readerProvider);
	}

	public void add(final Reader reader) {
		offer(new ReaderProvider() {
			@Override
			public Reader getResource() {
				return reader;
			}
		});
	}

	@Override
	protected Reader open(ReaderProvider input) {
		return input.getResource();
	}
}
