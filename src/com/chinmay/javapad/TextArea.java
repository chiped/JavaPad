package com.chinmay.javapad;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class TextArea extends JTextArea {
	
	private UndoManager undoManager;
	
	public TextArea() {
		setWrapStyleWord(true);
		setLineWrap(Global.isWrapped());
		undoManager = new UndoManager();
		getDocument().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit());
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Global.getStatusBar().updateCaretPosition();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Global.getStatusBar().updateCaretPosition();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				Global.getStatusBar().updateCaretPosition();
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Global.getStatusBar().updateCaretPosition();
			}
		});
	}
	
	public boolean isCutEnabled() {
		return isEnabled() && getSelectedText()!=null && getSelectedText().length() > 0;
	}
	
	public boolean isCopyEnabled() {
		return isEnabled() && getSelectedText()!=null && getSelectedText().length() > 0;
	}
	
	public boolean isPasteEnabled() {
		Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		return isEnabled() && isEditable() && content.isDataFlavorSupported(DataFlavor.stringFlavor);
	}
	
	public boolean isDeleteEnabled() {
		return isEnabled() && getSelectedText()!=null && getSelectedText().length() > 0;
	}
	
	public boolean isSelectAllEnabled() {
		return isEnabled() && !getText().equals("");
	}
	
	public boolean isUndoEnabled() {
		return undoManager.canUndo();
	}
	
	public boolean isRedoEnabled() {
		return undoManager.canRedo();
	}

	public void undo() {
		undoManager.undo();
	}

	public void redo() {
		undoManager.redo();
	}

	public void delete() {
		String text = getText();
		int caretPosition = getSelectionStart();
		StringBuffer newText = new StringBuffer();
		newText.append(text.substring(0, getSelectionStart()) ).append(text.substring(getSelectionEnd()) );
		setText(newText.toString());
		setCaretPosition(caretPosition);
	}
	
	public void addTimeDate() {
		String timeDate = Calendar.getInstance().getTime().toString();
		String text = getText();
		StringBuffer newText = new StringBuffer();
		int caretPosition = getSelectionStart();
		newText.append(text.substring(0, getSelectionStart()) ).append(timeDate).append(text.substring(getSelectionEnd()) );
		setText(newText.toString());
		setCaretPosition(caretPosition + timeDate.length());
	}

}