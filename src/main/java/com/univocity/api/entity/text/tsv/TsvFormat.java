/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.text.tsv;

import com.univocity.api.entity.*;
import com.univocity.api.entity.text.*;

/**
 * The TSV format configuration class. Used by TSV data entities in {@link TsvEntityConfiguration}.
 * <p>It provides the following configuration options (in addition to the ones in {@link TextFormat}):
 *
 * <ul>
 *  <li><b>escapeChar:</b> the character used for escaping special characters in TSV inputs: \t, \n, \r and \ . Defaults to '\\'
 *  	<p>e.g. the value <b>"\\n" "</b> is parsed as the newline character '\n')
 *  	<p><i>{@link #escapeChar} defaults to '\\'</i></li>
 * </ul>
 *
 * @see TsvEntityConfiguration
 * @see TextFormat
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class TsvFormat extends TextFormat {

	private Character escapeChar;

	/**
	 * Returns the character used for escaping special characters in TSV inputs: \t, \n, \r and \ . Defaults to '\\'
	 * <p>e.g. the value <b>"\\n" "</b> is parsed as the newline character '\n')</p>
	 * <p><i>Defaults to  to '\\'</i></p>
	 * @return the escape character for special sequences in TSV values
	 */
	public final char getEscapeChar() {
		if (escapeChar == null) {
			return '\\';
		}
		return escapeChar;
	}

	/**
	 * Defines the character used for escaping special characters in TSV inputs: \t, \n, \r and \ . Defaults to '\\'
	 * <p>e.g. the value <b>"\\n" "</b> is parsed as the newline character '\n')
	 * @param escapeChar the escape character
	 */
	public final void setEscapeChar(char escapeChar) {
		this.escapeChar = escapeChar;
	}

	/**
	 * Identifies whether or not a given character is used for escaping special characters in TSV inputs: \t, \n, \r and \
	 * @param ch the character to be verified
	 * @return {@code true} if the given character is the character used for escaping \t, \n, \r and \ , otherwise {@code false}
	 */
	public final boolean isEscapeChar(char ch) {
		return getEscapeChar() == ch;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void copyDefaultsFrom(Configuration defaultConfig) {
		super.copyDefaultsFrom(defaultConfig);

		TsvFormat defaults = (TsvFormat) defaultConfig;

		if (escapeChar == null) {
			escapeChar = defaults.getEscapeChar();
		}
	}
}
