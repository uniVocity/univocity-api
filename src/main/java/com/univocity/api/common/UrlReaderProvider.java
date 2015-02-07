/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.common;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.*;

public class UrlReaderProvider extends ReaderProvider {

	private static final Logger log = Logger.getLogger(UrlReaderProvider.class.getName());

	private final String url;
	private final Charset encoding;
	private int retries = 0;
	private long retryInterval = 2000;
	private int retryCount = 0;

	private final LinkedHashMap<String, String> requestProperties = new LinkedHashMap<String, String>();

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

	public final void addRequestProperty(String property, String value) {
		requestProperties.put(property, value);
	}

	@Override
	public Reader getResource() {
		try {
			URL url = new URL(this.url);
			HttpURLConnection rc = (HttpURLConnection) url.openConnection();

			for (Entry<String, String> property : requestProperties.entrySet()) {
				rc.addRequestProperty(property.getKey(), property.getValue());
			}
			rc.setRequestMethod("GET");
			InputStreamReader reader = new InputStreamReader(rc.getInputStream(), encoding);
			retryCount = 0;
			return reader;
		} catch (IOException ex) {
			if (retries > 0) {
				try {
					log.log(Level.FINE, "Unable to open URL '" + this.url + "', retrying after " + retryInterval + "ms", ex);
					Thread.sleep(retryInterval);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					retryCount = 0;
					throw new IllegalStateException("Thread interrupted while retrying connection on URL '" + this.url + "' after receiving error " + ex.getMessage(), ex);
				}
				if (retryCount >= retries) {
					int count = retryCount;
					retryCount = 0;
					throw new IllegalStateException("Cannot open URL after " + count + " retries", ex);
				}
				retryCount++;
				return getResource();
			}
			throw new IllegalStateException("Unable to open URL '" + url + "'", ex);
		} catch (Exception ex) {
			throw new IllegalStateException("Unable to open URL '" + url + "'", ex);
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + url + "]";
	}

}
