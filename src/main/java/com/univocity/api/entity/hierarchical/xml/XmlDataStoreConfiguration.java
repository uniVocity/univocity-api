/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.hierarchical.xml;

import java.io.*;
import java.nio.charset.*;

import com.univocity.api.common.*;
import com.univocity.api.entity.hierarchical.*;

public class XmlDataStoreConfiguration extends HierarchicalDataStoreConfiguration {
	public XmlDataStoreConfiguration(String dataStoreName, File inputFile, Charset encoding) {
		super(dataStoreName, inputFile, encoding);
	}

	public XmlDataStoreConfiguration(String dataStoreName, File inputFile, String encoding) {
		super(dataStoreName, inputFile, encoding);
	}

	public XmlDataStoreConfiguration(String dataStoreName, File inputFile) {
		super(dataStoreName, inputFile);
	}

	public XmlDataStoreConfiguration(String dataStoreName, ReaderProvider inputProvider) {
		super(dataStoreName, inputProvider);
	}

	public XmlDataStoreConfiguration(String dataStoreName, String pathToinputFile, Charset encoding) {
		super(dataStoreName, pathToinputFile, encoding);
	}

	public XmlDataStoreConfiguration(String dataStoreName, String pathToinputFile, String encoding) {
		super(dataStoreName, pathToinputFile, encoding);
	}

	public XmlDataStoreConfiguration(String dataStoreName, String pathToinputFile) {
		super(dataStoreName, pathToinputFile);
	}

	public XmlDataStoreConfiguration(String dataStoreName, UrlReaderProvider inputProvider) {
		super(dataStoreName, inputProvider);
	}

	public XmlDataStoreConfiguration(String dataStoreName) {
		super(dataStoreName);
	}

}
