/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.config.*;
import com.univocity.api.engine.*;

/**
 * The <code>IdentifierMappingSetup</code> is the base configuration used to define what fields from the source entity, 
 * when mapped to the destination, represent an identifier in the destination entity of the {@link EntityMapping}.
 * 
 * <p>An identifier has the property of uniquely identify an individual record. Records with the same values in 
 * their identifier fields will be considered by uniVocity as the same record.
 * 
 * <p>Values read from the source entity to produce identifiers in the destination will be stored into uniVocity metadata
 * tables (as defined in {@link MetadataSettings}) in order to enable features such as:
 * 
 * <ul>
 * 	<li>Mapping of references</li>
 *  <li>Enabling or disabling data updates</li>
 *  <li>Exclusion of absent records (those mapped in previous mapping cycles but not in the source anymore)</li>
 * </ul>
 * 
 * <p>Values used as identifiers MUST will have their String representation used in uniVocity metadata tables. You must ensure identifiers
 * can be converted from/to String consistently. If required, {@link FunctionCall}s or input {@link RowReader}s can be used to convert identifiers. 
 * 
 * @see ReferenceMappingSetup
 * @see EntityMapping
 * @see DataIntegrationEngine
 * @see MetadataSettings
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface IdentifierMappingSetup {
	/**
	 * Associates one or more fields from the source entity in an {@link EntityMapping} to the identifier of the destination entity. 
	 * 
	 * @param sourceFields the field names in the source entity to be associated to an identifier of the destination. 
	 * 		   Expressions are allowed within curly braces (i.e. "{expression}") 
	 * @return the next step of this configuration: define what fields of the destination are identifiers and how they will
	 *         receive the values read from the source.
	 */
	public IdentifierType associate(String... sourceFields);
}
