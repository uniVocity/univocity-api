/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.text;

import java.util.*;
import java.util.Map.Entry;

import com.univocity.api.common.*;
import com.univocity.api.entity.custom.*;

/**
 * This is the parent class for all configuration classes used by text-based data entities.
 * It provides essential configuration settings and sensible defaults for reading from and writing to text in conformance to a particular format (such as CSV, for example).
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 * @param <F> the configuration class that manages a specific text format.
 */
public abstract class TextEntityConfiguration<F extends TextFormat> extends TextEntityDefaults<F> {

	protected String[] headers;
	protected String[] identifiers;
	protected int[] fieldLengths;

	private int numberOfRecordsToRead = -1;

	protected TextEntityConfiguration() {

	}

	/**
	 * Returns the sequence of field names used to refer to columns in the input/output text of an entity.
	 * This overrides any headers extracted from a text input (when {@link #isHeaderExtractionEnabled()}{@code == true})
	 * <p><i>Defaults to {@code null}.</i>
	 * @return the field name sequence to be associated with each column in the input/output.
	 */
	public String[] getHeaders() {
		return headers == null ? null : headers.clone();
	}

	/**
	 * Defines a sequence of field names used to refer to columns in the input/output text of an entity.
	 * This overrides any headers extracted from a text input (when {@link #isHeaderExtractionEnabled()}{@code == true})
	 * @param headers the field name sequence to be associated with each column in the input/output.
	 */
	public void setHeaders(String... headers) {
		Args.notEmpty(headers, "Header sequence");
		validateHeaders(headers, this.identifiers, this.fieldLengths);
		this.headers = headers.clone();
	}

	/**
	 * Defines a sequence of field names to use as data entity identifiers.
	 * @param identifiers sequence of field names to use as identifiers of each record of the data entity
	 */
	public void setIdentifiers(String... identifiers) {
		Args.notEmpty(identifiers, "Identifier sequence");
		validateHeaders(this.headers, identifiers, this.fieldLengths);
		this.identifiers = identifiers.clone();
	}

	/**
	 * Returns the sequence of field names to use as data entity identifiers.
	 * <p><i>defaults to null.</i>
	 * @return the sequence of field names to use as identifiers of each record of the data entity
	 */
	public String[] getIdentifiers() {
		return identifiers == null ? null : identifiers.clone();
	}

	/**
	 * Returns the number of valid records to be parsed before the reading process is stopped.
	 * <br>A negative value indicates there's no limit and all records in the input will be read.
	 * <p><i>Defaults to -1.</i>
	 *
	 * @return the number of records to read before stopping the reading process.
	 */
	public int getNumberOfRecordsToRead() {
		return numberOfRecordsToRead;
	}

	/**
	 * Defines the number of valid records to be parsed before the reading process is stopped.
	 * <br>A negative value indicates there's no limit and all records in the input will be read.
	 * <p><i>Defaults to -1.</i>
	 *
	 * @param numberOfRecordsToRead the number of records to read before stopping the reading process.
	 */
	public void setNumberOfRecordsToRead(int numberOfRecordsToRead) {
		if (numberOfRecordsToRead < 0) {
			numberOfRecordsToRead = -1;
		}
		this.numberOfRecordsToRead = numberOfRecordsToRead;
	}

	/**
	 * Returns the length of each column of records returned by the data entity.
	 * <br>This information is only used when enabling database-like operations on the entity via {@link DataStoreConfiguration#enableDatabaseOperationsIn(String...)}
	 * <p><i>Defaults to {@code null}.</i>
	 * @return fieldLengths the length of each field of the records produced by the data entity.
	 */
	public int[] getFieldLengths() {
		return fieldLengths == null ? null : fieldLengths.clone();
	}

	/**
	 * Associates a length to each column of records returned by the data entity.
	 * <br>This information is only required when enabling database-like operations on the entity through {@link DataStoreConfiguration#enableDatabaseOperationsIn(String...)}
	 * @param fieldLengths the length of each field of the records produced by the data entity.
	 */
	public void setFieldLengths(int... fieldLengths) {
		Args.notEmpty(fieldLengths, "Field lengths");
		validateHeaders(this.headers, this.identifiers, fieldLengths);
		this.fieldLengths = fieldLengths.clone();
	}

	/**
	 * Validates headers and information associated with them.
	 * @param headers the headers to validate
	 * @param identifiers the identifiers that can be found among the given headers
	 * @param lengths the lengths of each field
	 */
	protected void validateHeaders(String[] headers, String[] identifiers, int[] lengths) {
		if (headers != null) {
			if (lengths != null && lengths.length != headers.length) {
				if (lengths == this.fieldLengths) {
					throw new IllegalArgumentException("Invalid sequence of field headers. The number of field lengths (" + lengths.length + ") and headers must match. A sequence of " + headers.length + " headers was provided.");
				} else {
					throw new IllegalArgumentException("Invalid sequence of field lengths. The number of headers (" + headers.length + ") and lengths must match. A sequence of " + lengths.length + " lengths was provided.");
				}
			}
			if (identifiers != null) {
				Set<String> notFound = new HashSet<String>();
				for (String identifier : identifiers) {
					String originalId = identifier;
					identifier = identifier.trim().toLowerCase();
					boolean found = false;
					for (String header : headers) {
						header = header.trim().toLowerCase();
						if (header.equals(identifier)) {
							found = true;
							break;
						}
					}
					if (!found) {
						notFound.add(originalId);
					}
				}
				if (!notFound.isEmpty()) {
					throw new IllegalArgumentException("Identifiers not found in declared headers: " + notFound + " could not be found in " + Arrays.toString(headers));
				}
			}
		}
	}

	/**
	 * Defines a sequence of field names used to refer to columns in the input/output text of an entity, along with their lengths.
	 * This overrides any headers extracted from a text input (when {@link #isHeaderExtractionEnabled()}{@code == true})
	 * @param fields a {@link LinkedHashMap} containing the sequence of fields to be associated with each column in the input/output, with their respective lengths.
	 */
	public void setFieldsAndLengths(LinkedHashMap<String, Integer> fields) {
		Args.notNull(fields, "Map of field and their lengths");

		String[] names = new String[fields.size()];
		int[] lengths = new int[fields.size()];

		int i = 0;

		for (Entry<String, Integer> entry : fields.entrySet()) {
			String fieldName = entry.getKey();
			Args.notBlank(fieldName, "Field name");

			Integer fieldLength = entry.getValue();
			Args.positive(fieldLength, "Legth of field " + fieldName);

			names[i] = fieldName;
			lengths[i] = fieldLength;
			i++;
		}

		this.headers = null;
		this.fieldLengths = null;

		this.setHeaders(names);
		this.setFieldLengths(lengths);
	}
}
