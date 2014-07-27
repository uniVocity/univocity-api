/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.text.fixed;

import com.univocity.api.entity.*;
import com.univocity.api.entity.text.*;

/**
 * The fixed-width format configuration class. Used by fixed-width data entities in {@link FixedWidthEntityConfiguration}.
 * <p>It provides the following configuration options (in addition to the ones in {@link TextFormat}):
 *
 * <ul>
 *  <li><b>padding:</b> the character used for filling unwritten spaces in a fixed-width record.
 *  	<p>e.g. if a field has a length of 5 characters, but the value is 'ZZ', the field should contain <b>[ZZ   ]</b> (i.e. ZZ followed by 3 unwritten spaces).
 *  	<br>If the padding is set to '_', then the field will be written as <b>[ZZ___]</b></li>
 *  	<p><i>{@link #padding} defaults to <b>' '</b></i>
 *  	<br>
 * </ul>
 *
 * @see FixedWidthEntityConfiguration
 * @see TextFormat
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class FixedWidthFormat extends TextFormat {

	private Character padding;

	/**
	 * Returns the character used for filling unwritten spaces in a fixed-width record.
	 * 	<p>e.g. if a field has a length of 5 characters, but the value is 'ZZ', the field should contain <b>[ZZ   ]</b> (i.e. ZZ followed by 3 unwritten spaces).
	 * 	<br>If the padding is set to '_', then the field will be written as <b>[ZZ___]</b></li>
	 * 	<p><i>Defaults to ' '</i>
	 * @return the padding character
	 */
	public final char getPadding() {
		if (padding == null) {
			return ' ';
		}
		return padding;
	}

	/**
	 * Defines the character used for filling unwritten spaces in a fixed-width record.
	 * 	<p>e.g. if a field has a length of 5 characters, but the value is 'ZZ', the field should contain <b>[ZZ   ]</b> (i.e. ZZ followed by 3 unwritten spaces).
	 * 	<br>If the padding is set to '_', then the field will be written as <b>[ZZ___]</b></li>
	 * @param padding the padding character
	 */
	public final void setPadding(char padding) {
		this.padding = padding;
	}

	/**
	 * Identifies whether or not a given character is used for representing unwritten spaces in a fixed-width record.
	 *
	 * @param ch the character to be verified
	 * @return true if the given character is the padding character, false otherwise
	 */
	public final boolean isPadding(char ch) {
		return getPadding() == ch;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void copyDefaultsFrom(Configuration defaultConfig) {
		super.copyDefaultsFrom(defaultConfig);

		FixedWidthFormat defaults = (FixedWidthFormat) defaultConfig;

		if (padding == null) {
			padding = defaults.getPadding();
		}
	}
}
