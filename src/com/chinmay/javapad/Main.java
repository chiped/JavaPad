package com.chinmay.javapad;

public class Main {

	public static void main(String[] args) {
		JavaPadFrame frame = new JavaPadFrame();
		Explorer explorer = new Explorer();
		Viewer viewer = new Viewer();
		MenuBar menuBar = new MenuBar();
		
		menuBar.setViewer(viewer);
		menuBar.initialize();		
		
		viewer.initialize();
		
		frame.setJMenuBar(menuBar);		
		frame.setExplorer(explorer);
		frame.setViewer(viewer);
		frame.initialize();
	}

}