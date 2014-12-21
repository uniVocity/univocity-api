package com.univocity.api.entity;

import com.univocity.api.engine.*;
import com.univocity.api.entity.custom.*;

/**
 * A data entity managed by uniVocity. It provides a consistent abstraction for accessing records from any source of data.
 * Instances of this interface must be obtained from {@link DataIntegrationEngine} through {@link DataIntegrationEngine#getEntity(String)}
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com
 *
 */
public interface Entity {
	/**
	 * <p>Start iterating over the records of this data entity. uniVocity will start a {@link ReadingProcess} in the background which will block when the number of
	 * rows loaded in memory reaches the specified limit specified by the parent data store.</p>
	 * 
	 * <p>If you do not intend to read all rows, invoke the {@link #stopReading()} 
	 * method to stop the background {@link ReadingProcess} and clear up rows loaded but not yet processed.</p>
	 * 
	 * @param fieldNames the fields to be read from the given data entity. Each row returned by the {@link #readNext()} method will organize the values in this specified order. 
	 * 		If no names are given, all fields will be read from the entity.
	 */
	public void beginReading(String... fieldNames);
	
	/**
	 * Reads the next record stored by this data entity. 
	 * @return the next row read from the underlying data entity.
	 */
	public Object[] readNext();
	
	/**
	 * Stops the active reading process created by the {@link #beginReading(String...)} method. 
	 */
	public void stopReading();
}
