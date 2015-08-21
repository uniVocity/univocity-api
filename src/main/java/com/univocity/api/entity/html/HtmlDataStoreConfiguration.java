package com.univocity.api.entity.html;

import com.univocity.api.common.*;
import com.univocity.api.entity.custom.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class HtmlDataStoreConfiguration extends DataStoreConfiguration {

	private final Map<String, HtmlEntityConfiguration> entities = new TreeMap<String, HtmlEntityConfiguration>();
	private final HtmlEntityConfiguration defaultEntityConfiguration = new HtmlEntityConfiguration();

	//TODO: refactor this code to reuse most of what is common between this class and HierarchicalDataStoreConfiguration.
	private final FileProvider inputFile;
	private final ReaderProvider inputProvider;

	private int limitOfRowsLoadedInMemory = 1000;

	public HtmlDataStoreConfiguration(String dataStoreName, File inputFile) {
		this(dataStoreName, new FileProvider(inputFile));
	}

	public HtmlDataStoreConfiguration(String dataStoreName, File inputFile, String encoding) {
		this(dataStoreName, new FileProvider(inputFile, encoding));
	}

	public HtmlDataStoreConfiguration(String dataStoreName, File inputFile, Charset encoding) {
		this(dataStoreName, new FileProvider(inputFile, encoding));
	}

	public HtmlDataStoreConfiguration(String dataStoreName, String pathToinputFile) {
		this(dataStoreName, new FileProvider(pathToinputFile));
	}

	public HtmlDataStoreConfiguration(String dataStoreName, String pathToinputFile, String encoding) {
		this(dataStoreName, new FileProvider(pathToinputFile, encoding));
	}

	public HtmlDataStoreConfiguration(String dataStoreName, String pathToinputFile, Charset encoding) {
		this(dataStoreName, new FileProvider(pathToinputFile, encoding));
	}

	public HtmlDataStoreConfiguration(String dataStoreName, UrlReaderProvider inputProvider) {
		this(dataStoreName, (ReaderProvider) inputProvider);
	}

	public HtmlDataStoreConfiguration(String dataStoreName, ReaderProvider inputProvider) {
		super(dataStoreName);
		this.inputProvider = inputProvider;
		this.inputFile = null;
	}

	private HtmlDataStoreConfiguration(String dataStoreName, FileProvider inputFile) {
		super(dataStoreName);
		this.inputFile = inputFile;
		this.inputProvider = null;
	}

	public HtmlDataStoreConfiguration(String dataStoreName) {
		super(dataStoreName);
		this.inputFile = null;
		this.inputProvider = null;
	}

	public Set<String> getEntityNames(){
		return new TreeSet<String>(entities.keySet());
	}

	public HtmlEntityConfiguration configureEntity(String entityName){
		if(entities.containsKey(entityName)){
			return entities.get(entityName);
		} else{
			HtmlEntityConfiguration config = new HtmlEntityConfiguration(entityName);
			entities.put(entityName, config);
			return config;
		}
	}

	public final FileProvider getInputFile() {
		return inputFile;
	}

	public final ReaderProvider getInputProvider() {
		return inputProvider;
	}

	public void setLimitOfRowsLoadedInMemory(int limitOfRowsLoadedInMemory){
		limitOfRowsLoadedInMemory = limitOfRowsLoadedInMemory;
	}

	@Override
	public int getLimitOfRowsLoadedInMemory() {
		return limitOfRowsLoadedInMemory;
	}
}
