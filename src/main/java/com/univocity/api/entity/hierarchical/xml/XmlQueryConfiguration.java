/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.hierarchical.xml;

import java.nio.charset.*;

import com.univocity.api.common.*;
import com.univocity.api.entity.hierarchical.*;

public class XmlQueryConfiguration extends HierarchyQueryConfiguration {

	public XmlQueryConfiguration(UrlReaderProvider queryURL) {
		super(queryURL);
	}

	public XmlQueryConfiguration(String queryUrl) {
		this(new UrlReaderProvider(queryUrl));
	}

	public XmlQueryConfiguration(String url, String encoding) {
		this(new UrlReaderProvider(url, encoding));
	}

	public XmlQueryConfiguration(String url, Charset encoding) {
		this(new UrlReaderProvider(url, encoding));
	}
}
