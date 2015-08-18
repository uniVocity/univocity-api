/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.text;

import com.univocity.api.entity.*;

/**
 * This class defines the essential text format parameters of all tabular text-based data entities supported by uniVocity.
 *
 * <p>In addition to the configuration options defined in {@link BasicTextFormat}, this class allows the definition of:
 * <ul>
 *  <li><b>comment:</b> a character that, if found in the beginning of a line of text, represents comment in any text-based input supported by uniVocity.
 *  	<p><i>{@link #comment} defaults to '#'.</i></li>
 * </ul>
 *
 * @see com.univocity.api.entity.text.BasicTextFormat
 * @see com.univocity.api.entity.text.csv.CsvFormat
 * @see com.univocity.api.entity.text.fixed.FixedWidthFormat
 * @see com.univocity.api.entity.text.TextEntityConfiguration
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public class TextFormat extends BasicTextFormat {

	private Character comment;

	protected TextFormat() {

	}

	/**
	 * Returns the character that represents a line comment. Defaults to '#'.
	 * <p> Set it to '\0' to disable comment skipping.
	 * @return the comment character
	 */
	public final char getComment() {
		if (comment == null) {
			return '#';
		}
		return comment;
	}

	/**
	 * Defines the character that represents a line comment when found in the beginning of a line of text. Defaults to '#'
	 * <p> Use '\0' to disable comment skipping.
	 * @param comment the comment character
	 */
	public void setComment(char comment) {
		this.comment = comment;
	}

	/**
	 * Identifies whether a given character represents a comment.
	 * @param ch the character to be verified
	 * @return {@code true} if the given character is the comment character, otherwise {@code false}
	 */
	public boolean isComment(char ch) {
		return this.getComment() == ch;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void copyDefaultsFrom(Configuration defaultConfig) {
		super.copyDefaultsFrom(defaultConfig);

		TextFormat defaults = (TextFormat) defaultConfig;
		if (comment == null) {
			comment = defaults.getComment();
		}
	}

}
