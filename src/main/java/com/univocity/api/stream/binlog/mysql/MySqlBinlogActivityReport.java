package com.univocity.api.stream.binlog.mysql;

import com.univocity.api.stream.*;

/**
 * Reports activity on MySQL's binary log stream
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public interface MySqlBinlogActivityReport extends ActivityReport {

	/**
	 * @return binary log position of last processed event. This value changes with each incoming event.
	 */
	long currentPosition();

	/**
	 * @return binary log filename (can be {@code null}). This value is changes once all events are consumed and the
	 * binlog stream switches to the next file.
	 */
	String currentBinaryLogFile();

	/**
	 * @return binary log position of last available position in the binlog. This value changes every time a
	 * change is made in the database.
	 */
	long lastAvailablePosition();

	/**
	 * @return last available binary log filename (can be {@code null}). This value is changes when the database
	 * rotates the binary log.
	 */
	String lastAvailableBinaryLogFile();

	/**
	 * Queries whether all binlog events have been consumed.
	 *
	 * @return {@code true} if all binlog events have been consumed, otherwise {@code false}
	 */
	boolean allEventsConsumed();
}
