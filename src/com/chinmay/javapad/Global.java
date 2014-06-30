package com.chinmay.javapad;

public class Global {
	private static boolean wrapped;
	private static StatusBar statusBar;

	public static boolean isWrapped() {
		return wrapped;
	}

	public static void setWrapped(boolean wrapped) {
		Global.wrapped = wrapped;
	}

	public static StatusBar getStatusBar() {
		return statusBar;
	}

	public static void setStatusbar(StatusBar statusBar) {
		Global.statusBar = statusBar;
	}
}