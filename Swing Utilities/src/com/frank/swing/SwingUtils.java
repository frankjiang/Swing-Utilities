/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. SwingUtils.java is built in 2012-11-2.
 */
package com.frank.swing;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;

/**
 * Swing utilities. It deals with the swing applications, can set their styles,
 * locations and so on.
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class SwingUtils
{
	/**
	 * Move the specified window to the center of the screen. Cautious, it may
	 * occupy two different screens if the system has two or more combined
	 * screens.
	 * 
	 * @param window
	 *            the specified window
	 */
	public static void setWindowToScreenCenter(Window window)
	{
		int screenWidth = window.getToolkit().getScreenSize().width;
		int screenHeight = window.getToolkit().getScreenSize().height;
		window.setLocation(screenWidth / 2 - window.getSize().width / 2,
				screenHeight / 2 - window.getSize().height / 2);
	}

	/**
	 * Set the text component in which only numerics (positive decimal integers
	 * with character 0-9).
	 * 
	 * @param c
	 *            the specified text component
	 */
	public static void setTextComponetIntegerValid(final JTextComponent c)
	{
		c.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				try
				{
					Integer.valueOf(c.getText() + e.getKeyChar());
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
	 * Set the look and feel of the specified component to the style of the
	 * current system.
	 * 
	 * @param c
	 *            the specified component
	 */
	public static void setSystemLookAndFeel(Component c)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(c);
			c.validate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Set the specified window to the style of Windows<sup>TM</sup> system.
	 * 
	 * @param window
	 *            the specified window
	 * @deprecated Please use {@link SwingUtils#setSystemLookAndFeel(Component)}
	 *             instead.
	 */
	public static void setWindowsLookLike(Window window)
	{
		try
		{
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//$NON-NLS-1$
			SwingUtilities.updateComponentTreeUI(window);
			window.validate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Set the specified window to the style of Windows<sup>TM</sup> system,
	 * move it to the center of screen and set it visible.
	 * 
	 * @param window
	 *            the specified window
	 */
	public static void execute(Window window)
	{
		try
		{
			int screenWidth = window.getToolkit().getScreenSize().width;
			int screenHeight = window.getToolkit().getScreenSize().height;
			window.setLocation(screenWidth / 2 - window.getSize().width / 2,
					screenHeight / 2 - window.getSize().height / 2);
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//$NON-NLS-1$
			SwingUtilities.updateComponentTreeUI(window);
			window.validate();
			window.setVisible(true);
		}
		catch (Exception e)
		{
			System.err.println(Messages.getString("SwingUtils.0")); //$NON-NLS-1$
		}
	}

	/**
	 * Add the specified pop-up menu the specified component.
	 * 
	 * @param component
	 *            the specified component
	 * @param popup
	 *            the specified pop-up menu
	 */
	public static void addPopup(Component component, final JPopupMenu popup)
	{
		component.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				if (e.isPopupTrigger())
					showMenu(e);
			}

			public void mouseReleased(MouseEvent e)
			{
				if (e.isPopupTrigger())
					showMenu(e);
			}

			private void showMenu(MouseEvent e)
			{
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	/**
	 * Add the specified text component with pop-up menus including select all,
	 * cut, copy and paste. (all in Chinese, zh_CH with no shortcuts)
	 * 
	 * @param text
	 *            the specified text component
	 */
	public static void addTextJPopMenus(final JTextComponent text)
	{
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(text, popupMenu);
		JMenuItem mntmSelectAll = new JMenuItem(
				Messages.getString("SwingUtils.1")); //$NON-NLS-1$
		mntmSelectAll.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				text.selectAll();
			}
		});
		mntmSelectAll.setMnemonic('A');
		popupMenu.add(mntmSelectAll);
		JMenuItem mntmCut = new JMenuItem(Messages.getString("SwingUtils.2")); //$NON-NLS-1$
		mntmCut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				text.cut();
			}
		});
		mntmCut.setMnemonic('T');
		popupMenu.add(mntmCut);
		JMenuItem mntmCopy = new JMenuItem(Messages.getString("SwingUtils.3")); //$NON-NLS-1$
		mntmCopy.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				text.copy();
			}
		});
		mntmCopy.setMnemonic('C');
		popupMenu.add(mntmCopy);
		JMenuItem mntmPaste = new JMenuItem(Messages.getString("SwingUtils.4")); //$NON-NLS-1$
		mntmPaste.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				text.paste();
			}
		});
		mntmPaste.setMnemonic('P');
		popupMenu.add(mntmPaste);
	}

	/**
	 * Linked two {@linkplain JComboBox}, the maximum one and the minimum one,
	 * the index of maximum one cannot be less than minimum one, vice versa. It
	 * will forbid exceeding the maximum or minimum and will alert the user with
	 * dialog (optional).
	 * 
	 * @param parent
	 *            the parent component of the two {@linkplain JComboBox}
	 * @param max
	 *            the {@linkplain JComboBox} of maximum
	 * @param min
	 *            the {@linkplain JComboBox} of minimum
	 * @param isAlert
	 *            true then shows the alert dialog when the select item exceed
	 *            the maximum or minimum
	 */
	public static void setLinkedComboBox(final Component parent,
			final JComboBox max, final JComboBox min, final boolean isAlert)
	{
		setLinkedComboBox(parent, max, min, 0, isAlert);
	}

	/**
	 * Linked two {@linkplain JComboBox}, the maximum one and the minimum one,
	 * the index of maximum one cannot be less than minimum one, vice versa. It
	 * will forbid exceeding the maximum or minimum and will alert the user with
	 * dialog (optional).
	 * 
	 * @param parent
	 *            the parent component of the two {@linkplain JComboBox}
	 * @param max
	 *            the {@linkplain JComboBox} of maximum
	 * @param min
	 *            the {@linkplain JComboBox} of minimum
	 * @param bias
	 *            the bias for the select item can go beyond the maximum or
	 *            below the minimum without forbidding or warning.
	 * @param isAlert
	 *            true then shows the alert dialog when the select item exceed
	 *            the maximum or minimum with bias calculated.
	 */
	public static void setLinkedComboBox(final Component parent,
			final JComboBox max, final JComboBox min, final int bias,
			final boolean isAlert)
	{
		max.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int maxValue = max.getSelectedIndex();
				int minValue = min.getSelectedIndex();
				if (maxValue + bias < minValue)
				{
					if (isAlert)
						JOptionPane.showConfirmDialog(
								parent,
								Messages.getString("SwingUtils.5"), //$NON-NLS-1$
								Messages.getString("SwingUtils.6"), //$NON-NLS-1$
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.ERROR_MESSAGE);
					max.setSelectedIndex(minValue - bias);
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
		min.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int maxValue = max.getSelectedIndex();
				int minValue = min.getSelectedIndex();
				if (maxValue + bias < minValue)
				{
					if (isAlert)
						JOptionPane.showConfirmDialog(
								parent,
								Messages.getString("SwingUtils.7"), Messages.getString("SwingUtils.8"), //$NON-NLS-1$ //$NON-NLS-2$
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.ERROR_MESSAGE);
					min.setSelectedIndex(maxValue + bias);
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
	}

	/**
	 * Select the file path via {@linkplain FileDialog}.
	 * 
	 * @param parent
	 *            the parent of {@linkplain FileDialog}
	 * @param title
	 *            the title of {@linkplain FileDialog}
	 * @return the selected path
	 */
	public static String selectPath(Frame parent, String title)
	{
		FileDialog dialog = new FileDialog(parent, title, FileDialog.LOAD);
		dialog.setVisible(true);
		String dir = dialog.getDirectory();
		dialog.dispose();
		return dir;
	}

	/**
	 * Select file to save via {@linkplain FileDialog} and create it if not
	 * exists.
	 * 
	 * @param parent
	 *            the parent of {@linkplain FileDialog}
	 * @param title
	 *            the title of {@linkplain FileDialog}
	 * @return the selected file if selected and created, null otherwise
	 * @throws IOException
	 *             If an I/O error occurred
	 */
	public static File selectSaveFile(Window parent, String title)
			throws IOException
	{
		return selectSaveFile(null, parent, title);
	}

	/**
	 * Select file to save via {@linkplain FileDialog} and create it if not
	 * exists.
	 * 
	 * @param file
	 *            the default file or the directory of the default file to set
	 *            the default directory of the {@linkplain FileDialog}
	 * @param parent
	 *            The parent of {@linkplain FileDialog}. <br>
	 *            This type can be {@linkplain Dialog} or {@linkplain Frame},
	 *            otherwise throw a RuntimeException.
	 * @param title
	 *            the title of {@linkplain FileDialog}
	 * @return the selected file if selected and created, null otherwise
	 * @throws IOException
	 *             If an I/O error occurred
	 * @throws RuntimeException
	 *             if the parent is not supported
	 */
	public static File selectSaveFile(File file, Window parent, String title)
			throws IOException
	{
		FileDialog dialog = null;
		if (parent instanceof Dialog)
			dialog = new FileDialog((Dialog) parent, title, FileDialog.SAVE);
		else if (parent instanceof Frame)
			dialog = new FileDialog((Frame) parent, title, FileDialog.SAVE);
		else
			throw new RuntimeException(String.format(
					Messages.getString("SwingUtils.12"), //$NON-NLS-1$
					parent.getClass()));
		if (file != null && file.exists())
		{
			if (file.isDirectory())
				dialog.setDirectory(file.getAbsolutePath());
			else
				dialog.setDirectory(file.getParent());
		}
		dialog.setVisible(true);
		String filename = dialog.getFile();
		if (filename == null || filename.equals(" "))//$NON-NLS-1$
			return null;
		String dir = dialog.getDirectory();
		dialog.dispose();
		File fileSave = new File(dir, filename);
		if (!fileSave.exists())
			if (fileSave.createNewFile())
				return fileSave;
			else
				return null;
		return fileSave;
	}

	/**
	 * Select file to save via {@linkplain JFileChooser} and create it if not
	 * exists.
	 * 
	 * @param parent
	 *            the parent component
	 * @param title
	 *            the title of current dialog
	 * @param filter
	 *            the file filter for the current dialog selection, set null if
	 *            is not needed
	 * @return the selected file if selected and created, null otherwise
	 * @throws IOException
	 *             If an I/O error occurred
	 */
	public static File selectSaveFile(Component parent, String title,
			FileFilter filter) throws IOException
	{
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogType(JFileChooser.SAVE_DIALOG);
		jfc.setDialogTitle(title);
		if (filter != null)
			jfc.setFileFilter(filter);
		jfc.setMultiSelectionEnabled(false);
		jfc.showOpenDialog(parent);
		File file = jfc.getSelectedFile();
		if (file == null)
			return null;
		if (!file.exists())
			if (file.createNewFile())
				return file;
			else
				return null;
		return file;
	}

	/**
	 * Select file to load via {@linkplain FileDialog}.
	 * 
	 * @param parent
	 *            the parent of {@linkplain FileDialog}
	 * @param title
	 *            the title of {@linkplain FileDialog}
	 * @return the selected file if selected and created, null otherwise
	 */
	public static File selectLoadFile(Window parent, String title)
	{
		return selectLoadFile(null, parent, title);
	}

	/**
	 * Select file to load via {@linkplain FileDialog}.
	 * 
	 * @param file
	 *            the default file or the directory of the default file to set
	 *            the default directory of the {@linkplain FileDialog}
	 * @param parent
	 *            The parent of {@linkplain FileDialog}. <br>
	 *            This type can be {@linkplain Dialog} or {@linkplain Frame},
	 *            otherwise use <code>null</code> as default.
	 * @param title
	 *            the title of {@linkplain FileDialog}
	 * @return the selected file if selected and created, <code>null</code>
	 *         otherwise
	 */
	public static File selectLoadFile(File file, Window parent, String title)
	{
		FileDialog dialog = null;
		if (parent instanceof Dialog)
			dialog = new FileDialog((Dialog) parent, title, FileDialog.LOAD);
		else if (parent instanceof Frame)
			dialog = new FileDialog((Frame) parent, title, FileDialog.LOAD);
		else
			throw new RuntimeException(String.format(
					Messages.getString("SwingUtils.12"),////$NON-NLS-1$
					parent.getClass()));
		if (file != null && file.exists())
		{
			if (file.isDirectory())
				dialog.setDirectory(file.getAbsolutePath());
			else
				dialog.setDirectory(file.getParent());
		}
		dialog.setVisible(true);
		String filename = dialog.getFile();
		if (filename == null || filename.equals(" "))//$NON-NLS-1$
			return null;
		String dir = dialog.getDirectory();
		dialog.dispose();
		return new File(dir, filename);
	}

	/**
	 * Select file to load with specified file filter via
	 * {@linkplain JFileChooser}.
	 * 
	 * @param parent
	 *            the parent component
	 * @param title
	 *            the title of current dialog
	 * @param filter
	 *            the file filter for the current dialog selection, set null if
	 *            is not needed
	 * @return the selected file if selected and created, null otherwise
	 */
	public static File selectLoadFile(Component parent, String title,
			FileFilter filter)
	{
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogType(JFileChooser.OPEN_DIALOG);
		jfc.setDialogTitle(title);
		if (filter != null)
			jfc.setFileFilter(filter);
		jfc.setMultiSelectionEnabled(false);
		jfc.showOpenDialog(parent);
		return jfc.getSelectedFile();
	}

	/**
	 * Select file to load with specified file filter via
	 * {@linkplain JFileChooser}.
	 * 
	 * @param parent
	 *            the parent component
	 * @param title
	 *            the title of current dialog
	 * @param filter
	 *            the file filter for the current dialog selection, set null if
	 *            is not needed
	 * @return the selected file if selected and created, null otherwise
	 */
	public static File[] selectLoadFiles(Component parent, String title,
			FileFilter filter)
	{
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogType(JFileChooser.OPEN_DIALOG);
		jfc.setDialogTitle(title);
		if (filter != null)
			jfc.setFileFilter(filter);
		jfc.setMultiSelectionEnabled(true);
		jfc.showOpenDialog(parent);
		return jfc.getSelectedFiles();
	}

	/**
	 * Show error message and beeps. Its title will be Chinese or English
	 * according to the locale.
	 * 
	 * @param parent
	 *            the parent component of message dialog
	 * @param message
	 *            the message to display.
	 */
	public static void errorMessage(Component parent, String message)
	{
		errorMessage(parent, message, true);
	}

	/**
	 * Show error message. Its title will be Chinese or English according to the
	 * locale.
	 * 
	 * @param parent
	 *            the parent component of message dialog
	 * @param message
	 *            the message to display.
	 * @param isBeep
	 *            if need beep set {@code true}
	 */
	public static void errorMessage(Component parent, String message,
			boolean isBeep)
	{
		if (isBeep)
			Toolkit.getDefaultToolkit().beep();
		JOptionPane.showConfirmDialog(parent, message,
				Messages.getString("SwingUtils.9"), //$NON-NLS-1$
				JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Show information message. Its title will be Chinese.
	 * 
	 * @param parent
	 * @param message
	 */
	public static void noticeMessage(Component parent, String message)
	{
		JOptionPane.showConfirmDialog(parent, message,
				Messages.getString("SwingUtils.10"), //$NON-NLS-1$
				JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Show confirm information message. Its title will be Chinese.
	 * 
	 * @param parent
	 * @param message
	 * @return true if confirmed
	 */
	public static boolean confirmMessage(Component parent, String message)
	{
		return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(parent,
				message,
				Messages.getString("SwingUtils.11"), JOptionPane.YES_NO_OPTION, //$NON-NLS-1$
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Show input dialog with specified title and message.
	 * 
	 * @param parent
	 *            the parent component of the input dialog, set {@code null} if
	 *            not has one
	 * @param title
	 *            the title of the dialog
	 * @param message
	 *            the message to display
	 * @return the input string, may be an empty string
	 */
	public static String inputDialog(Component parent, String title,
			String message)
	{
		return JOptionPane.showInputDialog(parent, message, title,
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Show input dialog with specified title, message and initial input
	 * content.
	 * 
	 * @param parent
	 *            the parent component of the input dialog, set {@code null} if
	 *            not has one
	 * @param title
	 *            the title of the dialog
	 * @param message
	 *            the message to display
	 * @param initial
	 *            the initial input content
	 * @return the input string, may be an empty string
	 */
	public static String inputDialog(Component parent, String title,
			String message, String initial)
	{
		Object obj = JOptionPane.showInputDialog(parent, message, title,
				JOptionPane.INFORMATION_MESSAGE, null, null, initial);
		if (obj == null)
			return null;
		else
			return obj.toString();
	}

	/**
	 * Bind the toggle buttons into a group in which only one can be selected.
	 * 
	 * @param buttons
	 *            the toggle buttons to bind
	 */
	public static void setToggleGroups(final JToggleButton... buttons)
	{
		for (int i = 0; i < buttons.length; i++)
		{
			final int t = i;
			buttons[i].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					boolean flag = buttons[t].isSelected();
					if (flag)
					{
						for (int i = 0; i < buttons.length; i++)
							if (i != t)
								buttons[i].setSelected(false);
					}
					else
						buttons[t].setSelected(true);
				}
			});
		}
	}

	/**
	 * Returns the dimension of the specified string <tt>s</tt> in specified
	 * graphic and font environment.
	 * 
	 * @param g
	 *            the graphic environment
	 * @param f
	 *            the font environment
	 * @param s
	 *            the string to display
	 * @return the dimension of the string
	 */
	public static Dimension stringDimension(Graphics g, Font f, String s)
	{
		FontMetrics fm = g.getFontMetrics(f);
		return new Dimension(fm.stringWidth(s), fm.getHeight());
	}
}
