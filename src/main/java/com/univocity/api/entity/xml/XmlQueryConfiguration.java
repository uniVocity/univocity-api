/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.xml;

import com.univocity.api.common.*;

public class XmlQueryConfiguration extends XmlReadingConfiguration {

	private final String query;

	private int retries = 0;
	private long retryInterval = 2000;

	public XmlQueryConfiguration(String queryURL) {
		Args.notBlank(queryURL, "XML query URL");
		this.query = queryURL;
	}

	public final String getQuery() {
		return query;
	}

	public final int getRetries() {
		return retries;
	}

	public final void setRetries(int retries) {
		this.retries = retries;
	}

	public final long getRetryInterval() {
		return retryInterval;
	}

	public final void setRetryInterval(long retryInterval) {
		this.retryInterval = retryInterval;
	}
}
