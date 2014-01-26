/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. CompManager.java is built in 2013-4-27.
 */
package com.frank.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JComponent;

/**
 * The internal frame (view) menu manager.
 * <p>
 * The <tt>CompManager</tt> is a internal frame to buttons binder. In this
 * binder, the internal frames are binded to buttons according to their indices.
 * The action of button perform is defined by {@link ViewActoin}.
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @param <C>
 *            the type of component
 * @param <B>
 *            the type of button
 * @version 1.0.0
 */
public class CompManager<C extends Component, B extends AbstractButton>
{
	/**
	 * The defined component action.
	 */
	protected ComponentAction<C, B>		action;
	/**
	 * The internal frames.
	 */
	protected Vector<C>					comps;
	/**
	 * The manage buttons.
	 */
	protected Vector<B>					buttons;
	/**
	 * The map for the parent components of components.
	 */
	protected HashMap<C, JComponent>	compParents;
	/**
	 * The map for the parent components of buttons.
	 */
	protected HashMap<B, JComponent>	buttonParents;

	/**
	 * Construct an instance of <tt>CompManager</tt>.
	 */
	protected CompManager()
	{
		// empty
	}

	/**
	 * Construct an instance of <tt>CompManager</tt>. The view will allocate two
	 * data arrays with <tt>10</tt> elements for frames and buttons.
	 * 
	 * @param action
	 *            the <tt>ViewAction</tt> which defines the actions between
	 *            frames and buttons
	 * @param size
	 *            the initial size of buttons and frames
	 */
	public CompManager(ComponentAction<C, B> action)
	{
		this(action, 10);
	}

	/**
	 * Construct an instance of <tt>CompManager</tt>. The view will allocate two
	 * data arrays with <tt>size</tt> elements for frames and buttons.
	 * 
	 * @param action
	 *            the <tt>ViewAction</tt> which defines the actions between
	 *            frames and buttons
	 * @param size
	 *            the initial size of buttons and frames
	 */
	public CompManager(ComponentAction<C, B> action, int size)
	{
		if (action == null)
			throw new NullPointerException(Messages.getString("CompManager.0")); //$NON-NLS-1$
		if (size < 0)
			throw new IllegalArgumentException(
					Messages.getString("CompManager.1")); //$NON-NLS-1$
		this.action = action;
		comps = new Vector(size);
		buttons = new Vector(size);
		compParents = new HashMap(size);
		buttonParents = new HashMap(size);
	}

	/**
	 * Put a couple of component and button to this manager.
	 * 
	 * @param comp
	 *            the component to put into
	 * @param button
	 *            the button to put into
	 */
	public void put(C comp, B button)
	{
		comps.add(comp);
		buttons.add(button);
		action.perform(comp, button);
		action.recall(comp, button);
	}

	/**
	 * Add the specified component and button into this manager and add them to
	 * specified parent component.
	 * 
	 * @param comp
	 *            the specified component
	 * @param cp
	 *            the parent component for the specified component
	 * @param button
	 *            the specified button
	 * @param bp
	 *            the parent component for the specified button
	 */
	public void bind(C comp, JComponent cp, B button, JComponent bp)
	{
		put(comp, button);
		bp.add(button);
		buttonParents.put(button, bp);
		cp.add(comp);
		compParents.put(comp, cp);
	}

	/**
	 * Put all the entries of component and button in the specified map to this
	 * manager.
	 * 
	 * @param map
	 *            the specified map
	 */
	public void putAll(Map<C, B> map)
	{
		for (Entry<C, B> e : map.entrySet())
		{
			C comp = e.getKey();
			B button = e.getValue();
			comps.add(comp);
			buttons.add(button);
			action.perform(comp, button);
			action.recall(comp, button);
		}
	}

	/**
	 * Remove the component and button in the manager according to the specified
	 * component.
	 * 
	 * @param comp
	 *            the specified component
	 */
	public void remove(C comp)
	{
		int index = comps.indexOf(comp);
		if (index != -1)
		{
			comps.remove(index);
			JComponent cp = compParents.remove(comp);
			if (cp != null)
				cp.remove(comp);
			B button = buttons.remove(index);
			JComponent bp = buttonParents.remove(button);
			if (bp != null)
				bp.remove(button);
		}
	}

	/**
	 * Clear all the components and buttons in this manager.
	 */
	public void clear()
	{
		buttons.clear();
		for (Entry<B, JComponent> e : buttonParents.entrySet())
			e.getValue().remove(e.getKey());
		buttonParents.clear();
		comps.clear();
		for (Entry<C, JComponent> e : compParents.entrySet())
			e.getValue().remove(e.getKey());
		compParents.clear();
	}

	/**
	 * Remove the component and button in the manager according to the specified
	 * index.
	 * 
	 * @param index
	 *            the specified index
	 */
	public void remove(int index)
	{
		C comp = comps.remove(index);
		B button = buttons.remove(index);
		JComponent bp = buttonParents.remove(button);
		if (bp != null)
			bp.remove(button);
		JComponent cp = compParents.remove(comp);
		if (cp != null)
			cp.remove(comp);
	}

	/**
	 * Remove the component and button in the manager according to the specified
	 * component.
	 * 
	 * @param comp
	 *            the specified component
	 */
	public void remove(B button)
	{
		int index = buttons.indexOf(button);
		if (index != -1)
		{
			buttons.remove(index);
			JComponent bp = buttonParents.remove(button);
			if (bp != null)
				bp.remove(button);
			C comp = comps.remove(index);
			JComponent cp = compParents.remove(comp);
			if (cp != null)
				cp.remove(comp);
		}
	}

	/**
	 * Add all the buttons in the manager to the specified parent component.
	 * 
	 * @param parent
	 *            the specified parent component
	 */
	public void addButtonsTo(JComponent parent)
	{
		for (B b : buttons)
			parent.add(b);
	}

	/**
	 * Add all the components in the manager to the specified parent component.
	 * 
	 * @param parent
	 *            the specified parent component
	 */
	public void addCompsTo(JComponent parent)
	{
		for (C b : comps)
			parent.add(b);
	}

	/**
	 * Set the size of all button according to specified width and height.
	 * 
	 * @param width
	 *            the specified width
	 * @param height
	 *            the specified height
	 */
	public void setButtonSize(int width, int height)
	{
		setButtonSize(new Dimension(width, height));
	}

	/**
	 * Set the size of all button according to specified size.
	 * 
	 * @param size
	 *            the specified size
	 */
	public void setButtonSize(Dimension size)
	{
		for (B b : buttons)
			b.setSize(size);
	}

	/**
	 * Set the size of all component according to specified width and height.
	 * 
	 * @param width
	 *            the specified width
	 * @param height
	 *            the specified height
	 */
	public void setCompSize(int width, int height)
	{
		setCompSize(new Dimension(width, height));
	}

	/**
	 * Set the size of all component according to specified size.
	 * 
	 * @param size
	 *            the specified size
	 */
	public void setCompSize(Dimension size)
	{
		for (C c : comps)
			c.setSize(size);
	}

	/**
	 * Returns the index of the first occurrence of the specified button in
	 * buttons vector, or -1 if this vector does not contain the button. More
	 * formally, returns the lowest index {@code i} such that
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
	 * or -1 if there is no such index.
	 * 
	 * @param button
	 *            button to search for
	 * @return the index of the first occurrence of the specified button in this
	 *         vector, or -1 if this vector does not contain the button
	 * @return
	 */
	public int indexOf(B button)
	{
		return buttons.indexOf(button);
	}

	/**
	 * Returns the index of the first occurrence of the specified component in
	 * components vector, or -1 if this vector does not contain the component.
	 * More formally, returns the lowest index {@code i} such that
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
	 * or -1 if there is no such index.
	 * 
	 * @param comp
	 *            component to search for
	 * @return the index of the first occurrence of the specified component in
	 *         this vector, or -1 if this vector does not contain the component
	 * @return
	 */
	public int indexOf(C comp)
	{
		return comps.indexOf(comp);
	}

	/**
	 * Returns the component according to its index.
	 * 
	 * @param index
	 *            component index
	 * @return the component
	 */
	public C getComp(int index)
	{
		return comps.elementAt(index);
	}

	/**
	 * Returns the component according to its binded button.
	 * 
	 * @param button
	 *            the binded button
	 * @return the component, or null if not contains this button
	 */
	public C getComp(B button)
	{
		int index = indexOf(button);
		if (index == -1)
			return null;
		return comps.elementAt(index);
	}

	/**
	 * Returns the button according to its index.
	 * 
	 * @param button
	 *            button index
	 * @return the button
	 */
	public B getButton(int index)
	{
		return buttons.elementAt(index);
	}

	/**
	 * Returns the button according to its binded component.
	 * 
	 * @param comp
	 *            the binded component
	 * @return the component
	 */
	public B getButton(C comp)
	{
		int index = indexOf(comp);
		if (index == -1)
			return null;
		return buttons.elementAt(index);
	}

	/**
	 * Get all the components.
	 * 
	 * @return the component vector
	 */
	public Vector<B> getAllButtons()
	{
		return buttons;
	}

	/**
	 * Get all the components.
	 * 
	 * @return the component vector
	 */
	public Vector<C> getAllComps()
	{
		return comps;
	}

	/**
	 * Returns the internal component action.
	 * 
	 * @return the internal component action
	 */
	public ComponentAction<C, B> getAction()
	{
		return action;
	}
}
