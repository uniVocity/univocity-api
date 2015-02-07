/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.hierarchical;

import java.nio.charset.*;

import com.univocity.api.common.*;

public abstract class HierarchyQueryConfiguration extends PathReaderConfiguration {

	private final UrlReaderProvider query;

	public HierarchyQueryConfiguration(UrlReaderProvider queryUrl) {
		Args.notNull(queryUrl, "Query URL");
		this.query = queryUrl;
	}

	public HierarchyQueryConfiguration(String queryUrl) {
		this(new UrlReaderProvider(queryUrl));
	}

	public HierarchyQueryConfiguration(String url, String encoding) {
		this(new UrlReaderProvider(url, encoding));
	}

	public HierarchyQueryConfiguration(String url, Charset encoding) {
		this(new UrlReaderProvider(url, encoding));
	}

	public final UrlReaderProvider getQueryProvider() {
		return query;
	}
}
