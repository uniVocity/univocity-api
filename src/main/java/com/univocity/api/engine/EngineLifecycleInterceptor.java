/*******************************************************************************
 * Copyright (c) 2013 uniVocity Software Pty Ltd. All rights reserved.
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package com.univocity.api.engine;

/**
 * The <code>EngineLifecycleInterceptor</code> is used to obtain notifications about life cycle events of a {@link DataIntegrationEngine}.
 * Use {@link DataIntegrationEngine#addInterceptor(EngineLifecycleInterceptor)} to obtain notifications of such events.
 *
 * @see DataIntegrationEngine
 * @see EngineLifecycleContext
 * @see EngineScope
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 *
 */
public abstract class EngineLifecycleInterceptor {

	/**
	 * Notifies the {@link DataIntegrationEngine} is ready to execute a new data mapping cycle:
	 * {@link EngineScope#PERSISTENT} and {@link EngineScope#APPLICATION} scopes are active. All data stores have been
	 * initialized and the engine is ready to map data.
	 * @param context the contextual information and controls available to the user while the {@link DataIntegrationEngine} is active.
	 * <p><b>Note: </b> attempts to obtain inactive contextual information from the {@link EngineLifecycleContext} will produce an {@link IllegalStateException}
	 */
	public void engineReady(EngineLifecycleContext context) {
	}

	/**
	 * Notifies a new data mapping cycle has been started in the {@link DataIntegrationEngine}:
	 * {@link EngineScope#CYCLE} is active and a set of data mappings is ready to be executed.
	 * @param context the contextual information and controls available to the user while the {@link DataIntegrationEngine} is active.
	 * <p><b>Note: </b> attempts to obtain inactive contextual information from the {@link EngineLifecycleContext} will produce an {@link IllegalStateException}
	 */
	public void cycleStarted(EngineLifecycleContext context) {
	}

	/**
	 * Notifies a new data mapping has been started in the {@link DataIntegrationEngine}:
	 * {@link EngineScope#MAPPING} is active and a data mapping is ready to be executed.
	 * @param context the contextual information and controls available to the user while the {@link DataIntegrationEngine} is active.
	 */
	public void mappingStarted(EngineLifecycleContext context) {
	}

	/**
	 * Notifies a data mapping has been in the {@link DataIntegrationEngine}:
	 * {@link EngineScope#MAPPING} is still active and will be destroyed after the interceptors have been executed.
	 * This method will be called even in case of exceptions.
	 * @param context the contextual information and controls available to the user while the {@link DataIntegrationEngine} is active.
	 */
	public void mappingCompleted(EngineLifecycleContext context) {
	}

	/**
	 * Notifies a data mapping cycle has completed in the {@link DataIntegrationEngine}:
	 * {@link EngineScope#CYCLE} is still active and will be destroyed after the interceptors have been executed.
	 * This method will be called even in case of exceptions.
	 * @param context the contextual information and controls available to the user while the {@link DataIntegrationEngine} is active.
	 * <p><b>Note: </b> attempts to obtain inactive contextual information from the {@link EngineLifecycleContext} will produce an {@link IllegalStateException}
	 */
	public void cycleCompleted(EngineLifecycleContext context) {
	}

	/**
	 * Notifies the {@link DataIntegrationEngine} is being shut down.
	 * {@link EngineScope#APPLICATION} and {@link EngineScope#PERSISTENT} are still active and will be destroyed after the interceptors have been executed.
	 * This method will be called even in case of exceptions.
	 * @param context the contextual information and controls available to the user while the {@link DataIntegrationEngine} is active.
	 * <p><b>Note: </b> attempts to obtain inactive contextual information from the {@link EngineLifecycleContext} will produce an {@link IllegalStateException}
	 */
	public void engineShuttingDown(EngineLifecycleContext context) {
	}

	/**
	 * Notifies the {@link DataIntegrationEngine} has been completely shut down.
	 * No {@link EngineScope} is active. After the interceptors have been executed, the remaining shutdown hooks will be executed and any resources allocated by
	 * {@link DataIntegrationEngine} will be freed.
	 * This method will be called even in case of exceptions.
	 * @param context the contextual information still available to the user before the {@link DataIntegrationEngine} takes its last breath.
	 * <p><b>Note: </b> attempts to obtain inactive contextual information from the {@link EngineLifecycleContext} will produce an {@link IllegalStateException}
	 */
	public void engineStopped(EngineLifecycleContext context) {
	}
}
