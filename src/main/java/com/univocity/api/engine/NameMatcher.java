/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.engine;

import com.univocity.api.config.builders.*;

/**
 * A <code>NameMatcher</code> provides a custom implementation for automatic detection of mappings based on entity names.
 * 
 * By default, {@link DataStoreMapping#autodetectMappings()} and {@link EntityMapping#autodetectMappings()} will automatically
 * create associations between entities and their fields based on their names. 
 * Entities with similar names will be automatically associated. Underscores and spaces are ignored, for example: <code>entity1</code> will 
 * be associated to <code>ENTITY 1</code> or <code>ENTITY_1</code>
 * 
 * <p>To override this behavior and provide a custom auto detection mechanism, 
 * {@link DataStoreMapping#autodetectMappings(NameMatcher, NameMatcher)} and {@link EntityMapping#autodetectMappings(NameMatcher)}
 * accept an implementation of <code>NameMatcher</code>.
 *  
 * @see DataStoreMapping
 * @see EntityMapping
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface NameMatcher {

	/**
	 * Identifies whether two entity or field names match and should be associated to each other automatically. 
	 * @param nameOnsource the name in the source
	 * @param nameOnDestination the name in the destination
	 * @return true if the entity of field name on source must be mapped to the entity or field name in the destination; false otherwise.
	 */
	public boolean matches(String nameOnsource, String nameOnDestination);
}
