package com.univocity.api.common;

import com.univocity.api.*;

import java.io.*;
import java.nio.charset.*;

public class InputFileQueue extends InputQueue<FileProvider> {

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
		offer(fileProvider);
	}

	@Override
	protected Reader open(FileProvider input) {
		return Univocity.provider().build(Reader.class, input);
	}
}
