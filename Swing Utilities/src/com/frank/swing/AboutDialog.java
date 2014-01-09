/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. AboutDialog.java is built in 2013-5-13.
 */
package com.frank.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * The about dialog which shows the copyright of the application.
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class AboutDialog extends JDialog
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= -8277426808915724316L;
	/**
	 * The content panel.
	 */
	private final JPanel		contentPanel		= new JPanel();

	/**
	 * Construct an instance of <tt>AboutDialog</tt>.
	 * 
	 * @param window
	 *            the parent window
	 */
	public AboutDialog(Window window)
	{
		super(window);
		int[] aligins = { SwingConstants.LEADING, SwingConstants.LEADING,
				SwingConstants.RIGHT };
		initialize(
				Messages.getString("AboutDialog.0"), //$NON-NLS-1$
				Messages.getString("AboutDialog.1"), //$NON-NLS-1$
				aligins,
				Messages.getString("AboutDialog.2"), //$NON-NLS-1$
				Messages.getString("AboutDialog.3"), Messages.getString("AboutDialog.4")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Construct an instance of <tt>AboutDialog</tt> with specified title and
	 * content.
	 * 
	 * @param window
	 *            the parent window
	 * @param title
	 *            the specified dialog title
	 * @param horizontalAlignments
	 *            the horizontal alignments for the current labels; the
	 *            alignments are bounded by {@link SwingConstants}
	 * @param content
	 *            the specified dialog content
	 */
	public AboutDialog(Window window, String title, String button,
			int[] horizontalAlignments, String... contents)
	{
		super(window);
		initialize(title, button, horizontalAlignments, contents);
	}

	/**
	 * Initialize the dialog with specified dialog content.
	 * 
	 * @param title
	 *            the specified dialog title
	 * @param button
	 *            the dialog button text
	 * @param horizontalAlignments
	 *            the horizontal alignments for the current labels; the
	 *            alignments are bounded by {@link SwingConstants}
	 * @param content
	 *            the dialog content
	 */
	protected void initialize(String title, String button,
			int[] horizontalAlignments, String... contents)
	{
		setTitle(title);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(contents.length, 1, 0, 0));
		int i = 0;
		for (String content : contents)
		{
			JLabel label = new JLabel(content);
			if (horizontalAlignments != null && i < horizontalAlignments.length)
				label.setHorizontalAlignment(horizontalAlignments[i++]);
			contentPanel.add(label);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(button);
				okButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		pack();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
