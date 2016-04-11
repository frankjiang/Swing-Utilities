/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * LogSlider.java is PROPRIETARY/CONFIDENTIAL built in 3:46:38 PM, Mar 5, 2016.
 * Use is subject to license terms.
 */

package com.frank.swing;

import javax.swing.JSlider;

/**
 * The slider for a logarithm value mappings.
 * <p>
 * The original function: <code>y = log<sub>a</sub>(x + b) + c</code><br>
 * The inverse function: <code>x = ln(x + b) / ln(a) + c</code>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class LogSlider extends JSlider
{

	/**
	 * The serialVersionUID.
	 */
	private static final long	serialVersionUID	= -1672590690480011400L;

	/**
	 * The real maximum value of the slider.
	 */
	protected double			realMax;
	/**
	 * The real minimum value of the slider.
	 */
	protected double			realMin;
	/**
	 * The logarithm parameters.
	 */
	protected double			a, b, c;

	protected double			accuracy			= 100.0;

	/**
	 * Construct an instance of <tt>LogSlider</tt>.
	 */
	public LogSlider()
	{
		setLogarithmParameters(2, 0, 0);
	}

	/**
	 * Set the logarithm parameters of the original function.
	 * <p>
	 * <code>y = log<sub>a</sub>(x + b) + c</code>
	 * </p>
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	public void setLogarithmParameters(double a, double b, double c)
	{
		this.a = a;
		this.b = b;
		this.c = c;
	}

	protected double forward(double x)
	{
		return Math.log(x + b) / Math.log(a) + c;
	}

	protected int forward_int(double x)
	{
		return (int) Math.round(forward((double) x));
	}

	protected double backward(double y)
	{
		return Math.exp(Math.log(a) * (y - c)) - b;
	}

	protected int backward_int(double x)
	{
		return (int) Math.round(backward(x));
	}

//	/**
//	 * @see javax.swing.JSlider#setMaximum(int)
//	 */
//	public void setMaximum(int maximum)
//	{
//		realMax = forward(maximum);
//		super.setMaximum((int) Math.ceil(realMax * accuracy));
//	}
//
//	/**
//	 * @see javax.swing.BoundedRangeModel#getMaximum()
//	 */
//	@Override
//	public int getMaximum()
//	{
//		return backward_int(realMax / accuracy);
//	}
//
//	/**
//	 * @see javax.swing.BoundedRangeModel#setMinimum(int)
//	 */
//	@Override
//	public void setMinimum(int minimum)
//	{
//		realMin = forward(minimum);
//		super.setMinimum((int) Math.floor(realMin * accuracy));
//	}
//
//	/**
//	 * @see javax.swing.BoundedRangeModel#getMinimum()
//	 */
//	@Override
//	public int getMinimum()
//	{
//		return backward_int(realMin);
//	}
//
//	protected double realValue;
//
//	/**
//	 * @see javax.swing.BoundedRangeModel#getValue()
//	 */
//	@Override
//	public int getValue()
//	{
//		return backward_int(realValue);
//	}
//
//	/**
//	 * @see javax.swing.BoundedRangeModel#setValue(int)
//	 */
//	@Override
//	public void setValue(int value)
//	{
//		realValue = forward(value);
//		super.setValue((int) Math.round(realValue * accuracy));
//	}

	
}
