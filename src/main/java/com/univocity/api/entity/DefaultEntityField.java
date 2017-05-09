/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity;

import com.univocity.api.common.*;

/**
 * Provides basic information for a data entity field.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public class DefaultEntityField {

	private final String name;
	private boolean isIdentifier = false;
	private boolean isNullable = true;
	private boolean isGenerated = false;
	private Object defaultValue = null;
	private int length = -1;
	private int scale = -1;
	private Class<?> type = null;
	private Integer sqlTypeCode = null;
	private String comments;

	/**
	 * Creates an entity field instance with its name.
	 *
	 * @param name a name that describes a particular field in each record of an entity.
	 */
	public DefaultEntityField(String name) {
		Args.notBlank(name, "Field name");
		this.name = name.trim();
	}

	/**
	 * Obtains the name that describes a particular field in each record of an entity.
	 *
	 * @return the field name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Indicates whether values for this field are generated automatically.
	 *
	 * @return true if values of this field are generated automatically; false otherwise
	 */
	public boolean isGenerated() {
		return isGenerated;
	}

	/**
	 * Indicates this field is part of the identifier of a record.
	 *
	 * @return true if this field is part of the identifier of a record; false otherwise
	 */
	public boolean isIdentifier() {
		return isIdentifier;
	}

	/**
	 * Indicates whether this field can store null values.
	 *
	 * @return true if this field accepts null values; false otherwise
	 */
	public boolean isNullable() {
		return this.isNullable;
	}

	/**
	 * Obtains a default value to use when the value of this field is null.
	 *
	 * @return the default value of this field.
	 */
	public Object getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * Defines this field as an identifier (or part of an identifier) of records produced by a data entity.
	 *
	 * @param isIdentifier a flag indicating this field is part of the identifiers of a data entity.
	 */
	public void setIdentifier(boolean isIdentifier) {
		this.isIdentifier = isIdentifier;
	}

	/**
	 * Defines whether this field can store null values.
	 *
	 * @param isNullable a flag indicating whether this field accepts null values.
	 */
	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}

	/**
	 * Defines a default value to use when the value of this field is null.
	 *
	 * @param defaultValue the default value of this field.
	 */
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Updates the indication that values for this field are generated automatically.
	 *
	 * @param isGenerated a flag indicating values for this field are generated automatically.
	 */
	public void setGenerated(boolean isGenerated) {
		this.isGenerated = isGenerated;
	}

	/**
	 * Obtains the maximum length of this field. A value of -1 means the length is undefined.
	 * <p><i>defaults to -1</i></p>
	 *
	 * @return the length of this field.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Defines the maximum length of this field. A value of -1 means the length is undefined. Zero is not a valid length.
	 *
	 * @param length the length of this field.
	 */
	public void setLength(int length) {
		if (length < 0) {
			length = -1;
		}
		this.length = length;
	}

	/**
	 * Obtains the type of values stored by this field. If defined, uniVocity will
	 * try to convert values before writing to this field.
	 * <p><i>defaults to {@code null}</i></p>
	 *
	 * @return the type of values stored by this field.
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * Defines the type of values stored by this field. uniVocity will
	 * try to convert values before writing to this field.
	 *
	 * @param type the type of values stored by this field.
	 */
	public void setType(Class<?> type) {
		this.type = type;
	}

	/**
	 * Returns the scale of numeric values stored by this field. A value of -1 means the scale is undefined.
	 * <p><i>defaults to -1</i></p>
	 *
	 * @return the scale to use when writing decimal values.
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * Defines the scale of numeric values stored by this field.
	 *
	 * @param scale the scale to use when writing decimal values.
	 */
	public void setScale(int scale) {
		if (scale < 0) {
			scale = -1;
		}
		this.scale = scale;
	}

	/**
	 * Returns the SQL type code (from {@link java.sql.Types}) that identifies a compatible database type for values of this field.
	 * A value of {@code null} means the type is undefined.
	 * <p><i>defaults to {@code null}</i></p>
	 *
	 * @return the SQL type to use when values of this field interact with a database.
	 */
	public Integer getSqlTypeCode() {
		return sqlTypeCode;
	}

	/**
	 * Defines a SQL type code (from {@link java.sql.Types}) that identifies a compatible database type for values of this field.
	 * A value of {@code null} means the type is undefined.
	 *
	 * @param sqlTypeCode the SQL type to use when values of this field interact with a database.
	 */
	public void setSqlTypeCode(Integer sqlTypeCode) {
		this.sqlTypeCode = sqlTypeCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		DefaultEntityField that = (DefaultEntityField) o;

		if (isIdentifier != that.isIdentifier) {
			return false;
		}
		if (isNullable != that.isNullable) {
			return false;
		}
		if (isGenerated != that.isGenerated) {
			return false;
		}
		if (length != that.length) {
			return false;
		}
		if (scale != that.scale) {
			return false;
		}
		if (!name.equals(that.name)) {
			return false;
		}
		if (defaultValue != null ? !defaultValue.equals(that.defaultValue) : that.defaultValue != null) {
			return false;
		}
		if (type != null ? !type.equals(that.type) : that.type != null) {
			return false;
		}
		return sqlTypeCode != null ? sqlTypeCode.equals(that.sqlTypeCode) : that.sqlTypeCode == null;

	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + (isIdentifier ? 1 : 0);
		result = 31 * result + (isNullable ? 1 : 0);
		result = 31 * result + (isGenerated ? 1 : 0);
		result = 31 * result + (defaultValue != null ? defaultValue.hashCode() : 0);
		result = 31 * result + length;
		result = 31 * result + scale;
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (sqlTypeCode != null ? sqlTypeCode.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Field: '" + name + '\'' +
				", length=" + length +
				", scale=" + scale +
				", type=" + type +
				'}';
	}

	public void copyPropertiesTo(DefaultEntityField field) {
		field.isIdentifier = isIdentifier;
		field.isNullable = isNullable;
		field.isGenerated = isGenerated;
		field.defaultValue = defaultValue;
		field.length = length;
		field.scale = scale;
		field.type = type;
		field.sqlTypeCode = sqlTypeCode;
		field.comments = comments;
	}


	public void copyPropertiesFrom(DefaultEntityField field) {
		field.copyPropertiesTo(this);
	}

	/**
	 * Returns a comment describing the field
	 *
	 * @return remarks about the field, if any.
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Defines a comment describing the field
	 *
	 * @param comments remarks about the field.
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
}
