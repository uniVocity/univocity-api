/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.custom;

import com.univocity.api.config.builders.*;

/**
 * The CustomDataEntity defines the essential methods that allow data manipulation in a user-managed resource.
 *
 * <p>Instances of this interface are expected to be provided by {@link CustomDataStore} in {@link CustomDataStore#getDataEntities()}
 *
 * @see CustomDataStore
 * @see EntityMapping
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public interface CustomDataEntity extends CustomReadableEntity {

	/**
	 * Initializes a writing process in this data entity.
	 *
	 * @param fieldNames a sequence of names identifying what fields will receive data in each new record created by uniVocity
	 * @return a {@link WritingProcess} object that is responsible for inserting new records into this entity.
	 */
	public WritingProcess prepareToWrite(String[] fieldNames);

	/**
	 * Initializes a update process in this data entity.
	 *
	 * @param fieldsToUpdate a sequence of names identifying what fields will have their data updated by uniVocity
	 * @param fieldsToMatch identifies which fields will be used to identify updated records.
	 * @return a {@link UpdateProcess} object that is responsible for updating existing records in this entity.
	 */
	public UpdateProcess prepareToUpdate(String[] fieldsToUpdate, String[] fieldsToMatch);

	/**
	 * Initializes an exclusion process in this data entity.
	 * The field names used to identify records to be deleted will be provided by uniVocity.
	 *
	 * @param fieldsToMatch the field names used to identify records to be removed
	 * @return an {@link ExclusionProcess} object that is responsible for removing records from this entity.
	 */
	public ExclusionProcess prepareToDelete(String[] fieldsToMatch);

	/**
	 * Removes all data contained in this data entity. Subsequent reads after executing this method are expected to not return any records.
	 */
	public void deleteAll();
}
