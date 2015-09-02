/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.common;

import java.io.*;
import java.nio.charset.*;

/**
 * A FileProvider validates and stores the minimum information required by uniVocity to locate and manipulate files.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public final class FileProvider {

	private final File file;
	private final Charset encoding;
	private final String filePath;

	/**
	 * Creates a provider for the file to be open/written using the default system encoding.
	 * @param file the File
	 */
	public FileProvider(File file) {
		this(file, (Charset) null);
	}

	/**
	 * Creates a provider for the file to be open/written using a given encoding
	 * @param file the File to be used.
	 * @param encoding the name of the encoding that must be used to read from/write to the given file.
	 */
	public FileProvider(File file, String encoding) {
		this(file, getEncoding(encoding));
	}

	/**
	 * Creates a provider for the file to be open/written using a given encoding
	 * @param file the File to be used.
	 * @param encoding the encoding that must be used to read from/write to the given file.
	 */
	public FileProvider(File file, Charset encoding) {
		if (file == null) {
			throw new IllegalArgumentException("File cannot be null");
		}
		this.encoding = getEncoding(encoding);
		this.file = file;
		this.filePath = file.getAbsolutePath();
	}

	/**
	 * Creates a provider for the file, represented by a path, to be open/written using the default system encoding.
	 * @param file the path to a file. It can either be the path to a file in the file system or a resource in the classpath.
	 */
	public FileProvider(String file) {
		this(file, (Charset) null);
	}

	/**
	 * Creates a provider for the file, represented by a path, to be open/written using the default system encoding.
	 * @param file the path to a file. It can either be the path to a file in the file system or a resource in the classpath.
	 * @param encoding the name of the encoding that must be used to read from/write to the given file.
	 */
	public FileProvider(String file, String encoding) {
		this(file, getEncoding(encoding));
	}

	/**
	 * Creates a provider for the file, represented by a path, to be open/written using the default system encoding.
	 * @param filePath the path to a file. It can either be the path to a file in the file system or a resource in the classpath.
	 * @param encoding the encoding that must be used to read from/write to the given file.
	 */
	public FileProvider(String filePath, Charset encoding) {
		if (filePath == null || filePath.trim().isEmpty()) {
			throw new IllegalArgumentException("File path cannot be null or empty");
		}
		this.encoding = getEncoding(encoding);
		this.file = null;
		this.filePath = filePath;
	}

	private final static Charset getEncoding(String encoding) {
		if (encoding == null) {
			return Charset.defaultCharset();
		}
		return Charset.forName(encoding);
	}

	private final static Charset getEncoding(Charset encoding) {
		if (encoding == null) {
			return Charset.defaultCharset();
		}
		return encoding;
	}

	/**
	 * Returns the File instance given in the constructor of this class, or null if a path to a resource is being used.
	 * @return the File to be loaded by uniVocity, or null if a resource path should be used instead.
	 */
	public final File getFile() {
		return file;
	}

	/**
	 * Returns the encoding used to manipulate the provided file.
	 * @return encoding used to manipulate the provided file.
	 */
	public final Charset getEncoding() {
		return encoding;
	}

	/**
	 * Returns the resource path given in the constructor of this class, or null if a File is being used.
	 * @return the path to a resource to be loaded by uniVocity, or null if a File should be used instead.
	 */
	public final String getFilePath() {
		return filePath;
	}

	@Override
	public final String toString() {
		return filePath + " ("+ encoding +")";
	}
}
