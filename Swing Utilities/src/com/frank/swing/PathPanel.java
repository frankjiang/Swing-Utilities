/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. PathPanel.java is built in 2013-4-23.
 */
package com.frank.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A path panel for file or directory path selecting.
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class PathPanel extends JPanel
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= -2835959111810805007L;
	/**
	 * The file path.
	 */
	private JTextField			textField;
	/**
	 * The title label.
	 */
	private JLabel				label;
	/**
	 * The select button.
	 */
	private JButton				button;
	/**
	 * The title of selection.
	 */
	private String				title;
	/**
	 * The flag for whether use {@link java.awt.FileDialog} in path selecting,
	 * {@code true} for using it; otherwise, using
	 * {@link javax.swing.JFileChooser} instead.
	 */
	private boolean				useFileDialog		= true;

	/**
	 * Create the path panel.
	 * 
	 * @param title
	 *            the title of path panel
	 * @param initialPath
	 *            the initial path
	 */
	public PathPanel(String title, String initialPath)
	{
		this.title = title;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 1, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		label = new JLabel(title);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(5, 10, 5, 10);
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		add(label, gbc_label);
		textField = new JTextField();
		if (initialPath != null)
			textField.setText(initialPath);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(5, 5, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		add(textField, gbc_textField);
		textField.setColumns(10);
		button = new JButton(Messages.getString("PathPanel.0"));//$NON-NLS-1$
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				File file = useFileDialog ? SwingUtils.selectLoadFile(null,
						PathPanel.this.title) : SwingUtils.selectLoadFile(
						PathPanel.this, PathPanel.this.title, null);
				if (file != null)
				{
					String path = file.getParentFile().getAbsolutePath();
					textField.setText(path);
					textField.setToolTipText(path);
				}
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(5, 10, 5, 10);
		gbc_button.anchor = GridBagConstraints.EAST;
		gbc_button.gridx = 2;
		gbc_button.gridy = 0;
		add(button, gbc_button);
		SwingUtils.addTextJPopMenus(textField);
		textField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				textField.setToolTipText(textField.getText());
			}
		});
	}

	/**
	 * Returns the current path defined in the panel.
	 * 
	 * @return the current path.
	 */
	public File getPath()
	{
		return new File(textField.getText());
	}

	/**
	 * Returns the exactly string on the {@linkplain #textField}.
	 * 
	 * @return the string
	 */
	public String getPathString()
	{
		return textField.getText();
	}

	/**
	 * Set the current path if not null.
	 * 
	 * @param path
	 *            the path string
	 */
	public void setPath(String path)
	{
		if (path != null)
		{
			textField.setText(path);
			textField.setToolTipText(path);
		}
	}

	/**
	 * Set whether use {@link java.awt.FileDialog} in path selecting,
	 * {@code true} for using it; otherwise, using
	 * {@link javax.swing.JFileChooser} instead.
	 * 
	 * @param b
	 *            the flag
	 */
	public void setUseFileDialog(boolean b)
	{
		useFileDialog = b;
	}
}
