package com.univocity.api.stream;

import com.univocity.api.entity.*;

public interface StreamingContext {

	/**
	 * Discards the current row being processed.
	 */
	public void discard();

	/**
	 * Returns the position of a field in the input row.
	 * @param fieldName the name of a field in the input row.
	 * @return the position of the given field name in the input row.
	 */
	public int getIndex(String fieldName);



	public boolean containsField(String name);

	/**
	 * Returns the sequence of fields read from the input data entity.
	 * @return the sequence of fields read from the input data entity.
	 */
	public String[] getFields();


	/**
	 * Returns the value of a given field in the current input row.
	 * @param fieldName name of the input field whose value will be returned.
	 * @return the value of the field in the current input row.
	 */
	public Object getValue(String fieldName);

	/**
	 * Modifies the value of a given field in the current input row.
	 * @param fieldName name of the input field whose value will be modified.
	 * @param value the new value of the field in the current input row.
	 */
	public void setValue(String fieldName, Object value);

	/**
	 * Returns the value of a given field in the current input row.
	 * @param <T> fieldType the type of the value stored in the input field
	 * @param fieldName name of the input field whose value will be returned.
	 * @param fieldType the class of the value stored in the input field
	 * @return the value of the field in the current input row.
	 *
	 */
	public <T> T getValue(String fieldName, Class<T> fieldType);

	/**
	 * Prints the input row data displaying fields names and the values associated to them 
	 * @return a String with input row data
	 */
	public String printRow();


	/**
	 * Returns the position of a field in the input row.
	 * @param fieldName the name of a field in the input row.
	 * @return the position of the given field name in the input row.
	 */
	public int getIndex(FieldIdentifier fieldName);


	public boolean containsField(FieldIdentifier name);


	/**
	 * Returns the value of a given field in the current input row.
	 * @param fieldName name of the input field whose value will be returned.
	 * @return the value of the field in the current input row.
	 */
	public Object getValue(FieldIdentifier fieldName);

	/**
	 * Modifies the value of a given field in the current input row.
	 * @param fieldName name of the input field whose value will be modified.
	 * @param value the new value of the field in the current input row.
	 */
	public void setValue(FieldIdentifier fieldName, Object value);

	/**
	 * Returns the value of a given field in the current input row.
	 * @param <T> fieldType the type of the value stored in the input field
	 * @param fieldName name of the input field whose value will be returned.
	 * @param fieldType the class of the value stored in the input field
	 * @return the value of the field in the current input row.
	 *
	 */
	public <T> T getValue(FieldIdentifier fieldName, Class<T> fieldType);

	
	/**
	 * Returns the values of a given list of fields in the current input row.
	 * @param fieldNames names of the input fields whose values will be returned.
	 * @return the values of the fields in the current input row.
	 */
	public Object[] getValues(FieldIdentifier ... fieldNames);

	
	/**
	 * Returns the values of a given list of fields in the current input row.
	 * @param fieldNames names of the input fields whose values will be returned.
	 * @return the values of the fields in the current input row.
	 */
	public Object[] getValues(String ... fieldNames);

	public Object[] getRecordIdentifierValues();

	public int[] getRecordIdentifierIndexes();

	public String[] getRecordIdentifierFields();
}
