package com.univocity.api.common;

import com.univocity.api.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class InputFileQueue extends ReaderProvider {

	private final Queue<FileProvider> inputQueue = new LinkedList<FileProvider>();

	public InputFileQueue() {

	}

	public boolean isEmpty() {
		return inputQueue.isEmpty();
	}

	public int size() {
		return inputQueue.size();
	}

	public void addFile(File file) {
		this.addFile(file, (Charset) null);
	}

	public void addFile(File file, String encoding) {
		addFile(new FileProvider(file, encoding));
	}

	public void addFile(File file, Charset encoding) {
		this.addFile(new FileProvider(file, encoding));
	}

	public void addFile(String filePath) {
		this.addFile(filePath, (Charset) null);
	}

	public void addFile(String filePath, String encoding) {
		addFile(new File(filePath, encoding));
	}

	public void addFile(String filePath, Charset encoding) {
		addFile(new FileProvider(filePath, encoding));
	}

	public void addFile(FileProvider fileProvider) {
		inputQueue.offer(fileProvider);
	}

	@Override
	public Reader getResource() {
		FileProvider fileProvider = inputQueue.poll();
		if (fileProvider == null) {
			throw new IllegalStateException("No files to process");
		}
		return Univocity.provider().build(Reader.class, fileProvider);
	}
}
