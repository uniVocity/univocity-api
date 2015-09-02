/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.common;

import com.univocity.api.*;

import java.io.*;
import java.nio.charset.*;

public class UrlReaderProvider extends ReaderProvider {

	private int retries = 0;
	private long retryInterval = 2000;
	private final Charset defaultEncoding;
	private HttpResponse response;
	protected HttpRequest request;

	public UrlReaderProvider(String url) {
		this(url, (Charset) null);
	}

	public UrlReaderProvider(String url, String defaultEncoding) {
		this(url, Charset.forName(defaultEncoding));
	}

	public UrlReaderProvider(String url, Charset defaultEncoding) {
		Args.notBlank(url, "URL");
		this.request = new HttpRequest(url);
		this.defaultEncoding = defaultEncoding == null ? Charset.forName("UTF-8") : defaultEncoding;
	}

	public HttpRequest getRequestConfiguration(){
		return request;
	}

	public final Charset getDefaultEncoding(){
		return defaultEncoding;
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

	public HttpResponse getResponse() {
		if (response == null) {
			response = Univocity.provider().build(HttpResponse.class, this);
		}
		return response;
	}

	@Override
	public final Reader getResource() {
		try {
			return getResponse().getContentReader();
		} catch (Exception ex) {
			throw new IllegalStateException("Unable to open URL '" + request.getUrl() + "'", ex);
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + request.getUrl() + "]";
	}
}
