/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

/**
 * The <code>ReferenceMappingSetup</code> is the base configuration used to define which fields should be read from a source entity in an {@link EntityMapping}
 * to construct a reference to a destination entity.
 *
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
 * //Uses fields "wgt_nbr" and "wgt_seq" from the source entity "WGT_DET" to obtain the identifier associated to "weight".
 * //After getting the correct identifier of "weight", copy it to "weight_ref"
 *
 * EntityMapping weightDetails = mapping.map("WGT_DET", "weight_details");
 * weightDetails.reference().using("wgt_nbr", "wgt_seq").referTo("WGT", "weight").on("weight_ref");
 * ...
 * </pre></blockquote><hr>
 *
 * Using the above mapping, uniVocity will execute a query against its metadata requesting any values associated with <code>wgt_nbr</code> and <code>wgt_seq</code>,
 * where the source entity is <code>WGT</code> and the destination entity is <code>weight</code>. The returned identifiers for <code>weight</code> will then be
 * associated with the <code>weight_ref</code> field of <code>weight_details</code>
 *
 * @see EntityMapping
 * @see IdentifierMappingSetup
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface ReferenceMappingSetup {
	/**
	 * Associates one or more fields from the source entity in an {@link EntityMapping} with a reference in the destination entity.
	 *
	 * @param sourceFields the field names in the source entity to be associated with a reference to an entity in the destination.
	 * 		   Expressions are allowed within curly braces (i.e. "{expression}")
	 * @return the next step of this configuration: define what source entity has an identifier mapped with the given fields, and
	 *         what entity in the destination had its identifier associated with these fields (using {@link IdentifierMappingSetup}).
	 */
	public ReferencedEntity using(String... sourceFields);
}
