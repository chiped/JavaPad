package com.chinmay.javapad;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class JavaPadFrame extends JFrame {
	private Explorer explorer;
	private Viewer viewer;
	private JSplitPane splitPane;
	private StatusBar statusBar;
	
	public Explorer getExplorer() {
		return explorer;
	}
	public void setExplorer(Explorer explorer) {
		this.explorer = explorer;
	}
	public Viewer getViewer() {
		return viewer;
	}
	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}
	public StatusBar getStatusBar() {
		return statusBar;
	}
	public void setStatusBar(StatusBar statusBar) {
		this.statusBar = statusBar;
	}
	public JavaPadFrame() {
		super("JavaPad");
//		initialize();
	}
	public void initialize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width/2, screenSize.height/2);
		this.setLocation(screenSize.width/4, screenSize.height/4);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, explorer, viewer);
		splitPane.setDividerLocation(200);
		this.add(splitPane);
		add(statusBar, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
                viewer.close();
	        }
	        @Override
	        public void windowOpened(WindowEvent e) {
	        	((TabComponentPane) viewer.getSelectedComponent()).getTextArea().requestFocus();
	        }
	    });
		this.setVisible(true);
	}
}