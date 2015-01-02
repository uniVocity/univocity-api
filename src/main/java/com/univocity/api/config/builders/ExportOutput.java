/*******************************************************************************
 * Copyright (c) 2015 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.builders;

import java.io.*;
import java.util.*;

import org.omg.IOP.*;

/**
 * Executes an {@link Export} operation over a given set of data entities and submits the results to a given output.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com
 *
 */
public interface ExportOutput {

	/**
	 * Exports the result of an {@link Export} operation as an object.
	 * @param <T> the type of the result object
	 * @param expectedResultType the type of the result object
	 * @return an object containing the result of the {@link Export} operation
	 */
	public <T> T toObject(Class<T> expectedResultType);

	/**
	 * Exports the result of an {@link Export} operation as a file.
	 *
	 * <p>If the output is text-based, the default system encoding will be used to encode the text.</p>
	 *
	 * @param outputFile the file which will receive the export results.
	 */
	public void toFile(File outputFile);

	/**
	 * Exports the text result of an {@link Export} to a file.
	 * @param outputFile the file which will receive the export results.
	 * @param encoding the encoding used to encode the results.
	 */
	public void toFile(File outputFile, String encoding);

	/**
	 * Exports the text result of an {@link Export} to a File.
	 * @param outputFile the file which will receive the export results.
	 * @param encoding the encoding used to encode the results.
	 */
	public void toFile(File outputFile, Encoding encoding);

	/**
	 * Exports the result of an {@link Export} operation as a file.
	 *
	 * <p>If the output is text-based, the default system encoding will be used to encode the text.</p>
	 *
	 * @param pathToOutputFile path to the output file which will receive the export results.
	 */
	public void toFile(String pathToOutputFile);

	/**
	 * Exports the text result of an {@link Export} to a File.
	 * @param pathToOutputFile path to the output file which will receive the export results.
	 * @param encoding the encoding used to encode the results.
	 */
	public void toFile(String pathToOutputFile, String encoding);

	/**
	 * Exports the text result of an {@link Export} to a File.
	 * @param pathToOutputFile path to the output file which will receive the export results.
	 * @param encoding the encoding used to encode the results.
	 */
	public void toFile(String pathToOutputFile, Encoding encoding);

	/**
	 * Exports the result of an {@link Export} operation as a {@link Map}.
	 * Keys on the map contain the entity name used to perform the export.
	 *
	 * @param <T> type of each result object associated to each entity
	 * @param expectedResultType type of each result object associated to each entity
	 * @return a {@link Map} associating the name of each exported entity with their corresponding result
	 */
	public <T> Map<String, T> toMap(Class<T> expectedResultType);

	/**
	 * Exports the result of an {@link Export} operation as multiple files in a given directory.
	 * Each file will have the name of the entity used to perform the export. File extensions depend on the export operation itself.
	 *
	 * <p>If the output is text-based, the default system encoding will be used to encode the text.</p>
	 * @param outputDir the directory in the file system where the export result files will be created.
	 *
	 */
	public void toDirectory(File outputDir);

	/**
	 * Exports the result of an {@link Export} operation as multiple files in a given directory.
	 * Each file will have the name of the entity used to perform the export. File extensions depend on the export operation itself.
	 *
	 * @param outputDir the directory in the file system where the export result files will be created.
	 * @param encoding the encoding used to encode the export result files.
	 */
	public void toDirectory(File outputDir, String encoding);

	/**
	 * Exports the result of an {@link Export} operation as multiple files in a given directory.
	 * Each file will have the name of the entity used to perform the export. File extensions depend on the export operation itself.
	 *
	 * @param outputDir the directory in the file system where the export result files will be created.
	 * @param encoding the encoding used to encode the export result files.
	 */
	public void toDirectory(File outputDir, Encoding encoding);

	/**
	 * Exports the result of an {@link Export} operation as multiple files in a given directory.
	 * Each file will have the name of the entity used to perform the export. File extensions depend on the export operation itself.
	 *
	 * <p>If the output is text-based, the default system encoding will be used to encode the text.</p>
	 *
	 * @param pathToOutputDir path to a directory in the file system where the export result files will be created.
	 */
	public void toDirectory(String pathToOutputDir);

	/**
	 * Exports the result of an {@link Export} operation as multiple files in a given directory.
	 * Each file will have the name of the entity used to perform the export. File extensions depend on the export operation itself.
	 *
	 * @param pathToOutputDir path to a directory in the file system where the export result files will be created.
	 * @param encoding the encoding used to encode the export result files.
	 */
	public void toDirectory(String pathToOutputDir, String encoding);

	/**
	 * Exports the result of an {@link Export} operation as multiple files in a given directory.
	 * Each file will have the name of the entity used to perform the export. File extensions depend on the export operation itself.
	 *
	 * @param pathToOutputDir path to a directory in the file system where the export result files will be created.
	 * @param encoding the encoding used to encode the export result files.
	 */
	public void toDirectory(String pathToOutputDir, Encoding encoding);

}
