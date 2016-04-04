/*
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.univocity.api.entity.jdbc;

public enum ScriptType {
	CREATE_TABLE,
	DROP_TABLE,
	ADD_COLUMN,
	MODIFY_COLUMN,
	DROP_COLUMN,
	ADD_PRIMARY_KEY,
	DROP_PRIMARY_KEY,
	ADD_FOREIGN_KEY,
	DROP_FOREIGN_KEY,
	ADD_UNIQUE_CONSTRAINT,
	DROP_UNIQUE_CONSTRAINT,
	CREATE_INDEX,
	DROP_INDEX;

}
