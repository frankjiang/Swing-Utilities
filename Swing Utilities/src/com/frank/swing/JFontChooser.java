/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights reserved.
 * JFontChooser.java is PROPRIETARY/CONFIDENTIAL built in 2013.
 * Use is subject to license terms.
 */
package com.frank.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;

/**
 * <tt>JFontChooser</tt> is a {@code JDialog} which providing {@code Font} type
 * choosing.
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class JFontChooser extends JDialog
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= -8373719988592494209L;
	/**
	 * User selected font.
	 */
	protected Font				font;
	private JPanel				contentPanel;
	private JLabel				lblSample;
	private JSplitPane			splitPane;
	private JComboBox			comboFontStyle;
	private JList				listFontSize;
	private JList				listFontName;

	/**
	 * Construct a modeless <tt>JFontChooser</tt>. <tt>JFontChooser</tt> can
	 * manual choose a useful font.
	 * 
	 * @param owner
	 *            the {@code Window} from which the dialog is displayed or
	 *            {@code null} if this dialog has no owner
	 * @wbp.parser.constructor
	 */
	public JFontChooser(Window owner)
	{
		this(owner, false);
	}

	/**
	 * Construct a <tt>JFontChooser</tt> dialog with specified modal type.
	 * <tt>JFontChooser</tt> can manual choose a useful font.
	 * 
	 * @param owner
	 *            the {@code Window} from which the dialog is displayed or
	 *            {@code null} if this dialog has no owner
	 * @param modal
	 *            modal specifies whether dialog blocks input to other windows
	 *            when shown; calling to <code>setModal(true)</code> is
	 *            equivalent to
	 *            <code>setModalityType(Dialog.DEFAULT_MODALITY_TYPE)</code>,
	 *            and calling to <code>setModal(false)</code> is equvivalent to
	 *            <code>setModalityType(Dialog.ModalityType.MODELESS)</code>
	 */
	public JFontChooser(Window owner, boolean modal)
	{
		super(owner, Messages.getString("JFontChooser.0")); //$NON-NLS-1$
		setModal(modal);
		JPanel buttonPanel = new JPanel();
		FlowLayout fl_buttonPanel = (FlowLayout) buttonPanel.getLayout();
		fl_buttonPanel.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		JButton btnOk = new JButton(Messages.getString("JFontChooser.1")); //$NON-NLS-1$
		btnOk.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		buttonPanel.add(btnOk);
		JButton btnCancel = new JButton(Messages.getString("JFontChooser.2")); //$NON-NLS-1$
		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				font = null;
				dispose();
			}
		});
		buttonPanel.add(btnCancel);
		contentPanel = new JPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		JPanel panelSample = new JPanel();
		panelSample.setBorder(new TitledBorder(null, Messages
				.getString("JFontChooser.3"), //$NON-NLS-1$
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panelSample, BorderLayout.SOUTH);
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(panelSample, popupMenu);
		JMenuItem mntmChangeSampleText = new JMenuItem(
				Messages.getString("JFontChooser.4")); //$NON-NLS-1$
		mntmChangeSampleText.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String s = SwingUtils.inputDialog(
						JFontChooser.this,
						Messages.getString("JFontChooser.5"), //$NON-NLS-1$
						Messages.getString("JFontChooser.6")); //$NON-NLS-1$
				if (s == null || s.equals(""))//$NON-NLS-1$
					return;
				lblSample.setText(s);
			}
		});
		popupMenu.add(mntmChangeSampleText);
		lblSample = new JLabel(Messages.getString("JFontChooser.7")); //$NON-NLS-1$
		panelSample.add(lblSample);
		splitPane = new JSplitPane();
		contentPanel.add(splitPane, BorderLayout.CENTER);
		JScrollPane scrollPaneFontNames = new JScrollPane();
		scrollPaneFontNames.setBorder(new TitledBorder(null, Messages
				.getString("JFontChooser.8"), //$NON-NLS-1$
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPane.setLeftComponent(scrollPaneFontNames);
		listFontName = new JList();
		scrollPaneFontNames.setViewportView(listFontName);
		JPanel panelFontStypleAndSize = new JPanel();
		splitPane.setRightComponent(panelFontStypleAndSize);
		panelFontStypleAndSize.setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		panelFontStypleAndSize.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		JLabel lblStyle = new JLabel(Messages.getString("JFontChooser.9")); //$NON-NLS-1$
		GridBagConstraints gbc_lblStyle = new GridBagConstraints();
		gbc_lblStyle.anchor = GridBagConstraints.EAST;
		gbc_lblStyle.insets = new Insets(5, 5, 5, 5);
		gbc_lblStyle.gridx = 0;
		gbc_lblStyle.gridy = 0;
		panel.add(lblStyle, gbc_lblStyle);
		comboFontStyle = new JComboBox();
		GridBagConstraints gbc_comboFontStyle = new GridBagConstraints();
		gbc_comboFontStyle.insets = new Insets(5, 0, 5, 5);
		gbc_comboFontStyle.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFontStyle.gridx = 1;
		gbc_comboFontStyle.gridy = 0;
		panel.add(comboFontStyle, gbc_comboFontStyle);
		JScrollPane scrollPaneFontSize = new JScrollPane();
		scrollPaneFontSize.setBorder(new TitledBorder(null, Messages
				.getString("JFontChooser.10"), //$NON-NLS-1$
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelFontStypleAndSize.add(scrollPaneFontSize, BorderLayout.CENTER);
		listFontSize = new JList();
		listFontSize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneFontSize.setViewportView(listFontSize);
		initializeFonts();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * Initialize the font settings.
	 */
	private void initializeFonts()
	{
		// initialize the default font
		font = lblSample.getFont();
		// list for font name
		listFontName.setModel(new AbstractListModel<String>()
		{
			/**
			 * serialVersionUID.
			 */
			private static final long	serialVersionUID	= -5818399397689713265L;
			String[]					names				= GraphicsEnvironment
																	.getLocalGraphicsEnvironment()
																	.getAvailableFontFamilyNames(
																			Locale.getDefault());

			@Override
			public int getSize()
			{
				return names.length;
			}

			@Override
			public String getElementAt(int index)
			{
				return names[index];
			}
		});
		listFontName.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				Font f = new Font((String) listFontName.getSelectedValue(),
						font.getStyle(), font.getSize());
				font = f;
				lblSample.setFont(font);
			}
		});
		listFontName.setSelectedIndex(0);
		// combo box for font style
		String[] styleNames = new String[] {
				Messages.getString("JFontChooser.11"), //$NON-NLS-1$
				Messages.getString("JFontChooser.12"), //$NON-NLS-1$
				Messages.getString("JFontChooser.13"), //$NON-NLS-1$
				Messages.getString("JFontChooser.14") }; //$NON-NLS-1$
		comboFontStyle.setModel(new DefaultComboBoxModel(styleNames));
		comboFontStyle.addActionListener(new ActionListener()
		{
			Integer[]	styles	= new Integer[] { Font.PLAIN, Font.BOLD,
										Font.ITALIC, Font.BOLD + Font.ITALIC };

			public void actionPerformed(ActionEvent e)
			{
				Font f = new Font(font.getName(), styles[comboFontStyle
						.getSelectedIndex()], font.getSize());
				font = f;
				lblSample.setFont(font);
			}
		});
		comboFontStyle.setSelectedIndex(0);
		// list for font size
		int min = 4, max = 96;
		final Integer[] sizes = new Integer[max - min + 1];
		for (int i = min; i <= max; i++)
			sizes[i - min] = i;
		listFontSize.setModel(new AbstractListModel<Integer>()
		{
			/**
			 * serialVersionUID.
			 */
			private static final long	serialVersionUID	= -1293146722334523303L;

			@Override
			public int getSize()
			{
				return sizes.length;
			}

			@Override
			public Integer getElementAt(int index)
			{
				return sizes[index];
			}
		});
		listFontSize.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				Font f = new Font(font.getName(), font.getStyle(),
						(Integer) listFontSize.getSelectedValue());
				font = f;
				lblSample.setFont(font);
			}
		});
		// adjust split pane
		splitPane.resetToPreferredSizes();
	}

	/**
	 * Returns the user selected font.
	 * 
	 * @return the font
	 */
	public Font getCustomFont()
	{
		return font;
	}

	private static void addPopup(Component component, final JPopupMenu popup)
	{
		component.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				if (e.isPopupTrigger())
				{
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e)
			{
				if (e.isPopupTrigger())
				{
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e)
			{
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
