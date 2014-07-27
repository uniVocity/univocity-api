/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.text.fixed;

import com.univocity.api.common.*;
import com.univocity.api.entity.*;
import com.univocity.api.entity.text.*;

/**
 * This is the class used to configure fixed-width data entities.
 *
 * <p>Note the length of all fields is required in order to process fixed-width records.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class FixedWidthEntityConfiguration extends TextEntityConfiguration<FixedWidthFormat> {

	//used to build the field length array
	private int lengthIndex = 0;

	//merged values
	private Boolean recordEndsOnNewline;
	private Boolean skipTrailingCharsUntilNewline;

	public FixedWidthEntityConfiguration() {
		this.fieldLengths = null;
	}

	/**
	 * Associates a length to each column of records returned by the fixed-width data entity. All lengths must be greater than 0.
	 * @param fieldLengths the sequence of lengths for each field that form a fixed-width record.
	 */
	@Override
	public final void setFieldLengths(int... fieldLengths) {
		Args.notEmpty(fieldLengths, "Field length sequence");

		super.validateHeaders(headers, identifiers, fieldLengths);

		setFieldsPerRecord(fieldLengths.length);

		for (int i = 0; i < fieldLengths.length; i++) {
			addField(fieldLengths[i]);
		}
	}

	/**
	 * Defines the number of fields per record. Lengths must be defined through either {@link #addField(int)} or {@link #setFieldLengths(int...)}
	 * @param fieldsPerRecord The number of fields in a fixed-width record. It must be greater than 0.
	 */
	public final void setFieldsPerRecord(int fieldsPerRecord) {
		Args.positive(fieldsPerRecord, "Number of fields per record");
		this.fieldLengths = new int[fieldsPerRecord];
		lengthIndex = 0;
	}

	/**
	 * Adds the length of the next field in a fixed-width record.
	 * <p> Note the total number of fields per record must have been defined using {@link #setFieldsPerRecord(int)} prior to invoking this method.
	 * <p> This method can be called the number of times
	 * @param length the length of the next field. It must be greater than 0.
	 */
	public final void addField(int length) {
		if (fieldLengths == null) {
			throw new IllegalStateException("Fields per record not set");
		}

		if (length < 1) {
			throw new IllegalArgumentException("Invalid field length: " + length + " for field at index " + lengthIndex);
		}
		if (fieldLengths.length == lengthIndex) {
			throw new IllegalArgumentException("Cannot add any more fields. All " + fieldLengths.length + " fields are already set");
		}

		fieldLengths[lengthIndex++] = length;
	}

	/**
	 * Returns the number of fields in each record of a fixed-width data entity
	 * @return the number of fields in each record of a fixed-width data entity
	 */
	public final int getFieldsPerRecord() {
		if (fieldLengths == null) {
			throw new IllegalStateException("Fields per record not set");
		}
		return fieldLengths.length;
	}

	/**
	 * Returns the sequence of lengths for each field that form a fixed-width record.
	 * <p>An {@link IllegalStateException} will be thrown if the field lengths were not fully configured.
	 * @return the sequence of lengths for each field that form a fixed-width record.
	 */
	@Override
	public final int[] getFieldLengths() {
		validate();
		return this.fieldLengths.clone();
	}

	private final void validate() {
		if (fieldLengths == null) {
			throw new IllegalStateException("Fields per record not set");
		}
		if (lengthIndex < fieldLengths.length) {
			throw new IllegalStateException("Fixed width format not properly defined: there should be " + getFieldsPerRecord() + " fields per record but only the first " + lengthIndex + " fields lengths were set");
		}
	}

	/**
	 * Indicates whether or not a record is considered parsed when a newline is reached. Examples:
	 * <ul>
	 *  <li>Consider two records of length <b>4</b>, and the input <b>12\n3456</b></li>
	 * 	<li>When {@link #recordEndsOnNewline} is set to true:  the first value will be read as <b>12</b> and the second <b>3456</b></li>
	 *  <li>When {@link #recordEndsOnNewline} is set to false:  the first value will be read as <b>12\n3</b> and the second <b>456</b></li>
	 * </ul>
	 * <p><i>Defaults to false</i>
	 * @return true if a record should be considered parsed when a newline is reached; false otherwise
	 */
	public final boolean getRecordEndsOnNewline() {
		if (this.recordEndsOnNewline == null) {
			return false;
		}
		return recordEndsOnNewline;
	}

	/**
	 * Defines whether or not a record is considered parsed when a newline is reached. Examples:
	 * <ul>
	 *  <li>Consider two records of length <b>4</b>, and the input <b>12\n3456</b></li>
	 * 	<li>When {@link #recordEndsOnNewline} is set to true:  the first value will be read as <b>12</b> and the second <b>3456</b></li>
	 *  <li>When {@link #recordEndsOnNewline} is set to false:  the first value will be read as <b>12\n3</b> and the second <b>456</b></li>
	 * </ul>
	 * @param recordEndsOnNewline a flag indicating whether or not a record is considered parsed when a newline is reached
	 */
	public final void setRecordEndsOnNewline(boolean recordEndsOnNewline) {
		this.recordEndsOnNewline = recordEndsOnNewline;
	}

	/**
	 * Indicates whether or not any trailing characters beyond the record's length should be skipped until the newline is reached.
	 * <br>For example, if the record length is 5, but the row contains <b>12345678\n</b>, then portion containing <b>678\n</b> will be discarded and not considered part of the next record
	 * <p><i>Defaults to false</i>
	 * @return returns true if any trailing characters beyond the record's length should be skipped until the newline is reached, false otherwise
	 */
	public final boolean getSkipTrailingCharsUntilNewline() {
		if (this.skipTrailingCharsUntilNewline == null) {
			return false;
		}
		return skipTrailingCharsUntilNewline;
	}

	/**
	 * Defines whether or not any trailing characters beyond the record's length should be skipped until the newline is reached.
	 * <br>For example, if the record length is 5, but the row contains <b>12345678\n</b>, then portion containing <b>678\n</b> will be discarded and not considered part of the next record
	 * @param skipTrailingCharsUntilNewline a flag indicating if any trailing characters beyond the record's length should be skipped until the newline is reached
	 */
	public final void setSkipTrailingCharsUntilNewline(boolean skipTrailingCharsUntilNewline) {
		this.skipTrailingCharsUntilNewline = skipTrailingCharsUntilNewline;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void copyDefaultsFrom(Configuration defaultConfig) {
		super.copyDefaultsFrom(defaultConfig);

		FixedWidthEntityConfiguration defaults = (FixedWidthEntityConfiguration) defaultConfig;

		if (recordEndsOnNewline == null) {
			recordEndsOnNewline = defaults.getRecordEndsOnNewline();
		}
		if (skipTrailingCharsUntilNewline == null) {
			skipTrailingCharsUntilNewline = defaults.getSkipTrailingCharsUntilNewline();
		}
	}

	/**
	 * Creates a new default {@link FixedWidthFormat} instance
	 */
	@Override
	protected final FixedWidthFormat newDefaultFormat() {
		return new FixedWidthFormat();
	}

}
