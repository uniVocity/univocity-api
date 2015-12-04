/*
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.univocity.api.config;

import com.univocity.api.common.*;
import com.univocity.api.entity.custom.*;
import com.univocity.api.stream.*;

import java.util.*;

public class DataStreamConfiguration {

	private final String engineName;
	private final StreamingDataStoreConfiguration masterConfig;

	private final Set<DataStoreConfiguration> targetDataStores = new HashSet<DataStoreConfiguration>();

	public DataStreamConfiguration(String engineName, StreamingDataStoreConfiguration masterConfig) {
		Args.notBlank(engineName, "Engine name");
		this.engineName = engineName;

		Args.notNull(masterConfig, "Streaming data store configuration");
		this.masterConfig = masterConfig;
	}

	public String getEngineName() {
		return engineName;
	}

	public StreamingDataStoreConfiguration getStreamingDataStoreConfiguration() {
		return masterConfig;
	}

	public void addTargetDataStore(DataStoreConfiguration dataStore) {
		targetDataStores.add(dataStore);
	}

	public Set<DataStoreConfiguration> getTargetDataStores() {
		return Collections.unmodifiableSet(targetDataStores);
	}

}