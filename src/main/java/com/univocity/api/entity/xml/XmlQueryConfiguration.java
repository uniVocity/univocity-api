/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.xml;

import com.univocity.api.common.*;

public class XmlQueryConfiguration extends XmlReadingConfiguration {

	private final String query;

	public XmlQueryConfiguration(String queryURL) {
		Args.notBlank(queryURL, "XML query URL");
		this.query = queryURL;
	}

	public final String getQuery() {
		return query;
	}

}
