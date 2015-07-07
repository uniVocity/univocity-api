/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity;

import java.util.*;

import com.univocity.api.engine.*;
import com.univocity.api.entity.custom.*;

/**
 * A data entity managed by uniVocity. It provides a consistent abstraction for accessing records from any source of data.
 * Instances of this interface must be obtained from {@link DataIntegrationEngine} through {@link DataIntegrationEngine#getEntity(String)}
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface Entity extends Comparable<Entity> {

	public boolean containsField(String name);

	public boolean containsField(FieldIdentifier name);

	Set<String> getFieldNames();

	Set<String> getIdentifierFieldNames();

	DefaultEntityField getField(String name);

	DefaultEntityField getField(FieldIdentifier name);

	List<DefaultEntityField> getFields(String... names);

	List<DefaultEntityField> getFields(Collection<String> names);
	
	List<DefaultEntityField> getFields();

	List<DefaultEntityField> getFields(FieldIdentifier... names);

	Set<EntityRelationship> getRelationshipsFrom();

	Set<EntityRelationship> getRelationshipsFrom(EntityRelationshipType type);

	Set<EntityRelationship> getRelationshipsTo();

	Set<EntityRelationship> getRelationshipsTo(EntityRelationshipType type);

	/**
	 * <p>Start iterating over the records of this data entity. uniVocity will start a {@link ReadingProcess} in the background which will block when the number of
	 * rows loaded in memory reaches the specified limit specified by the parent data store.</p>
	 *
	 * <p>If you do not intend to read all rows, invoke the {@link #stopReading()}
	 * method to stop the background {@link ReadingProcess} and clear up rows loaded but not yet processed.</p>
	 *
	 * @param fieldNames the fields to be read from the given data entity. Each row returned by the {@link #readNext()} method will organize the values in this specified order.
	 * 		If no names are given, all fields will be read from the entity.
	 */
	void beginReading(String... fieldNames);

	/**
	 * <p>Start iterating over the records of this data entity. uniVocity will start a {@link ReadingProcess} in the background which will block when the number of
	 * rows loaded in memory reaches the specified limit specified by the parent data store.</p>
	 *
	 * <p>If you do not intend to read all rows, invoke the {@link #stopReading()}
	 * method to stop the background {@link ReadingProcess} and clear up rows loaded but not yet processed.</p>
	 *
	 * @param fieldNames the fields to be read from the given data entity. Each row returned by the {@link #readNext()} method will organize the values in this specified order.
	 * 		If no names are given, all fields will be read from the entity.
	 */
	void beginReading(FieldIdentifier... fieldNames);

	/**
	 * Reads the next record stored by this data entity.
	 * @return the next row read from the underlying data entity.
	 */
	Object[] readNext();

	/**
	 * Stops the active reading process created by the {@link #beginReading(String...)} method.
	 */
	void stopReading();

	/**
	 * Returns the value of a given field in the current row.
	 * @param fieldName name of the field whose value will be returned.
	 * @return the value of the field in the current row.
	 */
	Object valueOf(String fieldName);

	/**
	 * Returns the value of a given field in the current row.
	 * @param fieldName name of the field whose value will be returned.
	 * @return the value of the field in the current row.
	 */
	Object valueOf(FieldIdentifier fieldName);

	/**
	 * Returns the value of a given field in the current row.
	 * @param fieldName name of the field whose value will be returned.
	 * @param type expected type of the value stored by the given field
	 * @return the value of the field in the current row.
	 * @param <T> type expected type of the value stored by the given field
	 */
	<T> T valueOf(String fieldName, Class<T> type);

	/**
	 * Returns the value of a given field in the current row.
	 * @param fieldName name of the field whose value will be returned.
	 * @param type expected type of the value stored by the given field
	 * @return the value of the field in the current row.
	 * @param <T> type expected type of the value stored by the given field
	 */
	<T> T valueOf(FieldIdentifier fieldName, Class<T> type);

	Object valueOf(int fieldIndex);

	<T> T valueOf(int fieldIndex, Class<T> type);

	/**
	 * Returns the position of a given field in the current row.
	 * @param fieldName name of the field whose value will be returned.
	 * @return the position of the field in the current row.
	 */
	int indexOf(String fieldName);

	/**
	 * Returns the current row read from the input.
	 * @return the current row read from the input, or {@code null} if the reading process has not begun or has been stopped
	 */
	Object[] getCurrentRow();

	/**
	 * Returns the name of this data entity
	 * @return the name of this data entity
	 */
	String getEntityName();

	String getDataStoreName();
}
