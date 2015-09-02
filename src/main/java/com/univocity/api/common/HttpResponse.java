package com.univocity.api.common;

import com.univocity.api.common.helpers.*;

import java.io.*;
import java.net.*;
import java.util.*;

public interface HttpResponse extends Closeable {

	Reader getContentReader();

	String getRedirectionUrl();

	int getStatusCode();

	String getStatusMessage();

	String getCharset();

	String getContentType();

	URL getUrl();

	HttpMethodType getMethod();

	Map<String, String> getHeaders(String name);

	Map<String, String> getCookies();

	void close();
}
