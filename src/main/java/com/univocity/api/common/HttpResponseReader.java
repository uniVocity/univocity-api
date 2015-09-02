package com.univocity.api.common;

import java.io.*;
import java.nio.charset.*;

public interface HttpResponseReader {

	void processResponse (HttpResponse response, InputStream responseBody, Charset encoding) throws Exception;
}
