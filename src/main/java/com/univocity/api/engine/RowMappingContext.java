/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.engine;

import com.univocity.api.config.builders.*;
import com.univocity.api.entity.*;

/**
 * The <code>RowMappingContext</code> is available to {@link RowReader} instances used during the execution of a data mapping between two entities.
 *
 * It provides information specific to the mapping being executed, as well as access to the {@link DataIntegrationEngine} execution context.
 *
 *
 * @see RowReader
 * @see EntityMappingContext
 * @see EngineExecutionContext
 * @see MappingCycleContext
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface RowMappingContext extends EntityMappingContext, EngineExecutionContext, MappingCycleContext {

	/**
	 * Discards the current row being processed. This behavior varies depending where the {@link RowReader} is applied:
	 *
	 * <ul>
	 *  <li><i><b>while reading from the input </b></i><code>{@link EntityMapping#addInputRowReader(RowReader)}</code>:
	 * 		<br>discards the input row. The discarded row won't be available to <code>RowReader</code> instances that manipulate output rows.
	 *  </li>
	 *  <li><i><b>before writing to the output </b></i><code>{@link EntityMapping#addInputRowReader(RowReader)}</code>:
	 *  	<br>discards the output row. The discarded row won't be available to <code>RowReader</code> instances that manipulate persisted rows.
	 *  </li>
	 *  <li><i><b>after writing to the output </b></i><code>{@link EntityMapping#addInputRowReader(RowReader)}</code>:
	 *  	<br>does nothing.
	 *  </li>
	 * </ul>
	 */
	public void discardRow();

	/**
	 * Returns the position of a field in the input row.
	 * @param fieldName the name of a field in the input row.
	 * @return the position of the given field name in the input row.
	 */
	public int getInputIndex(String fieldName);

	/**
	 * Returns the position of a field in the output row (if available).
	 * @param fieldName the name of a field in the output row.
	 * @return the position of the given field name in the output row.
	 */
	public int getOutputIndex(String fieldName);

	/**
	 * Return the current count of rows processed.
	 * @return the current count of rows processed.
	 */
	public int getCurrentRow();

	public boolean containsInputField(String name);

	/**
	 * Returns the sequence of fields read from the input data entity.
	 * @return the sequence of fields read from the input data entity.
	 */
	public String[] getInputFields();

	/**
	 * Returns the sequence of fields read from the output data entity (if available).
	 * @return the sequence of fields read from the output data entity.
	 */
	public String[] getOutputFields();

	public boolean containsOutputField(String name);

	/**
	 * Returns the value of a given field in the current input row.
	 * @param fieldName name of the input field whose value will be returned.
	 * @return the value of the field in the current input row.
	 */
	public Object getInputValue(String fieldName);

	/**
	 * Returns the value of a given field in the current output row.
	 * @param fieldName name of the output field whose value will be returned.
	 * @return the value of the field in the current output row.
	 */
	public Object getOutputValue(String fieldName);

	/**
	 * Modifies the value of a given field in the current input row.
	 * @param fieldName name of the input field whose value will be modified.
	 * @param value the new value of the field in the current input row.
	 */
	public void setInputValue(String fieldName, Object value);

	/**
	 * Modifies the value of a given field in the current output row.
	 * @param fieldName name of the output field whose value will be modified.
	 * @param value the new value of the field in the current output row.
	 */
	public void setOutputValue(String fieldName, Object value);

	/**
	 * Returns the value of a given field in the current input row.
	 * @param <T> fieldType the type of the value stored in the input field
	 * @param fieldName name of the input field whose value will be returned.
	 * @param fieldType the class of the value stored in the input field
	 * @return the value of the field in the current input row.
	 *
	 */
	public <T> T getInputValue(String fieldName, Class<T> fieldType);

	/**
	 * Returns the value of a given field in the current output row.
	 * @param <T> fieldType the type of the value stored in the output field
	 * @param fieldName name of the output field whose value will be returned.
	 * @param fieldType the class of the value stored in the output field
	 * @return the value of the field in the current output row.
	 */
	public <T> T getOutputValue(String fieldName, Class<T> fieldType);

	/**
	 * Prints the input row data displaying fields names and the values associated to them 
	 * @return a String with input row data
	 */
	public String printInputRow();

	/**
	 * Prints the output row data displaying fields names and the values associated to them 
	 * @return a String with output row data 
	 */
	public String printOutputRow();

	/**
	 * Returns the position of a field in the input row.
	 * @param fieldName the name of a field in the input row.
	 * @return the position of the given field name in the input row.
	 */
	public int getInputIndex(FieldIdentifier fieldName);

	/**
	 * Returns the position of a field in the output row (if available).
	 * @param fieldName the name of a field in the output row.
	 * @return the position of the given field name in the output row.
	 */
	public int getOutputIndex(FieldIdentifier fieldName);


	public boolean containsInputField(FieldIdentifier name);


	public boolean containsOutputField(FieldIdentifier name);

	/**
	 * Returns the value of a given field in the current input row.
	 * @param fieldName name of the input field whose value will be returned.
	 * @return the value of the field in the current input row.
	 */
	public Object getInputValue(FieldIdentifier fieldName);

	/**
	 * Returns the value of a given field in the current output row.
	 * @param fieldName name of the output field whose value will be returned.
	 * @return the value of the field in the current output row.
	 */
	public Object getOutputValue(FieldIdentifier fieldName);

	/**
	 * Modifies the value of a given field in the current input row.
	 * @param fieldName name of the input field whose value will be modified.
	 * @param value the new value of the field in the current input row.
	 */
	public void setInputValue(FieldIdentifier fieldName, Object value);

	/**
	 * Modifies the value of a given field in the current output row.
	 * @param fieldName name of the output field whose value will be modified.
	 * @param value the new value of the field in the current output row.
	 */
	public void setOutputValue(FieldIdentifier fieldName, Object value);

	/**
	 * Returns the value of a given field in the current input row.
	 * @param <T> fieldType the type of the value stored in the input field
	 * @param fieldName name of the input field whose value will be returned.
	 * @param fieldType the class of the value stored in the input field
	 * @return the value of the field in the current input row.
	 *
	 */
	public <T> T getInputValue(FieldIdentifier fieldName, Class<T> fieldType);

	/**
	 * Returns the value of a given field in the current output row.
	 * @param <T> fieldType the type of the value stored in the output field
	 * @param fieldName name of the output field whose value will be returned.
	 * @param fieldType the class of the value stored in the output field
	 * @return the value of the field in the current output row.
	 */
	public <T> T getOutputValue(FieldIdentifier fieldName, Class<T> fieldType);

	/**
	 * Returns the values of a given list of fields in the current input row.
	 * @param fieldNames names of the input fields whose values will be returned.
	 * @return the values of the fields in the current input row.
	 */
	public Object[] getInputValues(FieldIdentifier ... fieldNames);

	/**
	 * Returns the values of a given list of fields in the current output row.
	 * @param fieldNames names of the output fields whose values will be returned.
	 * @return the values of the fields in the current output row.
	 */
	public Object[] getOutputValues(FieldIdentifier ... fieldNames);

	/**
	 * Returns the values of a given list of fields in the current input row.
	 * @param fieldNames names of the input fields whose values will be returned.
	 * @return the values of the fields in the current input row.
	 */
	public Object[] getInputValues(String ... fieldNames);

	/**
	 * Returns the values of a given list of fields in the current output row.
	 * @param fieldNames names of the output fields whose values will be returned.
	 * @return the values of the fields in the current output row.
	 */
	public Object[] getOutputValues(String ... fieldNames);

}
