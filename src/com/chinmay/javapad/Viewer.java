package com.chinmay.javapad;

import java.io.File;
import javax.swing.JTabbedPane;

public class Viewer extends JTabbedPane {
	
	private int newDocumentCount = 0;
	
	public Viewer() {
		initialize();
	}
	
	public void initialize() {
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		generateNewTab(null);
	}
	
	public TabComponentPane newTab(String name) {
		TabComponentPane newTab = new TabComponentPane(name, this);
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
	
}