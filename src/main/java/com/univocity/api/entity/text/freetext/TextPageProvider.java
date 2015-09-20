package com.univocity.api.entity.text.freetext;

import java.io.*;

public interface TextPageProvider extends Closeable {

	void beginReading();

	String nextPage();


}
