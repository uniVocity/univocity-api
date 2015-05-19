/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;
import com.univocity.api.entity.*;

/**
 * The <code>IdentifierType</code> configuration is obtained from a  {@link IdentifierMappingSetup} using {@link IdentifierMappingSetup#associate(String...)}.
 *
 * <p>It is used to define how the values read from the source entity are to be associated with the destination identifiers:
 * <ul>
 * 	<li>If the destination identifier is generated automatically, the source values will be associated with the generated identifier using uniVocity metadata</li>
 *  <li>Otherwise, the source values will be transferred to the appropriate destination fields that form an identifier. This association will also be stored in uniVocity metadata</li>
 * </ul>
 *
 * The following example demonstrates how an identifier mapping can be written:
 * <hr><blockquote><pre>
 *  // The identifier of this mapping contains two fields: "id" and "locale".
 *  // The mapping associates source fields "number" and "seq" to derive the value of "id"
 *  // and executes a function to get the locale ID of "en_US" to derive the value of "locale"
 *  mapping.identity().associate("number", "seq").to("id").and("{getLocaleId(en_US)}").to("locale");
 *
 *  // "number" and "seq" are a reference to "referred_entity".
 *  // This reference will be read from the metadata and stored in the "id" field.
 *  mapping.reference().using("number", "seq").referTo("source_entity", "referred_entity").on("id");
 * </pre></blockquote><hr>
 *
 * @see FieldMappingSetup
 * @see FunctionCall
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface IdentifierType extends IdentifierCopy {

	/**
	 * Defines what generated identifier field of a destination entity should be associated with values extracted from a
	 * selection of fields in the source entity.
	 *
	 * @param destinationField the name of the generated field in the destination entity
	 * @param valueIfEmpty an expression used to produce a value to insert when adding new records, in case no fields (other than the identifier) are mapped.
	 * 	      This is required to create a new record in the destination and obtain the generated identifier.
	 *
	 * @return the next (optional) step of an identifier mapping configuration: define what functions should be executed to
	 * transform input values before associating them to the generated identifier.
	 */
	public GeneratedIdentifierTransform toGeneratedId(String destinationField, String valueIfEmpty);

	/**
	 * Defines what generated identifier field of a destination entity should be associated with values extracted from a
	 * selection of fields in the source entity.

	 * @param destinationField the name of the generated field in the destination entity
	 *
	 * <p>When adding new records, in case no fields (other than the identifier) are mapped, uniVocity will insert a record with <code>null</code>
	 * to obtain the identifier generated in the destination.
	 *
	 * @return the next (optional) step of an identifier mapping configuration: define what functions should be executed to
	 * transform input values before associating them to the generated identifier.
	 */
	public GeneratedIdentifierTransform toGeneratedId(String destinationField);

	/**
	 * Defines what generated identifier field of a destination entity should be associated with values extracted from a
	 * selection of fields in the source entity.
	 *
	 * @param destinationField the name of the generated field in the destination entity
	 * @param valueIfEmpty an expression used to produce a value to insert when adding new records, in case no fields (other than the identifier) are mapped.
	 * 	      This is required to create a new record in the destination and obtain the generated identifier.
	 *
	 * @return the next (optional) step of an identifier mapping configuration: define what functions should be executed to
	 * transform input values before associating them to the generated identifier.
	 */
	public GeneratedIdentifierTransform toGeneratedId(FieldIdentifier destinationField, String valueIfEmpty);

	/**
	 * Defines what generated identifier field of a destination entity should be associated with values extracted from a
	 * selection of fields in the source entity.

	 * @param destinationField the name of the generated field in the destination entity
	 *
	 * <p>When adding new records, in case no fields (other than the identifier) are mapped, uniVocity will insert a record with <code>null</code>
	 * to obtain the identifier generated in the destination.
	 *
	 * @return the next (optional) step of an identifier mapping configuration: define what functions should be executed to
	 * transform input values before associating them to the generated identifier.
	 */
	public GeneratedIdentifierTransform toGeneratedId(FieldIdentifier destinationField);
}
