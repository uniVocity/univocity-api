/*
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.univocity.api.stream;

import com.univocity.api.entity.custom.*;

public abstract class StreamingDataStoreConfiguration extends DataStoreConfiguration {

	private int limitOfRowsLoadedInMemory = 10000;

	public StreamingDataStoreConfiguration(String dataStoreName) {
		super(dataStoreName);
	}

	public void setLimitOfRowsLoadedInMemory(int limitOfRowsLoadedInMemory) {
		this.limitOfRowsLoadedInMemory = limitOfRowsLoadedInMemory;
	}

	@Override
	public int getLimitOfRowsLoadedInMemory() {
		return limitOfRowsLoadedInMemory;
	}


}
