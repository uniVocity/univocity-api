package com.univocity.api.stream;

/**
 * Reports activity on a data stream.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public interface ActivityReport {

	/**
	 * Returns the total amount of time (in milliseconds) elapsed since the last activity detected on the stream.
	 * Will return {@code 0} if activity is detected within the interval given by
	 * {@link com.univocity.api.config.DataStreamConfiguration#activityReportFrequency}
	 *
	 * @return how much time has passed since the last bit of data from the observed stream has been consumed.
	 */
	long inactiveTime();
}
