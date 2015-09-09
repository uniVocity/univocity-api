package com.univocity.api.common;

import java.io.*;

public class StringReaderProvider extends ReaderProvider {

	private String string;

	public StringReaderProvider(){
		this("");
	}

	public StringReaderProvider(String string){
		this.string = string;
	}

	public void setString(String string){
		this.string = string == null ? "" : string;
	}

	@Override
	public Reader getResource() {
		return new StringReader(string);
	}
}
