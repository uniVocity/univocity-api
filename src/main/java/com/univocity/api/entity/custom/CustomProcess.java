/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.custom;

import java.io.*;

/**
 * Base interface for processes executed by instances of {@link CustomReadableEntity} and {@link CustomDataEntity}.
 * <p><b>Note: </b>Keep in mind uniVocity will execute your custom processes in a multithreaded context. 
 *       If the process requires a shared resource, then proper synchronization will be required.
 * 
 * @see ReadingProcess
 * @see ExclusionProcess
 * @see WritingProcess
 * @see UpdateProcess
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public interface CustomProcess extends Closeable {

	/**
	 * Closes and releases any resources allocated by the process.
	 */
	@Override
	public void close();

}
