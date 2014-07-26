/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.text.csv;

import com.univocity.api.entity.*;
import com.univocity.api.entity.text.*;

/**
 * This is the class used to configure CSV data entities. 
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class CsvEntityConfiguration extends TextEntityConfiguration<CsvFormat> {

	private boolean emptyValueSet = false;

	private String emptyValue;
	private Boolean alwaysQuoteOnWrite;

	public CsvEntityConfiguration() {
	}

	/** 
	 * Defines a default value to be used in substitution of null when there are empty values within quotes in a CSV record.
	 * <ul>
	 *	<li><i>when reading:</i> if a value parsed from the input is empty, and the value was enclosed within quotes, then emptyValue is used instead of null.</li>
	 *  <li><i>when writing:</i> if the String representation of a value is empty, emptyValue is written instead of an empty String.</li>
	 * </ul>
	 * <p><i>defaults to null.</i>
	 * @return the default value used instead of null when reading and writing empty (but not null) fields.
	 */
	public final String getEmptyValue() {
		if (!emptyValueSet) {
			return null;
		}
		return emptyValue;
	}

	/** 
	 * Defines a default value to be used in substitution of null when there are empty values within quotes in a CSV record.
	 * <ul>
	 *	<li><i>when reading:</i> if a value parsed from the input is empty, and the value was enclosed within quotes, then emptyValue is used instead of null.</li>
	 *  <li><i>when writing:</i> if the String representation of a value is empty, emptyValue is written instead of an empty String.</li>
	 * </ul>
	 * @param emptyValue the default value used instead of null when reading and writing empty (but not null) fields.
	 */
	public final void setEmptyValue(String emptyValue) {
		this.emptyValue = emptyValue;
	}

	/**
	 * Defines whether or not all values should be enclosed within quotes (as specified in {@link CsvFormat#getQuote()}) when writing.
	 * <p>When writing, by default only values that contain a field separator are enclosed within quotes. Set this property to true to enclose all values within quotes.
	 * 
	 * @param quoteAllValues a flag indicating whether or not to enclose all values within quotes when writing.
	 */
	public final void setAlwaysQuoteOnWrite(boolean quoteAllValues) {
		this.alwaysQuoteOnWrite = quoteAllValues;
	}

	/**
	 * Indicates whether or not all values should be enclosed within quotes (as specified in {@link CsvFormat#getQuote()}) when writing.
	 * @return a flag indicating whether or not all values will should be enclosed within quotes when writing.
	 */
	public final boolean getAlwaysQuoteOnWrite() {
		if (alwaysQuoteOnWrite == null) {
			return false;
		}
		return alwaysQuoteOnWrite;
	}

	/**
	 * Creates a new default {@link CsvFormat} instance
	 */
	@Override
	protected final CsvFormat newDefaultFormat() {
		return new CsvFormat();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void copyDefaultsFrom(Configuration defaultConfig) {
		super.copyDefaultsFrom(defaultConfig);

		CsvEntityConfiguration defaults = (CsvEntityConfiguration) defaultConfig;

		if (alwaysQuoteOnWrite == null) {
			alwaysQuoteOnWrite = defaults.getAlwaysQuoteOnWrite();
		}
		if (!emptyValueSet) {
			this.emptyValue = defaults.getEmptyValue();
		}
	}
}
