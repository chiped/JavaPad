package com.chinmay.javapad;

import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

public class StatusBar extends JPanel {
	private JLabel capsLock;
	private JLabel numLock;
	private JLabel rowLabel;
	private JLabel columnLabel;
	private JLabel colonLabel;
	private JPanel caretPanel;
	private Toolkit toolkit;
	private Viewer viewer;
	public Viewer getViewer() {
		return viewer;
	}
	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}
	public void initialize() {
		initalizeComponents();
		setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 0));		
		setVisible(false);

		caretPanel.add(rowLabel);
		caretPanel.add(colonLabel);
		caretPanel.add(columnLabel);
		add(caretPanel);
		add(capsLock);
		add(numLock);
		addListener();
		
	}
	private void addListener() {

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if(e.getID() == KeyEvent.KEY_RELEASED) {
					if(e.getKeyCode() == KeyEvent.VK_CAPS_LOCK)
						capsLock.setEnabled(toolkit.getLockingKeyState(KeyEvent.VK_CAPS_LOCK));
					else if(e.getKeyCode() == KeyEvent.VK_NUM_LOCK)
						numLock.setEnabled(toolkit.getLockingKeyState(KeyEvent.VK_NUM_LOCK));
					updateCaretPosition();
				}				
				return false;
			}
		});
		viewer.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				updateCaretPosition();
			}			
		});
	}
	public void updateCaretPosition() {
		if(viewer.getTabCount() > 0) {
			TextArea textArea = ((TabComponentPane)viewer.getSelectedComponent()).getTextArea();
			int offset = textArea.getCaretPosition();
			int row, column;
			try {
				row = textArea.getLineOfOffset(offset);
				column = offset - textArea.getLineStartOffset(row);
				rowLabel.setText((row+1)+" ");
				columnLabel.setText((column+1)+" ");
			} catch (BadLocationException ble) {
				ble.printStackTrace();
			}					
		}
		else {
			caretPanel.setVisible(false);
		}
	}
	private void initalizeComponents() {
		toolkit = Toolkit.getDefaultToolkit();
		capsLock = new JLabel("CAPS");
		numLock = new JLabel("NUM");
		rowLabel = new JLabel("1 ");
		colonLabel = new JLabel(" : ");
		columnLabel = new JLabel("1 ");
		caretPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		capsLock.setEnabled(toolkit.getLockingKeyState(KeyEvent.VK_CAPS_LOCK));
		numLock.setEnabled(toolkit.getLockingKeyState(KeyEvent.VK_NUM_LOCK));
	}
}