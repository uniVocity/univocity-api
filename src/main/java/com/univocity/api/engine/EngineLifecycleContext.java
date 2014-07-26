/*******************************************************************************
 * Copyright (c) 2014 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.engine;

/**
 * The <code>EngineLifecycleContext</code> is provided by uniVocity through {@link EngineLifecycleInterceptor}, which is bound to a specific
 * {@link DataIntegrationEngine} by the user through {@link DataIntegrationEngine#addInterceptor(EngineLifecycleInterceptor)}.
 * 
 * <p>It provides information specific to the current activity being executed by uniVocity.
 * While the {@link DataIntegrationEngine} is active, it provides access to the {@link EngineExecutionContext} where functions and variables can be accessed.
 * When executing a data mapping cycle, the {@link MappingCycleContext} will be available to provide controls over the cycle.
 * Finally, when an entity mapping is executed, the {@link EntityMappingContext} will be available to provide controls and information over the individual mapping.  
 * 
 * <p><b>Note: </b> attempts to obtain inactive contextual information from this object will produce an {@link IllegalStateException}
 * 
 * @see EngineLifecycleInterceptor
 * @see EngineExecutionContext
 * @see MappingCycleContext
 * @see EntityMappingContext
 * 
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public interface EngineLifecycleContext {

	/**
	 * Returns the name associated to the {@link DataIntegrationEngine}
	 * @return the data integration engine name.
	 */
	public String getEngineName();

	/**
	 * Returns a contextual object that provides access to some elements of the {@link DataIntegrationEngine}, such as functions and variables. 
	 * @return an object that provides contextual information and controls over the {@link DataIntegrationEngine}.
	 * <p><b>Note: </b> An {@link IllegalStateException} if this method is called when the engine is stopped. 
	 */
	public EngineExecutionContext getExecutionContext();

	/**
	 * Returns a contextual object that provides controls over an active data mapping cycle in the {@link DataIntegrationEngine}. 
	 * @return an object that provides controls over an active data mapping cycle in the {@link DataIntegrationEngine}. 
	 * <p><b>Note: </b> An {@link IllegalStateException} if this method is called when data mapping cycle is not in execution. 
	 */
	public MappingCycleContext getCurrentMappingCycleContext();

	/**
	 * Returns a contextual object that provides controls over an active entity mapping in the {@link DataIntegrationEngine}. 
	 * @return an object that provides controls over an active  entity mapping in the {@link DataIntegrationEngine}. 
	 * <p><b>Note: </b> An {@link IllegalStateException} if this method is called when an entity mapping is not in execution. 
	 */
	public EntityMappingContext getCurrentEntityMapping();

	/**
	 * This method returns the number of the current cycle being executed (uniVocity counts each cycle incrementally, including the ones that failed) 
	 * If no cycle is active it this method will return the number of the last executed cycle. 
	 * @return the number of the most recent active cycle.
	 */
	public int getCurrentCycle();
}
