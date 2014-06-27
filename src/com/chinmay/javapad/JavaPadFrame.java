package com.chinmay.javapad;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

public class JavaPadFrame extends JFrame {
	private Explorer explorer;
	private Viewer viewer;
	private JSplitPane splitPane;
	private MenuBar menuBar;
	
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
	public JavaPadFrame() {
		super("JavaPad");
//		initialize();
	}
	public void initialize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width/2, screenSize.height/2);
		this.setLocation(screenSize.width/4, screenSize.height/4);
		menuBar = new MenuBar();
		menuBar.setViewer(viewer);
		menuBar.initialize();
		this.setJMenuBar(menuBar);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, explorer, viewer);
		splitPane.setDividerLocation(200);
		this.add(splitPane);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog((JFrame) e.getSource(), "Are you sure you want to exit ?", "Exit Mine Sweeper", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                	boolean canceled = false;
                	while(viewer.getTabCount() > 0) {
                		int tabCount = viewer.getTabCount();
                		((TabComponentPane)viewer.getComponentAt(0)).getCloseButton().doClick();
                		if(tabCount == viewer.getTabCount()) {
                			canceled = true;
                			break;
                		}
                	}
                	if(!canceled)
                		System.exit(0);
                }
	        }
	    });
		this.setVisible(true);
	}
}