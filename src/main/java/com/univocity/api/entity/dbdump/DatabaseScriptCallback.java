/*******************************************************************************
 * Cpyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.entity.dbdump;

/**
 * A callback class that allows user interaction with DDL scripts parsed from database dump files. Used in
 * {@link DumpDataStoreConfiguration}
 *
 * While parsing database dumps, uniVocity will invoke the {@link #setScript(String)} method with after a new
 * DDL script has been parsed. Then, {@link #scriptParsed(int)} will be called to notify the user.
 *
 * @see DumpDataStoreConfiguration
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com
 *
 */
public abstract class DatabaseScriptCallback {

	private String script;

	/**
	 * Invoked by uniVocity when a script has been parsed from a database dump file.
	 * @param sequence a sequential number indicating the position of the script in the file, in relation to other DDL scripts.
	 * Insert statements and other database-specific commands that provide data are not considered.
	 */
	public abstract void scriptParsed(int sequence);

	/**
	 * Returns the last DDL script parsed from the database dump file
	 * @return the last DDL script or null if the parsing process is not executing.
	 */
	public final String getScript() {
		return this.script;
	}

	/**
	 * Modified the contents of the latest parsed script. uniVocity will invoke this method with a new
	 * script every time one is parsed from the database dump file. Users can modify the script from the {@link #scriptParsed(int)} method.
	 * @param script a DDL script parsed by uniVocity, or a user-defined script
	 */
	public final void setScript(String script) {
		this.script = script;
	}
}
