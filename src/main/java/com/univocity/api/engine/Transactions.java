/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.engine;

import com.univocity.api.entity.custom.*;

/**
 * The <code>Transactions</code> enumeration provides options to determine how transactions should be organized in a data mapping cycle.
 * It is used by in {@link DataIntegrationEngine#executeCycle(Transactions)} to 
 *  
 * @see DataIntegrationEngine
 * @see TransactionalOperation
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public enum Transactions {
	/**
	 * Indicates all mappings in a data mapping cycle should be executed within a single transaction.
	 * If one of the mappings in the cycle fail, all changes of the already executed mappings should be rolled back.
	 * 
	 * <p><b>Note: </b> If all mappings executed and an error occurs while committing, 
	 * 					the changes already committed won't be rolled back.  
	 */
	PER_CYCLE,

	/**
	 * Indicates each mapping in a data mapping cycle will be executed within an individual transaction.
	 * If one of the mappings in the cycle fail, all changes of the already executed mappings will be kept.
	 * 
	 * <p><b>Note: </b> Data mappings with exclusion of records enabled cannot be executed with this setting.
	 * 					uniVocity executes data deletion in special mappings generated internally. 
	 * 					These are executed in independently of the actual data mappings.  
	 *   				An {@link IllegalArgumentException} will be thrown if any mapping in the cycle is configured with exclusion enabled. 
	 */
	PER_MAPPING
}
