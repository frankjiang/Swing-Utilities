/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. PerformanceManager.java is PROPRIETARY/CONFIDENTIAL built in 2013.
 * Use is subject to license terms.
 */
package com.frank.swing;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Observable;

/**
 * Performance manager.
 * <p>
 * A performance manager can record the performance steps and react the
 * performance with undos and redos.
 * </p>
 * <p>
 * In this class, its observable functions are not implemented.
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @param <T>
 * @version 1.0.0
 */
public class PerformanceManager<T> extends Observable
{
	/**
	 * The step lists.
	 */
	protected LinkedList<T>	list;
	/**
	 * The current step counter.
	 */
	protected int			step;
	/**
	 * The maximum steps to restore.
	 */
	protected int			maxStorage;

	/**
	 * Construct an instance of <tt>PerformanceManager</tt> with default maximum
	 * steps 100.
	 */
	public PerformanceManager()
	{
		list = new LinkedList();
		step = -1;
		maxStorage = 100;
	}

	/**
	 * Construct an instance of <tt>PerformanceManager</tt> with specified
	 * maximum steps.
	 * 
	 * @param maxStorage
	 *            the maximum steps to restore
	 */
	public PerformanceManager(int maxStorage)
	{
		list = new LinkedList();
		step = -1;
		this.maxStorage = maxStorage;
	}

	/**
	 * Reset the steps and clear the memory.
	 */
	public void reset()
	{
		step = -1;
		list.clear();
		setChanged();
		notifyObservers();
	}

	/**
	 * Undo the last step and returns the previous record.
	 */
	public void undo()
	{
		if (step > 0)
			step--;
		setChanged();
		notifyObservers();
	}

	/**
	 * Re-do the next step and returns the next record.
	 */
	public void redo()
	{
		if (step >= 0 && step < list.size())
			step++;
		setChanged();
		notifyObservers();
	}

	/**
	 * Perform one step and record.
	 * 
	 * @param record
	 *            the record instance
	 */
	public void perform(T record)
	{
		// Delete the overflowed part
		int toDelete = list.size() - maxStorage;
		ListIterator<T> it = list.listIterator();
		for (int i = 0; i < toDelete; i++)
		{
			it.next();
			it.remove();
		}
		// Delete the useless part
		step++;
		if (step < list.size())
		{
			it = list.listIterator(step);
			do
			{
				it.next();
				it.remove();
			}
			while (it.hasNext());
		}
		list.add(record);
		setChanged();
		notifyObservers();
	}

	/**
	 * Returns the reference of current record.
	 * 
	 * @return the current record, or <code>null</code> if not have one
	 */
	public T current()
	{
		if (step < 0 || step >= list.size())
			return null;
		return list.get(step);
	}

	/**
	 * Returns <tt>true</tt> if this collection contains no records.
	 * 
	 * @return <tt>true</tt> if this collection contains no records
	 */
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	/**
	 * Returns {@code true} if this record list iterator has more elements when
	 * traversing the list in the forward direction.
	 * 
	 * @return {@code true} if the list iterator has more elements when
	 *         traversing the list in the forward direction
	 */
	public boolean hasNext()
	{
		return (step >= 0) && (step < (list.size() - 1));
	}

	/**
	 * Returns {@code true} if this record list iterator has more elements when
	 * traversing the list in the reverse direction.
	 * 
	 * @return {@code true} if the list iterator has more elements when
	 *         traversing the list in the reverse direction
	 */
	public boolean hasPrevious()
	{
		return step > 0;
	}
}
