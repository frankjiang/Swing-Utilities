/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * ProgressControl.java is built in 2013-5-12.
 */
package com.frank.swing;

/**
 * This interface defines actions for a progress.
 * <p>
 * In this interface, the actions in the specified progress is under control by
 * the implementation of this interface.
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public interface ProgressControl
{
	/**
	 * Progress starts.
	 */
	public void start();

	/**
	 * Progress stops.
	 */
	public void terminate();

	/**
	 * Set the journey length of this progress.
	 * 
	 * @param length
	 *            the journey length
	 */
	public void setLength(int length);

	/**
	 * Update one step.
	 */
	public void update();

	/**
	 * Update the content of the current running task.
	 * 
	 * @param content
	 *            task content
	 */
	public void update(String content);

	/**
	 * Update specified steps.
	 * 
	 * @param step
	 *            the amount of steps to update
	 */
	public void update(double step);
}
