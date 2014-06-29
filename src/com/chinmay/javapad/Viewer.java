package com.chinmay.javapad;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class Viewer extends JTabbedPane {
	
	private int newDocumentCount = 0;

	public Viewer() {
	}
	
	public void initialize() {
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		generateNewTab(null);
	}
	
	public TabComponentPane newTab(String name) {
		TabComponentPane newTab = new TabComponentPane(name, this);
		newTab.initialize();
		setSelectedIndex(getTabCount()-1);
		return newTab;
	}

	public void generateNewTab(File file) {
		if(file == null) {
			newDocumentCount++;
			newTab("Document" + newDocumentCount);
			return;
		}
		TabComponentPane newTab = newTab(file.getName());
		newTab.openFile(file);
		
	}

	public void close() {
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit ?", "Exit JavaPad?", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
        	boolean canceled = false;
        	while(getTabCount() > 0) {
        		int tabCount = getTabCount();
        		((TabComponentPane)getComponentAt(0)).getCloseButton().doClick();
        		if(tabCount == getTabCount()) {
        			canceled = true;
        			break;
        		}
        	}
        	if(!canceled)
        		System.exit(0);
        }
	}
	
}