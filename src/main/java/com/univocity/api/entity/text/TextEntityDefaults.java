/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.text;

import com.univocity.api.common.*;
import com.univocity.api.entity.*;

/**
 * This is provides essential configuration defaults for reading from and writing to text in conformity to a particular format (such as CSV, for example).
 *
 * <p>By default, all uniVocity text-based data entities provide the configuration options available in this class.
 *
 * @see com.univocity.api.entity.text.TextFormat
 * @see com.univocity.api.entity.text.TextEntityConfiguration
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 * @param <F> the configuration class that manages a specific text format.
 */
abstract class TextEntityDefaults<F extends TextFormat> extends Configuration {

	private F format;
	private String nullValue;
	private boolean nullValueSet = false;

	private Integer maxCharsPerColumn;
	private Integer maxColumns;
	private Integer inputBufferSize;

	private Boolean readInputOnSeparateThread;
	private Boolean ignoreTrailingWhitespaces;
	private Boolean ignoreLeadingWhitespaces;
	private Boolean headerExtractionEnabled;
	private Boolean skipEmptyLines;
	private Boolean headerWritingEnabled;

	protected TextEntityDefaults() {

	}

	/**
	 * Determines whether to skip empty lines of text
	 * <ul>
	 * 	<li><i>when reading:</i> if the entity reads an empty line from the input, it will be discarded.</li>
	 * 	<li><i>when writing:</i> if the entity receives an empty or null row to write to the output, it will be ignored.</li>
	 * </ul>
	 * <i>defaults to true.</i>
	 * @return a flag indicating whether or not empty lines should be skipped.
	 */
	public final boolean getSkipEmptyLines() {
		if (skipEmptyLines == null) {
			return true;
		}
		return skipEmptyLines;
	}

	/**
	 * Determines whether to skip empty lines of text
	 * <ul>
	 * 	<li><i>when reading:</i> if the entity reads an empty line from the input, it will be discarded.</li>
	 * 	<li><i>when writing:</i> if the entity receives an empty or null row to write to the output, it will be ignored.</li>
	 * </ul>
	 * @param skipEmptyLines a flag indicating whether or not empty lines should be skipped.
	 */
	public final void setSkipEmptyLines(boolean skipEmptyLines) {
		this.skipEmptyLines = skipEmptyLines;
	}

	/**
	 * Determines whether to remove trailing white spaces from values being read/written
	 * <p><i>defaults to true.</i>
	 * @return true if trailing white spaces should be removed from values of this entity; false otherwise
	 */
	public final boolean getIgnoreTrailingWhitespaces() {
		if (ignoreTrailingWhitespaces == null) {
			return true;
		}
		return ignoreTrailingWhitespaces;
	}

	/**
	 * Determines whether to remove trailing white spaces from values being read/written
	 * @param ignoreTrailingWhitespaces flag indicating whether trailing white spaces should be removed from values of this entity.
	 */
	public final void setIgnoreTrailingWhitespaces(boolean ignoreTrailingWhitespaces) {
		this.ignoreTrailingWhitespaces = ignoreTrailingWhitespaces;
	}

	/**
	 * Indicates whether or not the first valid record parsed from the input should be used to derive the names of each column of this entity.
	 * <p><i>defaults to false.</i>
	 * @return true if the first valid record parsed from the input should be used to derive the names of each column, false otherwise
	 */
	public final boolean isHeaderExtractionEnabled() {
		if (headerExtractionEnabled == null) {
			return true;
		}
		return headerExtractionEnabled;
	}

	/**
	 * Defines whether or not the first valid record parsed from the input should be used to derive the names of each column of this entity.
	 * @param extractHeaders a flag indicating whether the first valid record parsed from the input be used to derive the names of each column of this entity.
	 */
	public final void setHeaderExtractionEnabled(boolean extractHeaders) {
		this.headerExtractionEnabled = extractHeaders;
	}

	/**
	 * Determines whether to remove leading white spaces from values being read/written
	 * <p><i>defaults to true.</i>
	 * @return true if leading white spaces should be removed from values of this entity; false otherwise.
	 */
	public final boolean getIgnoreLeadingWhitespaces() {
		if (ignoreLeadingWhitespaces == null) {
			return true;
		}
		return ignoreLeadingWhitespaces;
	}

	/**
	 * Determines whether to remove leading white spaces from values being read/written
	 * <p><i>defaults to true.</i>
	 * @param ignoreLeadingWhitespaces true if leading white spaces should be removed from values of this entity.
	 */
	public final void setIgnoreLeadingWhitespaces(boolean ignoreLeadingWhitespaces) {
		this.ignoreLeadingWhitespaces = ignoreLeadingWhitespaces;
	}

	/**
	 * Defines the number of characters held by the entity buffer when reading from the input
	 * <p><i>Defaults to 1024*1024 characters (i.e. 1,048,576 characters).</i>
	 * @return the number of characters held by the entity buffer when reading from the input
	 */
	public final int getInputBufferSize() {
		if (inputBufferSize == null) {
			return 1024 * 1024;
		}
		return inputBufferSize;
	}

	/**
	 * Defines the number of characters held by the entity buffer when reading from the input
	 * @param inputBufferSize the new input buffer size (in number of characters)
	 */
	public final void setInputBufferSize(int inputBufferSize) {
		Args.positive(inputBufferSize, "Input buffer size");
		this.inputBufferSize = inputBufferSize;
	}

	/**
	 * Defines whether or not a separate thread will be used to read characters from the input while parsing.
	 * <ul>
	 * 	<li><i>When enabled</i>, a reading thread will be started and load characters from the input, while the parser is processing its input buffer.
	 * 		<br>This yields better performance, especially when reading from big input (&gt;100 mb)</li>
	 *  <li><i>When disabled</i>, the parsing process will briefly pause so the buffer can be replenished every time it is exhausted.
	 *  	<br>This setting can be slightly more efficient when the input is small.</li>
	 * </ul>
	 *  <i>Defaults to true if the number of available processors at runtime is greater than 1</i>
	 * @return true if the input should be read on a separate thread, false otherwise
	 */
	public final boolean getReadInputOnSeparateThread() {
		if (readInputOnSeparateThread == null) {
			return Runtime.getRuntime().availableProcessors() > 1;
		}
		return readInputOnSeparateThread;
	}

	/**
	 * Defines whether or not a separate thread will be used to read characters from the input while parsing.
	 * <ul>
	 * 	<li><i>When enabled</i>, a reading thread will be started and load characters from the input, while the parser is processing its input buffer.
	 * 		<br>This yields better performance, especially when reading from big input (&gt;100 mb)</li>
	 *  <li><i>When disabled</i>, the parsing process will briefly pause so the buffer can be replenished every time it is exhausted.
	 *  	<br>This setting can be slightly more efficient when the input is small.</li>
	 * </ul>
	 * @param readInputOnSeparateThread the flag indicating whether or not the input should be read on a separate thread
	 */
	public final void setReadInputOnSeparateThread(boolean readInputOnSeparateThread) {
		this.readInputOnSeparateThread = readInputOnSeparateThread;
	}

	/**
	 * Defines a default value to be used in substitution of null when there are empty fields in a text record.
	 * <ul>
	 *	<li><i>when reading:</i> if a value parsed from the input is empty, the nullValue is used instead of null.</li>
	 *  <li><i>when writing:</i> if a value is null then nullValue is written instead of an empty string.</li>
	 * </ul>
	 * @param nullValue a default value used instead of null for reading and writing.
	 */
	public final void setNullValue(String nullValue) {
		nullValueSet = true;
		this.nullValue = nullValue;
	}

	/**
	 * Returns the default value used in substitution of null when there are empty fields in a text record.
	 * <ul>
	 *	<li><i>when reading:</i> if a value parsed from the input is empty, the nullValue is used instead of null.</li>
	 *  <li><i>when writing:</i> if a value is null then nullValue is written instead of an empty string.</li>
	 * </ul>
	 * <p><i>defaults to null.</i>
	 *
	 * @return a default value used instead of null for reading and writing.
	 */
	public final String getNullValue() {
		if (!nullValueSet) {
			return null;
		}
		return nullValue;
	}

	/**
	 * Returns the maximum number of characters allowed for any given value being written/read.
	 * <br>This is required to avoid getting an {@link OutOfMemoryError} in case a file does not have a valid format.
	 * <br>In such cases the entity might just keep reading from the input until its end, or until the memory is exhausted.
	 *     This provides a limit which avoids unwanted JVM crashes.
	 * <p><i>defaults to 4096.</i>
	 *
	 * @return the maximum number of characters any given field in a record can have
	 */
	public final int getMaxCharsPerColumn() {
		if (maxCharsPerColumn == null) {
			return 4096;
		}
		return maxCharsPerColumn;
	}

	/**
	 * Defines the maximum number of characters allowed for any given value being written/read.
	 * <br>This is required to avoid getting an {@link OutOfMemoryError} in case a file does not have a valid format.
	 * <br>In such cases the entity might just keep reading from the input until its end, or until the memory is exhausted.
	 *     This provides a limit which avoids unwanted JVM crashes.
	 * @param maxCharsPerColumn the maximum number of characters any given field in a record can have
	 */
	public final void setMaxCharsPerColumn(int maxCharsPerColumn) {
		Args.positive(maxCharsPerColumn, "Maximum number of characters per column");
		this.maxCharsPerColumn = maxCharsPerColumn;
	}

	/**
	 * Returns the hard limit on how many columns a record can have.
	 * <br>This is required to avoid getting an {@link OutOfMemoryError} in case a file does not have a valid format.
	 * <br>In such cases the entity might just keep reading from the input until its end, or until the memory is exhausted.
	 *     This provides a limit which avoids unwanted JVM crashes.
	 * <p><i>defaults to 512.</i>
	 * @return the maximum number of columns a record can have.
	 */
	public final int getMaxColumns() {
		if (maxColumns == null) {
			return 512;
		}
		return maxColumns;
	}

	/**
	 * Defines a hard limit on how many columns a record can have.
	 * <br>This is required to avoid getting an {@link OutOfMemoryError} in case a file does not have a valid format.
	 * <br>In such cases the entity might just keep reading from the input until its end, or until the memory is exhausted.
	 *     This provides a limit which avoids unwanted JVM crashes.
	 * @param maxColumns the maximum number of columns a record can have.
	 */
	public final void setMaxColumns(int maxColumns) {
		Args.positive(maxCharsPerColumn, "Maximum number of columns per record");
		this.maxColumns = maxColumns;
	}

	/**
	 * Indicates whether or not to write headers to the output when writing records to an empty entity.
	 * <p><b>Note:</b> write-only entities (i.e. obtained from {@link WriterProvider}) do not provide information about whether the output is empty or not.
	 * uniVocity will only attempt to write headers to such entities after a call to {@link WriterProvider#clearDestination()} is made
	 * <p><i>defaults to false.</i>
	 * @return true if the headers should be written before adding records to an empty entity, false otherwise
	 */
	public final boolean isHeaderWritingEnabled() {
		if (headerWritingEnabled == null) {
			return false;
		}
		return headerWritingEnabled;
	}

	/**
	 * Indicates whether or not to write headers to the output when writing records to an empty entity.
	 * <p><b>Note:</b> write-only entities (i.e. obtained from {@link WriterProvider}) do not provide information about whether the output is empty or not.
	 * uniVocity will only attempt to write headers to such entities after a call to {@link WriterProvider#clearDestination()} is made
	 * @param headerWritingEnabled true if the headers should be written before adding records to an empty entity, false otherwise
	 */
	public final void setHeaderWritingEnabled(boolean headerWritingEnabled) {
		this.headerWritingEnabled = headerWritingEnabled;
	}

	/**
	 * Returns the input/output format settings for a given text. Each text format requires specific configuration,
	 * but they all share common settings from {@link TextFormat}
	 * @return the text format settings.
	 */
	public final F getFormat() {
		if (format == null) {
			format = newDefaultFormat();
		}
		return format;
	}

	/**
	 * Defines the input/output format settings for a given text. Each text format requires specific configuration,
	 * but they all share common settings from {@link TextFormat}
	 * @param format the text format settings.
	 */
	public final void setFormat(F format) {
		Args.notNull(format, "Text format configuration");
		this.format = format;
	}

	/**
	 * Creates a new instance of a text format configuration.
	 * @return a new instance of a text format configuration.
	 */
	protected abstract F newDefaultFormat();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void copyDefaultsFrom(Configuration defaultConfig) {
		TextEntityDefaults<?> defaults = (TextEntityDefaults<?>) defaultConfig;

		if (skipEmptyLines == null) {
			skipEmptyLines = defaults.getSkipEmptyLines();
		}
		if (ignoreTrailingWhitespaces == null) {
			ignoreTrailingWhitespaces = defaults.getIgnoreTrailingWhitespaces();
		}
		if (ignoreLeadingWhitespaces == null) {
			ignoreLeadingWhitespaces = defaults.getIgnoreLeadingWhitespaces();
		}
		if (inputBufferSize == null) {
			inputBufferSize = defaults.getInputBufferSize();
		}
		if (readInputOnSeparateThread == null) {
			readInputOnSeparateThread = defaults.getReadInputOnSeparateThread();
		}
		if (format == null) {
			format = newDefaultFormat();
			format.copyDefaultsFrom(defaults.getFormat());
		}
		if (headerExtractionEnabled == null) {
			headerExtractionEnabled = defaults.isHeaderExtractionEnabled();
		}
		if (!nullValueSet) {
			setNullValue(defaults.getNullValue());
		}
		if (maxCharsPerColumn == null) {
			maxCharsPerColumn = defaults.getMaxCharsPerColumn();
		}
		if (maxColumns == null) {
			maxColumns = defaults.getMaxColumns();
		}
		if (headerWritingEnabled == null) {
			headerWritingEnabled = defaults.isHeaderWritingEnabled();
		}
	}
}
