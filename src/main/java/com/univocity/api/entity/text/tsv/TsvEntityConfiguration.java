/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.text.tsv;

import com.univocity.api.entity.text.*;

/**
 * This is the class used to configure TSV data entities.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public final class TsvEntityConfiguration extends TextEntityConfiguration<TsvFormat> {

	public TsvEntityConfiguration() {
	}

	/**
	 * Creates a new default {@link TsvFormat} instance.
	 */
	@Override
	protected final TsvFormat newDefaultFormat() {
		return new TsvFormat();
	}
}
