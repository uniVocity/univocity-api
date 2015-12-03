/*
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.univocity.api.stream;

import com.univocity.api.entity.custom.*;

import java.util.*;

public abstract class StreamingDataStoreConfiguration extends DataStoreConfiguration {

	private final Set<String> targetDataStores = new TreeSet<String>();
	private int limitOfRowsLoadedInMemory = 10000;

	public StreamingDataStoreConfiguration(String dataStoreName) {
		super(dataStoreName);
	}

	public void addTargetDataStore(String dataStoreName) {
		targetDataStores.add(dataStoreName);
	}

	public void addTargetDataStore(DataStoreConfiguration dataStore) {
		targetDataStores.add(dataStore.getDataStoreName());
	}


	public Set<String> getTargetDataStores() {
		return Collections.unmodifiableSet(targetDataStores);
	}

	public void setLimitOfRowsLoadedInMemory(int limitOfRowsLoadedInMemory) {
		this.limitOfRowsLoadedInMemory = limitOfRowsLoadedInMemory;
	}

	@Override
	public int getLimitOfRowsLoadedInMemory() {
		return limitOfRowsLoadedInMemory;
	}
}
