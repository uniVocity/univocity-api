package com.univocity.api.common;

import java.io.*;
import java.net.*;
import java.util.*;

public interface HttpResponse extends Closeable {

	String getRedirectionUrl();

	int getStatusCode();

	String getStatusMessage();

	String getCharset();

	String getContentType();

	URL getUrl();

	HttpMethodType getMethod();

	Map<String, String> getHeaders();

	Map<String, String> getCookies();

	void close();

	Reader getContentReader();

	void readContent(HttpResponseReader responseReader);

	int getRetriesPerformed();
}
