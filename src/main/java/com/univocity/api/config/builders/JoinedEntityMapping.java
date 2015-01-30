/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

public interface JoinedEntityMapping {
	
	/**
	 * Configures the mapping of one or more fields in the source entity to the destination fields used as identifiers.
	 * @return the identity mapping builder that exposes the appropriate configuration options required to map one or more fields used as identifiers.
	 */
	public IdentifierMappingSetup identity();

	/**
	 * Configures the mapping of one or more fields from the source entity to the destination.
	 * @return the field mapping builder that exposes the appropriate configuration options required to map one or more fields between two data entities.
	 */
	public FieldMappingSetup value();

	/**
	 * Configures the mapping of one or more fields in the source entity that are used to construct a reference to a destination entity.
	 * The source fields will be used for querying uniVocity's metadata to restore values associated with the identifier of an entity. For example:
	 *
	 * <hr><blockquote><pre>
	 * //Maps fields "nbr" and "seq" from the source entity "WGT" to a destination entity "weight"
	 *
	 * EntityMapping weightMapping = mapping.map("WGT", "weight");
	 * weightMapping.identity().associate("nbr", "seq").toGeneratedId("gid");
	 * ...
	 * </pre></blockquote><hr>
	 *
	 * When the above mapping is executed, values read from <code>nbr</code> and <code>seq</code> will be associated with the generated identifier <code>gid</code>:
	 * <hr><blockquote><pre>
	 * Source entity (WGT)    Destination entity (weight)
	 *     nbr | seq                    gid
	 *     ----+----                  -------
	 *     001 | 1                       1
	 *     001 | 2                       2
	 *     002 | 1                       3
	 * </pre></blockquote><hr>
	 *
	 * With this information on the metadata, a reference mapping can be defined as:
	 *
	 * <hr><blockquote><pre>
	 * //Uses fields "nbr" and "seq" from the source entity "WGT" to obtain the identifier associated with "weight".
	 * //After getting the correct identifier of "weight", copy it to "weight_id"
	 *
	 * EntityMapping weightDetails = mapping.map("WGT", "weight_details");
	 * weightDetails.reference().using("nbr", "seq").referTo("WGT", "weight").on("weight_id");
	 * ...
	 * </pre></blockquote><hr>
	 *
	 * Using the above mapping, uniVocity will execute a query against its metadata requesting for any values associated with <code>nbr</code> and <code>seq</code>,
	 * where the source entity is <code>WGT</code> and the destination entity is <code>weight</code>. The returned identifiers for <code>weight</code> will then be
	 * associated with the <code>weight_id</code> field in <code>weight_details</code>.
	 *
	 *
	 * @return the reference mapping builder that exposes the appropriate configuration options required to map one or more
	 *         fields to references to other entities in the destination data store.
	 */
	public ReferenceMappingSetup reference();

	/**
	 * Associates a function to a given list of source field names or expressions. When reading from the source entity, the function will be executed and its result
	 * will be send to the destination fields. The given function will be executed after other function sequences already applied to these fields.
	 *
	 * @param functionName the name of the function to associate to fields of the source entity.
	 * @param sourceFieldNames field names/expressions against which the given function should be applied to.
	 */
	public void transformFields(String functionName, String... sourceFieldNames);
}
