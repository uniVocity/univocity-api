/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.engine;

/**
 * The <code>EntityMappingContext</code> is available to {@link RowReader} instances used during the execution of a data mapping between two entities.
 * 
 * It provides information specific to the entities being mapped
 *  
 * @see RowReader
 * @see RowMappingContext
 * @see EngineExecutionContext
 * @see MappingCycleContext
 *  
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface EntityMappingContext {

	/**
	 * Returns the name of the source data store whose data entities are being read.
	 * @return the source data store name
	 */
	public String getSourceDataStore();

	/**
	 * Returns the name of the destination data store whose data entities are receiving mapped data.
	 * @return the destination data store name
	 */
	public String getDestinationDataStore();

	/**
	 * Returns the name of the source data entity that is being read
	 * @return the source data entity name
	 */
	public String getSourceEntity();

	/**
	 * Returns the name of the destination data entity that is receiving data mapped from the source entity.
	 * @return the destination data entity name
	 */
	public String getDestinationEntity();

	/**
	 * Skips the mapping between the source and destination entities. Rows mapped (if any) before this method is called will be persisted.
	 */
	public void skipEntityMapping();

	/**
	 * Informs whether the current mapping is for removal of records in the destination entity.
	 * In general, when mappings are configured to use deletion of records is uniVocity will automatically generate a mapping for exclusion.
	 *  
	 * <p>For example: suppose <code>GroupMember</code> has a reference to <code>Group</code>. 
	 * 
	 * If no removal of records is enabled, the mappings will be executed in the following order:
	 * <ul>
	 *  <li>map: <code>source -> Group</code></li>
	 *  <li>map: <code>otherSource -> GroupMember</code></li>
	 * </ul>
	 * 
	 * If removal is enabled in these mappings then uniVocity may generate exclusion mappings to be executed in reverse order.
	 * The exact removal sequence depends on the mapping configuration. In this example, GroupMember records that might be associated to Group will be removed first.
	 *  <ul>
	 *  <li>remove: <code>otherSource -> GroupMember</code></li>
	 *  <li>remove: <code>source -> Group</code></li>
	 *  <li>map: <code>source -> Group</code></li>
	 *  <li>map: <code>otherSource -> GroupMember</code></li>
	 * </ul>
	 *  
	 * @return true if this mapping will identify and remove records in the destination; false otherwise.  
	 */
	public boolean isExclusionMapping();

	/**
	 * Identifies whether the current mapping has been skipped.
	 * @return true if the current mapping has been skipped; false otherwise
	 */
	public boolean isEntityMappingSkipped();
}
