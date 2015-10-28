/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * FileTransferHandler.java is PROPRIETARY/CONFIDENTIAL built in 下午5:18:41,
 * 2015年10月19日.
 * Use is subject to license terms.
 */

package com.frank.swing;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;

import javax.swing.TransferHandler;

/**
 * The file tranfer handler.
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class FileTransferHandler extends TransferHandler
{
	/**
	 * The file visitor interface, declaring the function of retrieving the
	 * current dragged files.
	 * <p>
	 * </p>
	 * 
	 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
	 * @version 1.0.0
	 */
	public interface FileVisitor
	{
		/**
		 * Visit the transfered file.
		 * 
		 * @param file
		 *            the transfered file.
		 */
		void visit(File file);
	}

	/**
	 * The file visitor.
	 */
	protected FileVisitor visitor;

	/**
	 * Construct an instance of <tt>FileTransferHandler</tt>.
	 * 
	 * @param visitor
	 *            the file visitor
	 */
	public FileTransferHandler(FileVisitor visitor)
	{
		this.visitor = visitor;
	}

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8232497606617937711L;

	/**
	 * @see javax.swing.TransferHandler#canImport(javax.swing.TransferHandler.TransferSupport)
	 */
	public boolean canImport(TransferHandler.TransferSupport support)
	{
		if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
			return false;
		return true;
	}

	/**
	 * @see javax.swing.TransferHandler#importData(javax.swing.TransferHandler.TransferSupport)
	 */
	public boolean importData(TransferHandler.TransferSupport support)
	{
		if (!canImport(support))
			return false;
		Transferable t = support.getTransferable();
		try
		{
			java.util.List<File> l = (java.util.List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
			for (File f : l)
				visitor.visit(f);
		}
		catch (UnsupportedFlavorException | IOException e)
		{
			return false;
		}
		return true;
	}
}
