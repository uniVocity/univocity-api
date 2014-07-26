/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.custom;

import com.univocity.api.engine.*;

/**
 * A TransactionalOperation is created by uniVocity before applying data changes to entities of all data stores that are 
 * affected by each data mapping within a cycle (see {@link DataIntegrationEngine#executeCycle()}).
 * <br>These data changes are performed inside the {@link #execute()} method when uniVocity 
 * invokes {@link CustomDataStore#executeInTransaction(TransactionalOperation)}.
 * 
 * @see CustomDataStore
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public abstract class TransactionalOperation {

	/**
	 * Executes a transactional operation prepared by uniVocity when executing a 
	 * each mapping in {@link DataIntegrationEngine#executeCycle()}.
	 * 
	 * <p><b>Note</b> each data mapping in the cycle has its own independent TransactionalOperation. 
	 * A failure in one of the mappings in the cycle will not revert changes made in mappings already executed. 
	 */
	public abstract void execute();
}
