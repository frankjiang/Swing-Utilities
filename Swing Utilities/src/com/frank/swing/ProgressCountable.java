/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * ProgressCountable.java is built in 2013-2-27.
 */
package com.frank.swing;

/**
 * A interface defines the progress of a task or procedure. With this interface
 * implemented, the progress can be counted by recalling the members defined.
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public interface ProgressCountable
{
	/**
	 * To notify the component that the progress is in proceeding.
	 * 
	 * @param total
	 *            the amount of the steps for the current task or procedure
	 */
	public void progressStart(int total);

	/**
	 * To notify the component that the progress has moved forward one step.
	 */
	public void progressUpdate();

	/**
	 * To notify the component that the progress has move forward several step.
	 * 
	 * @param steps
	 *            the amount of the steps which the progress has moved
	 */
	public void progressUpdate(int steps);

	/**
	 * To notify the component that the progress has reached its end.
	 */
	public void progressStop();
}
