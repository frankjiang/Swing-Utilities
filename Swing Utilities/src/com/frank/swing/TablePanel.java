/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. TablePanel.java is built in 2013-4-18.
 */
package com.frank.swing;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * The titled table panel component.
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class TablePanel extends JPanel implements TableModel
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= 2080700964624904231L;
	/**
	 * The table.
	 */
	protected JTable			table;
	/**
	 * The scroll pane for the table.
	 */
	protected JScrollPane		scrollPane;
	/**
	 * The title for the table.
	 */
	protected JButton			title;
	/**
	 * The titles for the columns.
	 */
	protected String[]			titles;
	/**
	 * The column amount for the table.
	 */
	protected int				columns;
	/**
	 * The row amount for the table.
	 */
	protected int				rows;
	/**
	 * The types for each column.
	 */
	protected Class[]			types;
	/**
	 * The table data.
	 */
	protected Object[][]		values;
	/**
	 * List of table listeners.
	 */
	protected EventListenerList	tableListenerList	= new EventListenerList();
	/**
	 * The resize adapter for title.
	 */
	protected ResizeAdapter		titleAdapter;
	/**
	 * The resize adapter for table using in <tt>ScrollPane</tt>.
	 */
	protected ResizeAdapter		tableAdapter;
	/**
	 * The flag whether table is titled.
	 */
	protected boolean			isTableTitled;
	/**
	 * The flag whether columns are titled.
	 */
	protected boolean			isColumnTitle;
	/**
	 * The flags whether the cells are editable.
	 */
	protected boolean[][]		isCellEditable;

	/**
	 * Construct an instance of TablePanel with specified table title and column
	 * titles.
	 * 
	 * @param title
	 * @param titles
	 * @param rows
	 * @param columns
	 * @wbp.parser.constructor
	 */
	public TablePanel(String title, String[] titles, int rows, int columns)
	{
		initialize(rows, columns);
		isTableTitled = true;
		isColumnTitle = true;
		setAutoLayout();
		this.title.setText(title);
		setColumnTitles(titles);
		update();
	}

	/**
	 * Construct a titless TablePanel.
	 * 
	 * @param rows
	 * @param columns
	 */
	public TablePanel(int rows, int columns)
	{
		initialize(rows, columns);
		isTableTitled = false;
		isColumnTitle = false;
		setAutoLayout();
	}

	/**
	 * Initialize a table panel.
	 * 
	 * @param rows
	 * @param columns
	 */
	protected void initialize(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		setLayout(null);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 23, 450, 273);
		add(scrollPane);
		table = new JTable();
		table.setColumnSelectionAllowed(true);
		scrollPane.setViewportView(table);
		title = new JButton(Messages.getString("TablePanel.0")); //$NON-NLS-1$
		title.setFocusPainted(false);
		title.setName(Messages.getString("TablePanel.3"));//$NON-NLS-1$
		title.setFocusable(false);
		title.setBorder(UIManager.getBorder("List.noFocusBorder"));//$NON-NLS-1$
		title.setBounds(0, 0, 450, 23);
		add(title);
		titles = new String[columns];
		values = new Object[rows][columns];
	}

	/**
	 * Set the automatically changing layout for the table panel.
	 */
	protected void setAutoLayout()
	{
		int margin = 10;
		if (isTableTitled)
		{
			if (titleAdapter == null)
			{
				if (getGraphics() != null)
					title.setBounds(
							0,
							0,
							this.getWidth(),
							SwingUtils.stringDimension(getGraphics(),
									title.getFont(), title.getText()).height
									+ margin);
				else
					title.setBounds(0, 0, this.getWidth(), 30);
				titleAdapter = new ResizeAdapter(title, 0, -1, 0, 0,
						ResizeAdapter.WIDTH_TOP);
				addComponentListener(titleAdapter);
			}
			add(title);
			tableAdapter = new ResizeAdapter(scrollPane, title.getHeight(), 0,
					0, 0);
			addComponentListener(tableAdapter);
		}
		else
		{
			remove(title);
			tableAdapter = new ResizeAdapter(scrollPane, 0, 0, 0, 0);
			addComponentListener(tableAdapter);
		}
	}

	/**
	 * Update the layout and data content.
	 */
	protected void update()
	{
		table.setModel(this);
	}

	/**
	 * Set the column title according to the column index.
	 * 
	 * @param column
	 *            the specified column index
	 * @param title
	 *            the specified column title
	 */
	public void setColumnTitle(int column, String title)
	{
		titles[column] = title;
	}

	/**
	 * Set the column titles.
	 * 
	 * @param titles
	 *            the column titles
	 */
	public void setColumnTitles(String[] titles)
	{
		if (titles == null)
			return;
		if (this.titles == null)
		{
			this.titles = new String[rows];
			for (int i = 0; i < this.titles.length; i++)
				this.titles[i] = Messages.getString("TablePanel.1"); //$NON-NLS-1$
		}
		for (int i = 0; i < titles.length && i < this.titles.length; i++)
			this.titles[i] = titles[i];
	}

	/**
	 * Returns the index where the types do not match.
	 * 
	 * @param instances
	 *            the instances
	 * @param classes
	 *            the classes
	 * @return the index where not match or -1 if all matched
	 */
	protected int validate(Object[] instances, Class[] classes)
	{
		for (int i = 0; i < instances.length && i < classes.length; i++)
			if (!classes[i].isInstance(instances[i]))
				return i;
		return -1;
	}

	/**
	 * Set the values in one row.
	 * 
	 * @param row
	 *            the specified row index
	 * @param values
	 *            the row values
	 */
	public void setValues(int row, Object[] values)
	{
		if (types != null)
		{
			int r = validate(values, types);
			if (r != -1)
				throw new IllegalArgumentException(String.format(
						Messages.getString("TablePanel.2"), //$NON-NLS-1$
						values[r].toString(), row, r, types[r].toString()));
		}
		this.values[row] = values;
	}

	/**
	 * Set the types of each column.
	 * 
	 * @param types
	 */
	public void setTypes(Class... types)
	{
		if (types == null || types.length == 0)
			return;
		if (this.types == null)
		{
			this.types = new Class[columns];
			for (int i = 0; i < types.length; i++)
				this.types[i] = Object.class;
		}
		for (int i = 0; i < types.length && i < this.types.length; i++)
			this.types[i] = types[i];
	}

	/**
	 * Set the values in the table.
	 * 
	 * @param values
	 *            the table values
	 */
	public void setValues(Object[][] values)
	{
		if (types != null)
		{
			int r;
			for (int i = 0; i < values.length; i++)
				if ((r = validate(values[i], types)) != -1)
					throw new IllegalArgumentException(String.format(
							Messages.getString("TablePanel.2"), //$NON-NLS-1$
							values[i][r].toString(), i, r, types[r].toString()));
		}
		this.values = values;
		update();
	}

	/**
	 * Returns the inner table in the panel.
	 * 
	 * @return the inner table
	 */
	public JTable getTable()
	{
		return table;
	}

	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount()
	{
		return rows;
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount()
	{
		return columns;
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column)
	{
		return titles == null ? null : titles[column];
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int column)
	{
		if (types == null)
			return Object.class;
		return types[column] == null ? Object.class : types[column];
	}

	/**
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int row, int column)
	{
		return isCellEditable == null ? false
				: (isCellEditable[row] == null ? false
						: isCellEditable[row][column]);
	}

	/**
	 * Set the specified cell editable or not.
	 * 
	 * @param row
	 *            cell row index
	 * @param column
	 *            cell column index
	 * @param editable
	 *            <code>true</code> if editable
	 */
	public void setCellEditable(int row, int column, boolean editable)
	{
		if (isCellEditable == null && isCellEditable[row] == null)
			isCellEditable[row][column] = editable;
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int column)
	{
		return values[row][column];
	}

	/**
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(Object aValue, int row, int column)
	{
		if (types != null && !types[column].isInstance(aValue))
			throw new IllegalArgumentException(String.format(
					Messages.getString("TablePanel.2"), //$NON-NLS-1$
					aValue.toString(), row, column, types[column].toString()));
		values[row][column] = aValue;
	}

	/**
	 * @see javax.swing.table.TableModel#addTableModelListener(javax.swing.event.TableModelListener)
	 */
	public void addTableModelListener(TableModelListener l)
	{
		tableListenerList.add(TableModelListener.class, l);
	}

	/**
	 * @see javax.swing.table.TableModel#removeTableModelListener(javax.swing.event.TableModelListener)
	 */
	public void removeTableModelListener(TableModelListener l)
	{
		tableListenerList.remove(TableModelListener.class, l);
	}

	//public void 
	/**
	 * Set the specified pop-up menu to the table title.
	 * 
	 * @param popup
	 *            pop-up menu
	 */
	public void setTitlePopupMenu(JPopupMenu popup)
	{
		SwingUtils.addPopup(title, popup);
	}

	/**
	 * Set the specified pop-up menu to the table body.
	 * 
	 * @param popup
	 *            pop-up menu
	 */
	public void setTablePopupMenu(JPopupMenu popup)
	{
		SwingUtils.addPopup(table, popup);
	}
}
