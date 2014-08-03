/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.engine;

/**
 * The <code>EngineExecutionContext</code> is provided by a {@link DataIntegrationEngine} through {@link RowReader} and {@link EngineLifecycleContext} and allows
 * access to variables and functions in the current {@link EngineScope}.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface EngineExecutionContext {

	/**
	 * Executes an expression and returns the result. Acceptable expressions conform to the following format:
	 *
	 * <ul>
	 *  <li>Variables and constant names are prefixed with {@code $}</li>
	 *  <li>A function call is represented by its name and a sequence of parameters, if any, between {@code ()} and separated by commas.
	 *      Additional expressions such as function calls are accepted as parameters.
	 *  </li>
	 *  <li>A single expression can return multiple values. Each value must be separated by a comma.</li>
	 *  <li>The word "null", without quotes, will be evaluated as a {@code null} object; 'null' will be evaluated to the "null" {@code String}.
	 *  </li>
	 *  <li>Any value between single quotes is interpreted as a {@code String} literal. Single quotes are only required if the {@code String} literal contains:
	 *  	<ul>
	 *  		<li>leading or trailing white spaces,</li>
	 *  		<li>any of the following characters: <code> $ ) ' ( , </code> </li>
	 *  		<li>in case the single quote is part of the value, it must be escaped with an addition single quote:
	 *  			<p>the expression: <code>"' '' this is escaped ''  '"</code> will be parsed as the string <code>" ' this is escaped ' "</code>
	 *  		</li>
	 *  	</ul>
	 * 	</li>
	 * </ul>
	 *
	 * <p>Example:</p>
	 * <blockquote><pre>
	 * setVariable("b", 1);
	 * setVariable("e", 2);
	 * evaluateExpression("a, $b, concat( d, $e, 'f')"); //assume the concat function concatenates any number of arguments into a String.
	 * </pre></blockquote>
	 * Will return an object array with: <code>"a", 1, "d2f"</code>
	 *
	 * @param expression the expression to be executed
	 * @return the result of the expression.
	 */
	public Object evaluateExpression(String expression);

	/**
	 * Sets the value of a variable in the current scope.
	 * @param variableName the name of the variable to be set.
	 * @param value the value to assign to the variable.
	 */
	public void setVariable(String variableName, Object value);

	/**
	 * Reads the value of a variable available in the current scope.
	 * @param variableName the name of the variable whose value is going to be obtained.
	 * @return the value of the given variable.
	 */
	public Object readVariable(String variableName);

	/**
	 * Queries the current scope for the existence of a variable
	 * @param variableName the name of the variable to have its existence verified
	 * @return {@code true} if the current scope contains the given variable, otherwise {@code false}.
	 */
	public boolean containsVariable(String variableName);

	/**
	 * Invokes a function registered in the {@link DataIntegrationEngine} and returns the result
	 *
	 * @param functionName the name of the function to be invoked
	 * @param args the arguments to pass to the function
	 * @return the result of the function execution with the given arguments. Results can be cached depending on the scope with which they are associated.
	 * In this case the function will only be invoked if there is no previous result cached on its scope.
	 */
	public Object executeFunction(String functionName, Object... args);

	/**
	 * Returns the current active scope in the {@link DataIntegrationEngine}
	 * @return the current active scope
	 */
	public EngineScope getCurrentActiveScope();
}
