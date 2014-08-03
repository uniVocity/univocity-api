/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.common;

import java.io.*;

/**
 * Base abstract class to define classes that provide instances of {@link java.io.Writer}.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public abstract class WriterProvider implements ResourceProvider<Writer> {

	/**
	 * Removes any data contained in the resource being written using the instances of {@link java.io.Writer} provided by this class.
	 */
	public abstract void clearDestination();

	/**
	 * Queries whether or not the resource to be written contains records already. This is used only for determining whether or not uniVocity should write
	 * a row with headers for each column in case the underlying resource is empty, and the entity is configured to write its headers to the output.
	 *
	 * Considering the entity is configured to write its headers to the output, the <code>isEmpty()</code> method will be queried and:
	 * <ul>
	 * 	<li>if {@code false}: The underlying resource has data already and no headers will be written</li>
	 *  <li>if {@code true}: The underlying resource is empty and headers will be written to it</li>
	 * </ul>
	 *
	 * If {@link #clearDestination()} was invoked in the same transaction, uniVocity will know the entity is empty and will then attempt to write the header row.
	 * However, as it cannot determine whether the output has data already written in it; Mappings where {@link #clearDestination()} is not used
	 * might produce outputs without the header row. This is likely to occur in write-only entities with no initial data.
	 *
	 * @return a flag indicating whether or not the underlying resource contains records.
	 */
	public abstract boolean isEmpty();
}
