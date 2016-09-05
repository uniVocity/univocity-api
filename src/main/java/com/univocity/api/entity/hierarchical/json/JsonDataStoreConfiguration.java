/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.hierarchical.json;

import com.univocity.api.common.*;
import com.univocity.api.entity.hierarchical.*;

import java.io.*;
import java.nio.charset.*;

public class JsonDataStoreConfiguration extends HierarchicalDataStoreConfiguration {
	public JsonDataStoreConfiguration(String dataStoreName, File inputFile, Charset encoding) {
		super(dataStoreName, inputFile, encoding);
	}

	public JsonDataStoreConfiguration(String dataStoreName, File inputFile, String encoding) {
		super(dataStoreName, inputFile, encoding);
	}

	public JsonDataStoreConfiguration(String dataStoreName, File inputFile) {
		super(dataStoreName, inputFile);
	}

	public JsonDataStoreConfiguration(String dataStoreName, ReaderProvider inputProvider) {
		super(dataStoreName, inputProvider);
	}

	public JsonDataStoreConfiguration(String dataStoreName, String pathToinputFile, Charset encoding) {
		super(dataStoreName, pathToinputFile, encoding);
	}

	public JsonDataStoreConfiguration(String dataStoreName, String pathToinputFile, String encoding) {
		super(dataStoreName, pathToinputFile, encoding);
	}

	public JsonDataStoreConfiguration(String dataStoreName, String pathToinputFile) {
		super(dataStoreName, pathToinputFile);
	}

	public JsonDataStoreConfiguration(String dataStoreName, UrlReaderProvider inputProvider) {
		super(dataStoreName, inputProvider);
	}

	public JsonDataStoreConfiguration(String dataStoreName) {
		super(dataStoreName);
	}

	public JsonDataStoreConfiguration(String dataStoreName, FileProvider fileProvider) {
		super(dataStoreName, fileProvider);
	}

}
