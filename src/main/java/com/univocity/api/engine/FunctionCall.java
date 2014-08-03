/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.engine;

import com.univocity.api.config.annotation.*;

/**
 * A custom function call that can be used from within the {@link DataIntegrationEngine} to obtain values from the current {@link EngineScope}, and to execute any external
 * operation that produces values required while executing data mappings.
 *
 * <p>Use {@link DataIntegrationEngine#addFunction(EngineScope, String, FunctionCall)} to associate it with a name and a scope.</p>
 * <p>Use {@link DataIntegrationEngine#addFunctions(Object...)} to automatically create functions with the methods annotated with {@link FunctionWrapper}.
 *    The object instances will be used to execute the function calls and there is no restriction on their state. Keep in mind that, depending on your configuration,
 *    concurrent function invocations might occur. In this situation it is important either to avoid sharing an object with multiple functions or to synchronize any code that alters its state.</p>
 *
 * <p>Calls executed within uniVocity will cache the function result in the given scope and will only be executed again once data stored in that scope is lost.</p>
 *
 * <p>Functions can be used to transform mapped values. They are expressed in mappings using special expressions. For example, consider the function <i>getLocaleId</i>:</p>
 *
 * <hr><blockquote><pre>
 * engine.addFunction(EngineScope.APPLICATION, "getLocaleId", new FunctionCall&lt;Integer, String&gt;() {
 *	   public Integer execute(String locale) {
 *	       return getLocaleId(locale); // fetches the ID of a given locale from an external service.
 *	   }
 * });
 * </pre></blockquote><hr>
 *
 * <p>Here we use the <i>getLocaleId</i> function to execute against locale codes read from a source entity,
 * generating locale IDs to be written into the destination:</p>
 * <hr><blockquote><pre>
 * // invokes getLocaleId with the en_AU String as an argument.:
 * mapping.identity().associate("locale_code").to("locale_id").readingWith("getLocaleId");
 * </pre></blockquote><hr>
 *
 * <p>The <i>getLocaleId</i> function can be used as a source field in a mapping as well. When used within a field mapping, the function must be
 * enclosed within curly braces. The following example uses it to obtain the <i>en_AU</i> locale ID in a mapping using different constructs:</p>
 * <hr><blockquote><pre>
 * // invokes getLocaleId with the "en_AU" String as an argument
 * mapping.identity().associate("{getLocaleId(en_AU)}").to("locale_id");
 *
 * //or: invokes getLocaleId with the value of the variable currentLocale
 * mapping.identity().associate("{getLocaleId($currentLocale)}").to("locale_id");
 *
 * //or: invokes getLocaleId with the value produced by another function call
 * mapping.identity().associate("{getLocaleId(getCurrentLocale())}").to("locale_id");
 * </pre></blockquote><hr>
 *
 * <p>There is no limitation on the number of arguments used to invoke a function. If a function takes multiple arguments, use an array of {@code Object}s.</p>
 *
 * @see FunctionWrapper
 * @see EngineScope
 * @see DataIntegrationEngine
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 * @param <O> the output type of this function
 * @param <I> the input type expected for this function.
 */
public interface FunctionCall<O, I> {

	/**
	 * Executes the function and returns the result (if any)
	 * 
	 * @param input the function arguments
	 * @return the function result.
	 */
	O execute(I input);
}
