/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * ResizeAdapter.java is built in 2013-1-20.
 */
package com.frank.swing;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * The adapter of a component listener which makes the dimension of specified
 * component automatically adjusting to the dimension of the under listening
 * component (the parent component).
 * <p>
 * Several types of listening policies are define in the adapter. With different
 * policy the component can change its dimension differently.
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class ResizeAdapter extends ComponentAdapter
{
	/**
	 * Adjusting both width and height, according to the specified margin.
	 */
	public static final int	BOTH						= 0;
	/**
	 * Adjusting width only and the component will stick to the top of the
	 * parent component.
	 */
	public static final int	WIDTH_TOP					= 1;
	/**
	 * Adjusting width only and the component will stick to the bottom of the
	 * parent component.
	 */
	public static final int	WIDTH_BOTTOM				= 2;
	/**
	 * Adjusting height only and the component will stick to the left of the
	 * parent component.
	 */
	public static final int	HEIGHT_LEFT					= -1;
	/**
	 * Adjusting height only and the component will stick to the right of the
	 * parent component.
	 */
	public static final int	HEIGHT_RIGHT				= -2;
	/**
	 * No adjusting for the width or height, the component will stick to the
	 * border on the top.
	 */
	public static final int	BORDER_TOP					= 100;
	/**
	 * No adjusting for the width or height, the component will stick to the
	 * border on the top-left.
	 */
	public static final int	BORDER_TOP_LEFT				= 101;
	/**
	 * No adjusting for the width or height, the component will stick to the
	 * border on the top-right.
	 */
	public static final int	BORDER_TOP_RIGHT			= 102;
	/**
	 * No adjusting for the width or height, the component will stick to the
	 * border on the bottom.
	 */
	public static final int	BORDER_BOTTOM				= 200;
	/**
	 * No adjusting for the width or height, the component will stick to the
	 * border on the bottom-left.
	 */
	public static final int	BORDER_BOTTOM_LEFT			= 201;
	/**
	 * No adjusting for the width or height, the component will stick to the
	 * border on the bottom-right.
	 */
	public static final int	BORDER_BOTTOM_RIGHT			= 202;
	/**
	 * No adjusting for the width or height, the component will stick to the
	 * border on the left.
	 */
	public static final int	BORDER_LEFT					= 300;
	/**
	 * No adjusting for the width or height, the component will stick to the
	 * border on the right.
	 */
	public static final int	BORDER_RIGHT				= 400;
	/**
	 * No adjusting for the width or height, the component will change its
	 * X-axis and Y-axis, remove to the center of the parent component. The
	 * X-axis and Y-axis will be set as the half component size.
	 */
	public static final int	CENTER						= 500;
	/**
	 * No adjusting for the width or height, the component will change its
	 * X-axis and Y-axis, remove to the center of the parent component. The
	 * X-axis will be set as the half as the subtraction of parent width to
	 * component width; the Y-axis will be set according to top margin.
	 */
	public static final int	CENTER_HORIZONTAL_TOP		= 510;
	/**
	 * No adjusting for the width or height, the component will change its
	 * X-axis and Y-axis, remove to the center of the parent component. The
	 * X-axis will be set as the half as the subtraction of parent width to
	 * component width; the Y-axis will be set according to bottom margin.
	 */
	public static final int	CENTER_HORIZONTAL_BOTTOM	= 511;
	/**
	 * No adjusting for the width or height, the component will change its
	 * X-axis and Y-axis, remove to the center of the parent component. The
	 * X-axis will be set according to left margin; the Y-axis will be set as
	 * the half as the subtraction of parent height to component height.
	 */
	public static final int	CENTER_VERTICAL_LEFT		= 520;
	/**
	 * No adjusting for the width or height, the component will change its
	 * X-axis and Y-axis, remove to the center of the parent component. The
	 * X-axis will be set according to right margin; the Y-axis will be set as
	 * the half as the subtraction of parent height to component height.
	 */
	public static final int	CENTER_VERTICAL_RIGHT		= 521;
	/**
	 * The margin to the left border.
	 */
	private int				left						= 10;
	/**
	 * The margin to the right border.
	 */
	private int				right						= 10;
	/**
	 * The margin to the top border.
	 */
	private int				top							= 10;
	/**
	 * The margin to the bottom border.
	 */
	private int				bottom						= 10;
	/**
	 * The adjusting policy, use {@linkplain #BOTH} as default.
	 */
	private int				policy						= BOTH;
	/**
	 * The component to adjusting.
	 */
	private Component		comp;

	/**
	 * Construct an default instance of ResizeAdapter. This adapter will
	 * adjusting the specified component in both width and height with margin 10
	 * to every border.
	 * 
	 * @param comp
	 *            The component to adjusting.
	 */
	public ResizeAdapter(Component comp)
	{
		this.comp = comp;
	}

	/**
	 * Construct an instance of ResizeAdapter.
	 * 
	 * @param comp
	 *            The component to adjusting.
	 * @param top
	 *            The margin to top.
	 * @param bottom
	 *            The margin to bottom.
	 * @param left
	 *            The margin to left.
	 * @param right
	 *            The margin to right.
	 */
	public ResizeAdapter(Component comp, int top, int bottom, int left,
			int right)
	{
		this.comp = comp;
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
	}

	/**
	 * Construct an instance of ResizeAdapter with specified policy and default
	 * margin(10).
	 * 
	 * @param comp
	 *            The component to adjusting.
	 * @param policy
	 *            The adjusting policy.
	 */
	public ResizeAdapter(Component comp, int policy)
	{
		this.comp = comp;
		this.policy = policy;
	}

	/**
	 * Construct an instance of customized ResizeAdapter.
	 * 
	 * @param comp
	 *            The component to adjusting.
	 * @param top
	 *            The margin to top.
	 * @param bottom
	 *            The margin to bottom.
	 * @param left
	 *            The margin to left.
	 * @param right
	 *            The margin to right.
	 * @param policy
	 *            The adjusting policy.
	 */
	public ResizeAdapter(Component comp, int top, int bottom, int left,
			int right, int policy)
	{
		this.comp = comp;
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		this.policy = policy;
	}

	/**
	 * @see java.awt.event.ComponentAdapter#componentResized(java.awt.event.ComponentEvent)
	 */
	public void componentResized(ComponentEvent e)
	{
		Rectangle r = e.getComponent().getBounds();
		switch (policy)
		{
			default:
			case BOTH:
				comp.setBounds(left, top, r.width - left - right, r.height
						- top - bottom);
				break;
			case WIDTH_TOP:
			{
				int height = comp.getBounds().height;
				comp.setBounds(left, top, r.width - left - right, height);
			}
				break;
			case WIDTH_BOTTOM:
			{
				int height = comp.getBounds().height;
				comp.setBounds(left, r.height - height - bottom, r.width - left
						- right, height);
			}
				break;
			case HEIGHT_LEFT:
			{
				int width = comp.getBounds().width;
				comp.setBounds(left, top, width, r.height - top - bottom);
			}
				break;
			case HEIGHT_RIGHT:
			{
				int width = comp.getBounds().width;
				comp.setBounds(r.width - width - right, top, width, r.height
						- top - bottom);
			}
				break;
			case BORDER_TOP:
			{
				Rectangle rt = comp.getBounds();
				comp.setBounds(rt.x, top, rt.width, rt.height);
			}
				break;
			case BORDER_TOP_LEFT:
			{
				Rectangle rt = comp.getBounds();
				comp.setBounds(left, top, rt.width, rt.height);
			}
				break;
			case BORDER_TOP_RIGHT:
			{
				Rectangle rt = comp.getBounds();
				comp.setBounds(r.width - right - rt.width, top, rt.width,
						rt.height);
			}
				break;
			case BORDER_BOTTOM:
			{
				Rectangle rt = comp.getBounds();
				comp.setBounds(rt.x, r.height - bottom - rt.height, rt.width,
						rt.height);
			}
				break;
			case BORDER_BOTTOM_LEFT:
			{
				Rectangle rt = comp.getBounds();
				comp.setBounds(left, r.height - bottom - rt.height, rt.width,
						rt.height);
			}
				break;
			case BORDER_BOTTOM_RIGHT:
			{
				Rectangle rt = comp.getBounds();
				comp.setBounds(r.width - right - rt.width, r.height - bottom
						- rt.height, rt.width, rt.height);
			}
				break;
			case BORDER_LEFT:
			{
				Rectangle rt = comp.getBounds();
				comp.setBounds(left, rt.y, rt.width, rt.height);
			}
				break;
			case BORDER_RIGHT:
			{
				Rectangle rt = comp.getBounds();
				comp.setBounds(r.width - right - rt.width, rt.y, rt.width,
						rt.height);
			}
				break;
			case CENTER:
			{
				Rectangle rt = comp.getBounds();
				comp.setBounds((r.width - rt.width) / 2,
						(r.height - rt.height) / 2, rt.width, rt.height);
			}
				break;
			case CENTER_HORIZONTAL_TOP:
			{
				Rectangle rt = comp.getBounds();
				comp.setBounds((r.width - rt.width) / 2, top, rt.width,
						rt.height);
			}
				break;
			case CENTER_VERTICAL_LEFT:
			{
				Rectangle rt = comp.getBounds();
				comp.setBounds(left, (r.height - rt.height) / 2, rt.width,
						rt.height);
			}
				break;
			case CENTER_HORIZONTAL_BOTTOM:
			{
				Rectangle rt = comp.getBounds();
				comp.setBounds((r.width - rt.width) / 2, r.height - rt.height
						- bottom, rt.width, rt.height);
			}
				break;
			case CENTER_VERTICAL_RIGHT:
			{
				Rectangle rt = comp.getBounds();
				comp.setBounds(r.x - rt.width - right,
						(r.height - rt.height) / 2, rt.width, rt.height);
			}
				break;
		}
		comp.validate();
	}
}
