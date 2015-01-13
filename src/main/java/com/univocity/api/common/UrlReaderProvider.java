/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.common;

import java.io.*;
import java.net.*;
import java.nio.charset.*;

public class UrlReaderProvider extends ReaderProvider {

	private final String url;
	private final Charset encoding;

	public UrlReaderProvider(String url) {
		this(url, (Charset) null);
	}

	public UrlReaderProvider(String url, String encoding) {
		this(url, Charset.forName(encoding));
	}

	public UrlReaderProvider(String url, Charset encoding) {
		Args.notBlank(url, "URL");
		this.url = url;
		this.encoding = encoding == null ? Charset.defaultCharset() : encoding;
	}

	@Override
	public Reader getResource() {
		try {
			return new InputStreamReader(new URL(this.url).openStream(), encoding);
		} catch (Exception ex) {
			throw new IllegalStateException("Unable to open URL '" + url + "'", ex);
		}
	}

}
