/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights reserved.
 * ProxyPanel.java is PROPRIETARY/CONFIDENTIAL built in 2013.
 * Use is subject to license terms.
 */
package com.frank.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

/**
 * An IPv4 proxy setting panel.
 * <p>
 * In this panel, an IPv4 proxy can be configured, or set direct connection.
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class ProxyPanel extends JPanel
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= -3350504569249093553L;
	/**
	 * Text field for the first address of an IPv4 address.
	 */
	private JTextField			txtIPv4_1;
	/**
	 * Text field for the second address of an IPv4 address.
	 */
	private JTextField			txtIPv4_2;
	/**
	 * Text field for the third address of an IPv4 address.
	 */
	private JTextField			txtIPv4_3;
	/**
	 * Text field for the fourth address of an IPv4 address.
	 */
	private JTextField			txtIPv4_4;
	/**
	 * Text field for the port of an IPv4 address.
	 */
	private JTextField			txtIPv4_Port;
	/**
	 * The radio button for manual proxy settings.
	 */
	private JRadioButton		rdbtnManualProxy;
	/**
	 * The radio button for directly connection.
	 */
	private JRadioButton		rdbtnConnectDirectly;

	/**
	 * Create a <tt>ProxyPanel</tt>.
	 * 
	 * @param useTitle
	 *            if {@code true} add a group title to the panel
	 * @param title
	 *            the title content, if {@code null} use default setting
	 * @param proxy
	 *            the initial manual proxy, if {@code null} use empty proxy
	 */
	public ProxyPanel(boolean useTitle, String title, Proxy proxy)
	{
		if (useTitle)
		{
			if (title == null)
				setBorder(new TitledBorder(null,
						Messages.getString("ProxyPanel.0"), //$NON-NLS-1$
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
			else
				setBorder(new TitledBorder(null, title, TitledBorder.LEADING,
						TitledBorder.TOP, null, null));
		}
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0 };
		setLayout(gridBagLayout);
		rdbtnConnectDirectly = new JRadioButton(
				Messages.getString("ProxyPanel.1")); //$NON-NLS-1$
		rdbtnConnectDirectly.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_rdbtnConnectDirectly = new GridBagConstraints();
		gbc_rdbtnConnectDirectly.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnConnectDirectly.insets = new Insets(5, 10, 5, 5);
		gbc_rdbtnConnectDirectly.gridx = 0;
		gbc_rdbtnConnectDirectly.gridy = 0;
		add(rdbtnConnectDirectly, gbc_rdbtnConnectDirectly);
		rdbtnManualProxy = new JRadioButton(Messages.getString("ProxyPanel.2")); //$NON-NLS-1$
		rdbtnManualProxy.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_rdbtnManualProxy = new GridBagConstraints();
		gbc_rdbtnManualProxy.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnManualProxy.insets = new Insets(0, 10, 5, 5);
		gbc_rdbtnManualProxy.gridx = 0;
		gbc_rdbtnManualProxy.gridy = 1;
		add(rdbtnManualProxy, gbc_rdbtnManualProxy);
		txtIPv4_1 = new JTextField();
		txtIPv4_1.setToolTipText(Messages.getString("ProxyPanel.txtIPv4_1.toolTipText")); //$NON-NLS-1$
		GridBagConstraints gbc_txtIPv4_1 = new GridBagConstraints();
		gbc_txtIPv4_1.insets = new Insets(0, 0, 5, 0);
		gbc_txtIPv4_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIPv4_1.gridx = 1;
		gbc_txtIPv4_1.gridy = 1;
		add(txtIPv4_1, gbc_txtIPv4_1);
		txtIPv4_1.setColumns(3);
		JLabel label = new JLabel(".");//$NON-NLS-1$
		label.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 2;
		gbc_label.gridy = 1;
		add(label, gbc_label);
		txtIPv4_2 = new JTextField();
		txtIPv4_2.setToolTipText(Messages.getString("ProxyPanel.txtIPv4_2.toolTipText")); //$NON-NLS-1$
		GridBagConstraints gbc_txtIPv4_2 = new GridBagConstraints();
		gbc_txtIPv4_2.insets = new Insets(0, 0, 5, 0);
		gbc_txtIPv4_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIPv4_2.gridx = 3;
		gbc_txtIPv4_2.gridy = 1;
		add(txtIPv4_2, gbc_txtIPv4_2);
		txtIPv4_2.setColumns(3);
		JLabel label_1 = new JLabel(".");//$NON-NLS-1$
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 0);
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.gridx = 4;
		gbc_label_1.gridy = 1;
		add(label_1, gbc_label_1);
		txtIPv4_3 = new JTextField();
		txtIPv4_3.setToolTipText(Messages.getString("ProxyPanel.txtIPv4_3.toolTipText")); //$NON-NLS-1$
		GridBagConstraints gbc_txtIPv4_3 = new GridBagConstraints();
		gbc_txtIPv4_3.insets = new Insets(0, 0, 5, 0);
		gbc_txtIPv4_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIPv4_3.gridx = 5;
		gbc_txtIPv4_3.gridy = 1;
		add(txtIPv4_3, gbc_txtIPv4_3);
		txtIPv4_3.setColumns(3);
		JLabel label_2 = new JLabel(".");//$NON-NLS-1$
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_2.insets = new Insets(0, 0, 5, 0);
		gbc_label_2.gridx = 6;
		gbc_label_2.gridy = 1;
		add(label_2, gbc_label_2);
		txtIPv4_4 = new JTextField();
		txtIPv4_4.setToolTipText(Messages.getString("ProxyPanel.txtIPv4_4.toolTipText")); //$NON-NLS-1$
		txtIPv4_4.setColumns(3);
		GridBagConstraints gbc_txtIPv4_4 = new GridBagConstraints();
		gbc_txtIPv4_4.insets = new Insets(0, 0, 5, 0);
		gbc_txtIPv4_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIPv4_4.gridx = 7;
		gbc_txtIPv4_4.gridy = 1;
		add(txtIPv4_4, gbc_txtIPv4_4);
		JLabel label_colon = new JLabel(":");//$NON-NLS-1$
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_3.insets = new Insets(0, 0, 5, 0);
		gbc_label_3.gridx = 8;
		gbc_label_3.gridy = 1;
		add(label_colon, gbc_label_3);
		txtIPv4_Port = new JTextField();
		txtIPv4_Port.setToolTipText(Messages.getString("ProxyPanel.txtIPv4_Port.toolTipText")); //$NON-NLS-1$
		GridBagConstraints gbc_txtIPv4_Port = new GridBagConstraints();
		gbc_txtIPv4_Port.insets = new Insets(0, 0, 5, 0);
		gbc_txtIPv4_Port.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIPv4_Port.gridx = 9;
		gbc_txtIPv4_Port.gridy = 1;
		add(txtIPv4_Port, gbc_txtIPv4_Port);
		txtIPv4_Port.setColumns(6);
		// add IP limit
		addIPLimited(txtIPv4_1, 256);
		addIPLimited(txtIPv4_2, 256);
		addIPLimited(txtIPv4_3, 256);
		addIPLimited(txtIPv4_4, 256);
		addIPLimited(txtIPv4_Port, 65536);
		rdbtnConnectDirectly.addActionListener(new SelectListener(
				rdbtnConnectDirectly, false));
		rdbtnManualProxy.addActionListener(new SelectListener(rdbtnManualProxy,
				true));
		// initialize the proxy setting
		if (proxy == null || proxy.type() == Proxy.Type.DIRECT)
		{
			rdbtnConnectDirectly.setSelected(true);
			rdbtnManualProxy.setSelected(false);
		}
		else
		{
			rdbtnConnectDirectly.setSelected(false);
			rdbtnManualProxy.setSelected(true);
			InetSocketAddress sa = (InetSocketAddress) proxy.address();
			System.out.println(sa.getAddress().getHostAddress());
			String[] ips = sa.getAddress().getHostAddress().split("\\p{Punct}");
			System.out.println(Arrays.toString(ips));
			txtIPv4_1.setText(ips[0]);
			txtIPv4_2.setText(ips[1]);
			txtIPv4_3.setText(ips[2]);
			txtIPv4_4.setText(ips[3]);
			txtIPv4_Port.setText(Integer.toString(sa.getPort()));
		}
	}

	/**
	 * The selection listener for radio buttons.
	 * <p>
	 * Perform setting enable actions to the input text fields when the radio
	 * button is selected.
	 * </p>
	 * 
	 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
	 * @version 1.0.0
	 */
	private final class SelectListener implements ActionListener
	{
		private JToggleButton	button;
		private boolean			isSelect;

		/**
		 * Construct an instance of <tt>SelectListener</tt>.
		 * 
		 * @param button
		 *            the button to be listened
		 * @param isSelect
		 *            if {@code true} enable the IP components when the button
		 *            is selected
		 */
		public SelectListener(JToggleButton button, boolean isSelect)
		{
			this.button = button;
			this.isSelect = isSelect;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			boolean b = !(button.isSelected() ^ isSelect);
			txtIPv4_1.setEnabled(b);
			txtIPv4_2.setEnabled(b);
			txtIPv4_3.setEnabled(b);
			txtIPv4_4.setEnabled(b);
			txtIPv4_Port.setEnabled(b);
		}
	}

	/**
	 * Add pop menus and IP input limit listener to the specified text
	 * component.
	 * 
	 * @param c
	 *            the specified text component
	 * @param max
	 *            the maximum value of the input
	 */
	private static void addIPLimited(final JTextComponent c, final int max)
	{
		SwingUtils.addTextJPopMenus(c);
		c.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				try
				{
					if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE)
					{
						Integer i = Integer.valueOf(c.getText()
								+ e.getKeyChar());
						e.consume();
						if (i < max)
							c.setText(i.toString());
						else
							SwingUtils.errorMessage(
									c,
									String.format(
											Messages.getString("ProxyPanel.3"), max - 1)); //$NON-NLS-1$
					}
				}
				catch (NumberFormatException nfe)
				{
					e.consume();
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
	}

	/**
	 * Returns the proxy selected by this panel.
	 * 
	 * @return the proxy selected
	 * @throws IllegalArgumentException
	 *             if the IP address input is invalid
	 */
	public Proxy getProxy() throws IllegalArgumentException
	{
		if (rdbtnConnectDirectly.isSelected())
			return Proxy.NO_PROXY;
		if (rdbtnManualProxy.isSelected())
		{
			int a, b, c, d, port;
			try
			{
				a = Integer.valueOf(txtIPv4_1.getText());
				b = Integer.valueOf(txtIPv4_2.getText());
				c = Integer.valueOf(txtIPv4_3.getText());
				d = Integer.valueOf(txtIPv4_4.getText());
				port = Integer.valueOf(txtIPv4_Port.getText());
			}
			catch (NumberFormatException e)
			{
				throw new IllegalArgumentException(String.format(
						Messages.getString("ProxyPanel.4"), //$NON-NLS-1$
						txtIPv4_1.getText(), txtIPv4_2.getText(),
						txtIPv4_3.getText(), txtIPv4_4.getText(),
						txtIPv4_Port.getText()));
			}
			SocketAddress sa = new InetSocketAddress(String.format(
					"%d.%d.%d.%d", a, b, c, d), port);//$NON-NLS-1$
			return new Proxy(Proxy.Type.HTTP, sa);
		}
		return null;
	}
}
