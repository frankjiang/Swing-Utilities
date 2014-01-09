/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * Recreatable.java is built in 2013-2-27.
 */
package com.frank.swing;

/**
 * Interface for a recreatable component. Using this interface, the implemented
 * object can be serialized one or several parts of it. Which may maintain all
 * the information of the current object.
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public interface Recreatable
{
	/**
	 * Recreate the component according to the extracted serialized object.
	 * 
	 * @param obj
	 *            the extracted serialized object
	 */
	public void recreate(Object obj);

	/**
	 * Returns the serialized object by which the object can be recreated.
	 * 
	 * @return the serialized object
	 */
	public Object extract();
}
