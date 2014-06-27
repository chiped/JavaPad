package com.chinmay.javapad;

public class Main {

	public static void main(String[] args) {
		JavaPadFrame frame = new JavaPadFrame();
		frame.setExplorer(new Explorer());
		frame.setViewer(new Viewer());
		frame.initialize();
	}

}