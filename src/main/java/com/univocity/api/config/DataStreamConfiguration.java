/*
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.univocity.api.config;

import com.univocity.api.common.*;
import com.univocity.api.config.builders.*;
import com.univocity.api.entity.custom.*;
import com.univocity.api.stream.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class DataStreamConfiguration implements EventPersistence {

	private boolean persistEvents = false;
	private long activityReportFrequency = 0L;
	private File eventDirectory;
	private final String engineName;
	private final StreamingDataStoreConfiguration masterConfig;

	private final Set<DataStoreConfiguration> targetDataStores = new HashSet<DataStoreConfiguration>();
	private final Set<StreamEventListener> eventListeners = new LinkedHashSet<StreamEventListener>();

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

	public Set<StreamEventListener> getStreamEventListeners() {
		return Collections.unmodifiableSet(eventListeners);
	}

	public EventPersistence persistEvents() {
		persistEvents = true;
		return this;
	}

	public void addStreamEventListener(StreamEventListener listener) {
		this.eventListeners.add(listener);
	}

	@Override
	public void inDirectory(File directory) {
		Args.notNull(directory, "Directory for storing streamed events.");
		eventDirectory = directory;
	}

	public File getEventDirectory() {
		return eventDirectory;
	}

	public long getActivityReportFrequency() {
		return activityReportFrequency;
	}

	public void setActivityReportFrequency(long activityReportFrequency) {
		this.activityReportFrequency = activityReportFrequency;
	}

	public void setActivityReportFrequency(long activityReportFrequency, TimeUnit timeUnit) {
		this.activityReportFrequency = timeUnit.toMillis(activityReportFrequency);
	}
}