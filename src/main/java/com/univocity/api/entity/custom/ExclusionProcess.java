/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.custom;

/**
 * A process for removing records from a {@link CustomDataEntity}.
 * Instances of this process must created when {@link CustomDataEntity#prepareToDelete(String[])} is called from a user-provided entity implementation
 *
 * @see CustomDataEntity
 * @see CustomProcess
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface ExclusionProcess extends CustomProcess {

	/**
	 * Removes an existing row of data in the custom data entity that originated this update process.
	 * @param matchingValues identifies which row to delete. The field name sequence for the matching values is provided by uniVocity in {@link CustomDataEntity#prepareToUpdate(String[], String[])}
	 */
	public void deleteNext(Object[] matchingValues);

}
