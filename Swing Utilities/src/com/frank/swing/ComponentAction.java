/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * ViewAction.java is built in 2013-4-27.
 */
package com.frank.swing;

import java.awt.Component;

import javax.swing.AbstractButton;

/**
 * The component action interface.
 * <p>
 * The <tt>ComponentAction</tt> is a interface defining the button perform
 * action for specified component.
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @param <C>
 *            the type of component
 * @param <B>
 *            the type of button
 * @version 1.0.0
 */
public interface ComponentAction<C extends Component, B extends AbstractButton>
{
	/**
	 * Perform component action when button action is performed.
	 * 
	 * @param comp
	 *            the specified component
	 * @param button
	 *            the specified button
	 */
	public void perform(C comp, B button);

	/**
	 * Recall button action when component action is performed.
	 * 
	 * @param comp
	 *            the specified component
	 * @param button
	 *            the specified button
	 */
	public void recall(C comp, B button);
}
