/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

public interface MergeSetup {
	/**
	 * Takes values of multiple fields from the source entity in an {@link EntityMapping} and merges them into a single value using a function.
	 *
	 * <p>Example:
	 *
	 * <hr><blockquote><pre>
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

}
