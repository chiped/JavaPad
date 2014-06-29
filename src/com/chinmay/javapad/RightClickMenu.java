package com.chinmay.javapad;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

public class RightClickMenu extends JPopupMenu {
	private TextArea textArea;
	private AbstractAction undo, redo, cut, copy, paste, delete, selectAll;

	public RightClickMenu(TextArea textArea) {
		this.textArea = textArea;
	}
	public void initialize() {
		initalizeComponentes();
		add(undo);
		add(redo);
		addSeparator();
		add(cut);
		add(copy);
		add(paste);
		add(delete);
		addSeparator();
		add(selectAll);
	}
	private void initalizeComponentes() {
		cut = new AbstractAction("Cut") {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.cut();
			}
			@Override
			public boolean isEnabled() {
				return textArea.isCopyEnabled();
			}
		};
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		copy = new AbstractAction("Copy") {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.copy();
			}
			@Override
			public boolean isEnabled() {
				return textArea.isCopyEnabled();
			}
		};
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

		paste = new AbstractAction("Paste") {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.paste();
			}
			@Override
			public boolean isEnabled() {
				return textArea.isPasteEnabled();
			}
		};
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

		delete = new AbstractAction("Delete") {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.delete();
			}
			@Override
			public boolean isEnabled() {
				return textArea.isDeleteEnabled();
			}
		};
		selectAll = new AbstractAction("Select all") {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.selectAll();
			}
			@Override
			public boolean isEnabled() {
				return textArea.isSelectAllEnabled();
			}
		};
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

		undo = new AbstractAction("Undo") {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.undo();
			}
			@Override
			public boolean isEnabled() {
				return textArea.isUndoEnabled();
			}
		};
		undo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		redo = new AbstractAction("Redo") {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.redo();
			}
			@Override
			public boolean isEnabled() {
				return textArea.isRedoEnabled();
			}
		};
		redo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
	}
}