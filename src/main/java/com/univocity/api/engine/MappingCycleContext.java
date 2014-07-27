/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.engine;

import com.univocity.api.exception.*;

/**
 * The <code>MappingCycleContext</code> is available to {@link RowReader} instances used during the execution of a data mapping between two entities.
 *
 * It provides information specific to the mapping cycle being executed.
 *
 *
 * @see RowReader
 * @see RowMappingContext
 * @see EntityMappingContext
 * @see EngineExecutionContext
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface MappingCycleContext {

	/**
	 * Aborts the current mapping cycle. This will trigger a {@link CycleAbortedException} and all active reading/writing processes and associated transactions will be stopped.
	 * @param reason the reason message for aborting the mapping cycle. This message will be passed onto the {@link CycleAbortedException}.
	 */
	public void abortCycle(String reason);
}
