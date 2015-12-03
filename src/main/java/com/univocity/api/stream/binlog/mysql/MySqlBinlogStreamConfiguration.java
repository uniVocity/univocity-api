/*
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.univocity.api.stream.binlog.mysql;

import com.univocity.api.stream.*;

public class MySqlBinlogStreamConfiguration extends StreamingDataStoreConfiguration {

	private String hostname = "localhost";
	private int port = 3306;
	private String schema = null;
	private String username = "root";
	private String password = null;
	private long connectionTimeout = 3000L;

	public MySqlBinlogStreamConfiguration(String streamName) {
		super(streamName);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(long connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	@Override
	public String toString() {
		return "MySqlBinlogStreamConfiguration{" +
				"hostname='" + hostname + '\'' +
				", port=" + port +
				", schema='" + schema + '\'' +
				", username='" + username + '\'' +
				'}';
	}
}