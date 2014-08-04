/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.config.annotation;

import java.lang.annotation.*;

import com.univocity.api.engine.*;

/**
 * A function wrapper is used to annotate methods of classes whose instances are used by a {@link DataIntegrationEngine} to wrap methods in a {@link FunctionCall}.
 *
 * <p><i><b>Important:</b> Methods with this annotation must declare a return value. Arrays of objects are supported</i>.
 *
 * @see FunctionCall
 * @see EngineScope
 * @see DataIntegrationEngine
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(value = { ElementType.METHOD })
public @interface FunctionWrapper {

	/**
	 * The name of the function call used in expressions and mappings of a {@link DataIntegrationEngine}. If no explicit name is provided, then the method name will be used.
	 * @return the name of the function to be used in place of the method name
	 */
	String name() default "";

	/**
	 * The {@link EngineScope} of the {@link FunctionCall} created with this method. It determines how the result produced by this call is retained before the
	 * function has to be called again. A new function call with the same parameters (if any) will be executed only once in each active scope.
	 *  <p><i><b>Note:</b> functions are accessible from any scope</i>.
	 * @return the scope associated with values returned by this function.
	 */
	EngineScope scope() default EngineScope.STATELESS;

}
