package com.univocity.api.config.builders;

/**
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public interface RecordTrackingFields {

	public ExclusionWithMetadata using(String ... fieldsAtDestination);
}
