/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.text;

import java.util.*;

import com.univocity.api.common.*;
import com.univocity.api.entity.*;

/**
 * This is the parent class for all configuration classes that define a file format in uniVocity.
 * 
 * <p>By default, all plain-text based entities in uniVocity require the following format definitions:
 * 
 * <ul>
 *  <li><b>lineSeparator:</b> the 1-2 character sequence that indicates the end of a line. Newline sequences are different across operating systems. Typically:
 *		<ul>
 *			<li>Windows uses carriage return and line feed: <i>\r\n</i></li>
 *			<li>Linux/Unix uses line feed only: <i>\n</i></li>
 *			<li>MacOS uses carriage return only: <i>\r</i></li>
 *		</ul>  	
 *   	<i>{@link #lineSeparator} defaults to the system line separator</i>
 *  </li>
 *  <p>
 *  <li><b>normalizedNewLine:</b> a single character used to represent the end of a line uniformly in any parsed content. It has the following implications:
 *  	<ul>
 *			<li>When <i>reading</i> a text-based input, the sequence of characters defined in {@link #lineSeparator} will be replaced by this character.</li>
 *			<li>When <i>writing</i> to a text-based output, this character will be replaced by the sequence of characters defined in {@link #lineSeparator}.</li>
 *		</ul>
 *  	<p><i>{@link #normalizedNewline} defaults to '\n'.</i>
 *  </li>
 *  <p>
 *  <li><b>comment:</b>a character that, if found in the beginning of a line of text, represents comment in any text-based input supported by uniVocity.</li>
 *  	<p><i>{@link #comment} defaults to '#'.</i>
 * </ul> 
 * 
 * @see com.univocity.api.entity.text.csv.CsvFormat
 * @see com.univocity.api.entity.text.fixed.FixedWidthFormat
 * @see com.univocity.api.entity.text.TextEntityConfiguration
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 * 
 */
public class TextFormat extends Configuration {

	private static final char[] systemLineSeparator;

	static {
		String systemLineSeparatorString = System.getProperty("line.separator");
		if (systemLineSeparatorString == null) {
			systemLineSeparatorString = "\n";
		}
		systemLineSeparator = systemLineSeparatorString.toCharArray();
	}

	private char[] lineSeparator;
	private Character normalizedNewline;
	private Character comment;

	protected TextFormat() {

	}

	/**
	 * Returns the current line separator character sequence, which can contain 1 to 2 characters. Defaults to the system's line separator sequence (usually '\r\n' in Windows, '\r' in MacOS, and '\n' in Linux/Unix).
	 * @return the sequence of 1 to 2 characters that identifies the end of a line
	 */
	public final char[] getLineSeparator() {
		if (lineSeparator == null) {
			return systemLineSeparator.clone();
		}
		return lineSeparator.clone();
	}

	/**
	 * Returns the current line separator sequence as a String of 1 to 2 characters. Defaults to the system's line separator sequence (usually "\r\n" in Windows, "\r" in MacOS, and "\n" in Linux/Unix).
	 * @return the sequence of 1 to 2 characters that identifies the end of a line
	 */
	public final String getLineSeparatorString() {
		return new String(getLineSeparator());
	}

	/**
	 * Identifies whether a given character sequence matches the {@link #lineSeparator} sequence.
	 * @param string the character sequence to be matched
	 * @return true if the given character sequence matches the {@link #lineSeparator}, false otherwise 
	 */
	public final boolean isLineSeparator(String string) {
		return getLineSeparatorString().equals(string);
	}

	/**
	 * Identifies whether a given character sequence matches the {@link #lineSeparator} sequence.
	 * @param chars the character sequence to be matched
	 * @return true if the given character sequence matches the {@link #lineSeparator}, false otherwise 
	 */
	public final boolean isLineSeparator(char[] chars) {
		if (chars == null) {
			return false;
		}
		return Arrays.equals(getLineSeparator(), chars);
	}

	/**
	 * Defines the line separator sequence that should be used for parsing and writing.
	 * @param lineSeparator a sequence of 1 to 2 characters that identifies the end of a line
	 */
	public final void setLineSeparator(String lineSeparator) {
		Args.notEmpty(this.lineSeparator, "Line separator");
		setLineSeparator(lineSeparator.toCharArray());
	}

	/**
	 * Defines the line separator sequence that should be used for parsing and writing.
	 * @param lineSeparator a sequence of 1 to 2 characters that identifies the end of a line
	 */
	public final void setLineSeparator(char[] lineSeparator) {
		Args.notEmpty(this.lineSeparator, "Line separator");
		if (lineSeparator.length > 2) {
			throw new IllegalArgumentException("Invalid line separator. Up to 2 characters are expected. Got " + lineSeparator.length + " characters.");
		}
		this.lineSeparator = lineSeparator;
	}

	/**
	 * Returns the normalized newline character, which is automatically replaced by {@link #lineSeparator} when reading/writing. Defaults to '\n'.
	 * @return the normalized newline character
	 */
	public final char getNormalizedNewline() {
		if (normalizedNewline == null) {
			return '\n';
		}
		return normalizedNewline;
	}

	/**
	 * Sets the normalized newline character, which is automatically replaced by {@link #lineSeparator} when reading/writing
	 * @param normalizedNewline a single character used to represent a line separator.
	 */
	public final void setNormalizedNewline(char normalizedNewline) {
		this.normalizedNewline = normalizedNewline;
	}

	/**
	 * Compares the given character against the {@link #normalizedNewline} character.
	 * @param  ch the character to be verified
	 * @return true if the given character is the normalized newline character, false otherwise 
	 */
	public final boolean isNormalizedNewLine(char ch) {
		return this.getNormalizedNewline() == ch;
	}

	/**
	 * Returns the character that represents a line comment. Defaults to '#'
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
	 * Identifies whether a given character represents a comment
	 * @param ch the character to be verified
	 * @return true if the given character is the comment character, false otherwise 
	 */
	public boolean isComment(char ch) {
		return this.getComment() == ch;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void copyDefaultsFrom(Configuration defaultConfig) {
		TextFormat defaults = (TextFormat) defaultConfig;
		if (comment == null) {
			comment = defaults.getComment();
		}

		if (lineSeparator == null) {
			lineSeparator = defaults.getLineSeparator();
		}

		if (normalizedNewline == null) {
			normalizedNewline = defaults.getNormalizedNewline();
		}
	}

}
