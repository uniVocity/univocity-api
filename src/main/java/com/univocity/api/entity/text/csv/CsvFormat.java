/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.text.csv;

import com.univocity.api.entity.*;
import com.univocity.api.entity.text.*;

/**
 * The CSV format configuration class. Used by CSV data entities in {@link CsvEntityConfiguration}.
 * <p>It provides the following configuration options (in addition to the ones in {@link TextFormat}):
 *
 * <ul>
 *  <li><b>delimiter: </b> the field delimiter character. Used to separate individual fields in a CSV record (where the record is usually a line of text with multiple fields).
 *  	<br>e.g. the value  <b>a , b</b>  is parsed as <b>[ a ][ b ]</b>
 *  	<p><i>{@link #delimiter} defaults to ','</i>
 *  	<br></li>
 *  <li><b>quote:</b> character used for escaping values where the field delimiter is part of the value.
 *  	<p>e.g. the value <b>" a , b "</b> must be parsed as <b>[ a , b ]</b> (instead of <b>[ a ],[ b ]</b>)
 *  	<p><i>{@link #quote} defaults to ','</i></li>
 *  <li><b>quoteEscape:</b> character used for escaping the quote character inside an already quoted value
 *  	<p>e.g. the value <b>" "" a , b "" "</b> is parsed as <b>[ " a , b " ]</b>  (instead of <b>[ " a ][ b " ]</b> or <b>[ "" a , b "" ]</b>)
 *  	<p><i>{@link #quoteEscape} defaults to ','</i></li>
 * </ul>
 *
 * @see CsvEntityConfiguration
 * @see TextFormat
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class CsvFormat extends TextFormat {
	private Character quote;
	private Character quoteEscape;
	private Character delimiter;

	/**
	 * Returns the character used for escaping values where the field delimiter is part of the value.
	 * <p>e.g. the value <b>" a , b "</b> must be parsed as <b>[ a , b ]</b> (instead of <b>[ a ],[ b ]</b>)
	 * <p><i>Defaults to '"'</i>
	 * @return the quote character
	 */
	public final char getQuote() {
		if (quote == null) {
			return '"';
		}
		return quote;
	}

	/**
	 * Defines the character used for escaping values where the field delimiter is part of the value.
	 * <p>e.g. the value <b>" a , b "</b> must be parsed as <b>[ a , b ]</b> (instead of <b>[ a ],[ b ]</b>)
	 * @param quote the quote character
	 */
	public final void setQuote(char quote) {
		this.quote = quote;
	}

	/**
	 * Identifies whether or not a given character is used for escaping values where the field delimiter is part of the value
	 * @param ch the character to be verified
	 * @return true if the given character is the character used for escaping values that contain a field delimiter character, false otherwise
	 */
	public final boolean isQuote(char ch) {
		return getQuote() == ch;
	}

	/**
	 * Returns the character used for escaping quotes inside an already quoted value.
	 * <p>e.g. using <b>"</b> as the quote escape, the value <b>" "" a , b "" "</b> is parsed as <b>[ " a , b " ]</b>  (instead of <b>[ " a ][ b " ]</b> or <b>[ "" a , b "" ]</b>)
	 * <p><i>Defaults to '"'</i>
	 * @return the quote escape character
	 */
	public final char getQuoteEscape() {
		if (quoteEscape == null) {
			return '"';
		}
		return quoteEscape;
	}

	/**
	 * Defines the character used for escaping quotes inside an already quoted value.
	 * <p>e.g. using <b>"</b> as the quote escape, the value <b>" "" a , b "" "</b> is parsed as <b>[ " a , b " ]</b>  (instead of <b>[ " a ][ b " ]</b> or <b>[ "" a , b "" ]</b>)
	 * @param quoteEscape the quote escape character
	 */
	public final void setQuoteEscape(char quoteEscape) {
		this.quoteEscape = quoteEscape;
	}

	/**
	 * Identifies whether or not a given character is used for escaping quotes inside an already quoted value.
	 * @param ch the character to be verified
	 * @return true if the given character is the quote escape character, false otherwise
	 */
	public final boolean isQuoteEscape(char ch) {
		return getQuoteEscape() == ch;
	}

	/**
	 * Returns the field delimiter character. Used to separate individual fields in a CSV record.
	 * <p>e.g. using <b>,</b> as the delimiter, the value  <b>a , b</b>  is parsed as <b>[ a ][ b ]</b>
	 * <p><i>Defaults to ','</i>
	 * @return the field delimiter character
	 */
	public final char getDelimiter() {
		if (delimiter == null) {
			return ',';
		}
		return delimiter;
	}

	/**
	 * Defines the field delimiter character. Used to separate individual fields in a CSV record.
	 * <p>e.g. using <b>,</b> as the delimiter, the value  <b>a , b</b>  is parsed as <b>[ a ][ b ]</b>
	 * @param delimiter the field delimiter character
	 */
	public final void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Identifies whether or not a given character is the field delimiter used to separate individual fields in a CSV record.
	 * @param ch the character to be verified
	 * @return true if the given character is the field delimiter character, false otherwise
	 */
	public final boolean isDelimiter(char ch) {
		return getDelimiter() == ch;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void copyDefaultsFrom(Configuration defaultConfig) {
		super.copyDefaultsFrom(defaultConfig);

		CsvFormat defaults = (CsvFormat) defaultConfig;

		if (quote == null) {
			quote = defaults.getQuote();
		}

		if (quoteEscape == null) {
			quoteEscape = defaults.getQuoteEscape();
		}

		if (delimiter == null) {
			delimiter = defaults.getDelimiter();
		}
	}
}
