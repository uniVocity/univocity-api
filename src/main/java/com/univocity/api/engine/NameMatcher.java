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
 * Entities with similar names will be automatically associated. The identifier case will be ignored. For example: <code>identifier1</code> will
 * be associated with <code>IDENTIFIER1</code> or even <code>IdEnTiFiEr1</code>.
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
	 * Identifies whether two entity or field names match and should be associated with each other automatically.
	 * @param nameInSource the name in the source
	 * @param nameInDestination the name in the destination
	 * @return {@code true} if the entity of field name in source must be mapped to the entity or field name in the destination, otherwise {@code false}.
	 */
	public boolean matches(String nameInSource, String nameInDestination);
}
