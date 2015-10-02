/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;
import com.univocity.api.exception.*;

/**
 * The <code>UnmatchedReferenceHandling</code> configuration is obtained from a {@link UnmatchedReference} configuration
 * which is part of the configuration initialized by a call to {@link ReferenceMappingSetup#using(String...)}
 *
 * <p>It is to provide configuration options that define how to handle references that could not be matched.</p>
 *
 * <p>For example, consider the following information in uniVocity's metadata:</p>
 *
 * <hr><blockquote><pre>
 * Source entity (WGT)    Destination entity (weight)
 *     nbr | seq                    gid
 *     ----+----                  -------
 *     001 | 1                       1
 *     001 | 2                       2
 *     002 | 1                       3
 * </pre></blockquote><hr>
 *
 * <p>And the following mapping:</p>
 * <hr><blockquote><pre>
 * EntityMapping weightDetails = mapping.map("WGT_DET", "weight_details");
 * weightDetails.reference().using("wgt_nbr", "wgt_seq").referTo("WGT", "weight").on("weight_ref");
 * </pre></blockquote><hr>
 *
 * <p>If a record in the source entity <code>WGT_DET</code> contains <code>wgt_nbr = 1, seq = 3</code> and this is used to refer to
 * <code>WGT</code>, no results will come from uniVocity's metadata.</p>
 *
 * <p>This configuration class provides some options for handling like this.</p>
 *
 * @see ReferenceMappingSetup
 * @see FunctionCall
 * @see DataIntegrationEngine
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface UnmatchedReferenceHandling {
	/**
	 * Defines that in case a reference cannot be matched, the entire record must be discarded.
	 * <p><i>This completes the configuration started in {@link ReferenceMappingSetup#using(String...)} </i></p>
	 */
	public void discard();

	/**
	 * Defines that in case a reference cannot be matched, the mapping cycle must be aborted.
	 * A {@link CycleAbortedException} will be thrown from within the {@link DataIntegrationEngine}
	 * <p><i>This completes the configuration started in {@link ReferenceMappingSetup#using(String...)} </i></p>
	 */
	public void abort();

	/**
	 * Defines that in case a reference cannot be matched, this will be ignored and reference value will be applied anyway.
	 * <p><i>This completes the configuration started in {@link ReferenceMappingSetup#using(String...)} </i></p>
	 */
	public void ignore();

	/**
	 * Defines that in case a reference cannot be matched, this will be ignored and the reference will be set to null.
	 * <p><i>This completes the configuration started in {@link ReferenceMappingSetup#using(String...)} </i></p>
	 */
	public void setNull();

	/**
	 * Defines that in case a reference cannot be matched, the entire record must be discarded.
	 * <p><i>This completes the configuration started in {@link ReferenceMappingSetup#using(String...)} </i></p>
	 *
	 * @param functionNames the sequence of function names that will be executed against the reference values read from the source entity.
	 * <p><i>Note: </i> The record will still be discarded after the execution of these functions.
	 * 					<br>The sequence of declared function names establishes a chaining of functions:
	 *  				If the first function trims strings, then the second function will receive a trimmed String instead of the original value.</p>

	 */
	public void executeAndDiscard(String... functionNames);

	/**
	 * Defines that in case a reference cannot be matched, the mapping cycle must be aborted.
	 * A {@link CycleAbortedException} will be thrown from within the {@link DataIntegrationEngine}
	 * <p><i>This completes the configuration started in {@link ReferenceMappingSetup#using(String...)} </i></p>
	 *
	 * @param functionNames the sequence of function names that will be executed against the reference values read from the source entity.
	 * <p><i>Note: </i> The mapping cycle will still be aborted after the execution of these functions.
	 * 					<br>The sequence of declared function names establishes a chaining of functions:
	 *  				If the first function trims strings, then the second function will receive a trimmed String instead of the original value.</p>
	 */
	public void executeAndAbort(String... functionNames);

	/**
	 * Defines that in case a reference cannot be matched, this will be ignored and reference value will be applied anyway.
	 * Fields that are part of the reference will be set to whatever result is obtained from a given sequence of function calls, and the record will be mapped.
	 * <p><i>This completes the configuration started in {@link ReferenceMappingSetup#using(String...)} </i></p>
	 *
	 * @param functionNames the sequence of function names that will be executed against the reference values read from the source entity.
	 * <p><i>Note: </i> The sequence of declared function names establishes a chaining of functions:
	 *  				If the first function trims strings, then the second function will receive a trimmed String instead of the original value.</p>
	 */
	public void executeAndIgnore(String... functionNames);

	/**
	 * Defines that in case a reference cannot be matched, this will be ignored and the reference will be set to null.
	 * Fields that are part of the reference will be set to null regardless of the result obtained from the given sequence of function calls, and the record will be mapped.
	 * <p><i>This completes the configuration started in {@link ReferenceMappingSetup#using(String...)} </i></p>
	 *
	 * @param functionNames the sequence of function names that will be executed against the reference values read from the source entity.
	 * <p><i>Note: </i> The sequence of declared function names establishes a chaining of functions:
	 *  				If the first function trims strings, then the second function will receive a trimmed String instead of the original value.</p>
	 */
	public void executeAndSetNull(String... functionNames);
}
