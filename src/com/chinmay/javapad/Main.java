package com.chinmay.javapad;

public class Main {

	public static void main(String[] args) {
		JavaPadFrame frame = new JavaPadFrame();
		Explorer explorer = new Explorer();
		Viewer viewer = new Viewer();
		MenuBar menuBar = new MenuBar();
		StatusBar statusBar = new StatusBar();
		
		statusBar.setViewer(viewer);
		statusBar.initialize();
		Global.setStatusbar(statusBar);
		
		menuBar.setViewer(viewer);
		menuBar.setStatusBar(statusBar);
		menuBar.initialize();		
		
		viewer.initialize();
		
		frame.setJMenuBar(menuBar);		
		frame.setExplorer(explorer);
		frame.setViewer(viewer);
		frame.setStatusBar(statusBar);
		frame.initialize();
	}

}