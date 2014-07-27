/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import com.univocity.api.engine.*;

/**
 * The <code>FieldMappingSetup</code> is the base configuration used to define what fields should be read from a source entity in an {@link EntityMapping}.
 *
 * @see EntityMapping
 * @see RowReader
 * @see FunctionCall
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface FieldMappingSetup {
	/**
	 * Selects a field from the source entity in an {@link EntityMapping} to be split into multiple values using a function.
	 *
	 * <p>Example:
	 *
	 * <p><hr><blockquote><pre>
	 * //Splits "field_name" using "split_function" to generate values to "field_1" and "field_2" in the destination entity.
	 *
	 *  mapping.value().split("field_name").with("split_function").into("field_1", "field_2");
	 * </pre></blockquote><hr>
	 *
	 * @param sourceField the field name in the source entity to be split into multiple values. Expressions are allowed within curly braces (i.e. "{expression}")
	 * @return the next step of this configuration: define a split function that will process values read from the given field.
	 */
	public Split split(String sourceField);

	/**
	 * Merges values of multiple fields from the source entity in an {@link EntityMapping} and merge them into a single value using a function.
	 *
	 * <p>Example:
	 *
	 * <p><hr><blockquote><pre>
	 *  //Merges "item", "price" and the result of the function "getDate()" using "pipe_separator_function"
	 *  //to generate a single value for the field "receipt" in the destination entity.
	 *
	 *  mapping.value().merge("item", "price", "{getDate()}").using("pipe_separator_function").into("receipt");
	 * </pre></blockquote><hr>
	 *
	 * @param sourceFields the field names in the source entity to be split into multiple values. Expressions are allowed within curly braces (i.e. "{expression}")
	 * @return the next step of this configuration: define a merge function that will process values read from the given fields.
	 */
	public Merge merge(String... sourceFields);

	/**
	 * Copies the value of one field from the source entity in an {@link EntityMapping} and write it to a field in the destination entity.
	 *
	 * <p>Example:
	 *
	 * <p><hr><blockquote><pre>
	 *  //Copies "CHO_Factor" to "carbohydrate_factor".
	 *
	 *  mapping.value().copy("CHO_Factor").to("carbohydrate_factor");
	 *
	 *  //copies the result of an expression, in this case the value of the variable "version", to "version_number".
	 *  mapping.value().copy("{$version}").to("version_number");
	 * </pre></blockquote><hr>
	 *
	 * @param sourceField the field name in the source entity to have its value copied to the a field in the destination.
	 * 		  Expressions are allowed within curly braces (i.e. "{expression}")
	 * @return the next step of this configuration: define which destination fields will receive the values read from the given field names.
	 */
	public CopyToSingleField copy(String sourceField);

	/**
	 * Copies values of multiple fields from the source entity in an {@link EntityMapping} and write them to multiple fields in the destination entity.
	 *
	 * <p>Example:
	 *
	 * <p><hr><blockquote><pre>
	 *  //Copies "CHO_Factor", "Fat_Factor" and "Pro_Factor" to "carbohydrate_factor", "fat_factor" and "protein_factor" respectively.
	 *
	 *  mapping.value().copy("CHO_Factor", "Fat_Factor", "Pro_Factor").to("carbohydrate_factor", "fat_factor", "protein_factor");
	 * </pre></blockquote><hr>
	 *
	 * @param sourceFields the field names in the source entity to have their values copied to the same number of fields in the destination.
	 * Expressions are allowed within curly braces (i.e. "{expression}")
	 * @return the next step of this configuration: define which destination fields will receive the values read from the given field names.
	 */
	public CopyToMultipleFields copy(String... sourceFields);

	/**
	 * Reads values of multiple fields from the source entity in an {@link EntityMapping} but does not transfer them to the destination.
	 * This is used to obtain values from the input that might be required for custom processing such as logging, statistics or generating data structures and objects.
	 * These values will be available to {@link RowReader}s while executing the mapping.
	 *
	 * <p>Example:
	 *
	 * <p><hr><blockquote><pre>
	 *  //Reads "CHO_Factor" and "Fat_Factor"  to memory for custom processing.
	 *  mapping.value().read("CHO_Factor", "Fat_Factor");
	 * </pre></blockquote><hr>
	 *
	 * @param sourceFields the field names in the source entity to have their values loaded in memory for processing.
	 * Expressions are allowed within curly braces (i.e. "{expression}")
	 * @return the next step of this configuration: define functions applied to each value read.
	 */
	public CopyTransform read(String... sourceFields);
}
