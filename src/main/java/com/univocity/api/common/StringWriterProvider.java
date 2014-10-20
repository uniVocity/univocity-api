/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.common;

import java.io.*;

/**
 * A {@code WriterProvider} for Strings. Use this to write data directly to a String.
 * This is just a convenience class that you can use to write test cases
 * without having to deal with files or other persistent resources.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class StringWriterProvider extends WriterProvider {

	private StringWriter writer;
	private String string = "";

	public StringWriterProvider() {

	}

	/**
	 * Obtains a new StringWriter instance.
	 * @return a new StringWriter
	 */
	@Override
	public final Writer getResource() {
		string = getString();
		writer = new StringWriter();
		writer.append(string);
		return writer;
	}

	/**
	 * Clears the contents written to the string so far
	 */
	@Override
	public final void clearDestination() {
		string = "";
		writer = new StringWriter();
	}

	/**
	 * Returns the contents written to the string so far.
	 * @return the contents written to the string so far.
	 */
	public final String getString() {
		if (writer != null) {
			string = writer.toString();
			writer = null;
		}
		return string;
	}

	@Override
	public final boolean isEmpty() {
		return string.isEmpty() && writer.getBuffer().length() == 0;
	}
}
