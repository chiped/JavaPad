package com.chinmay.javapad;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FindMenu extends JFrame {
	private JPanel findPanel;
	private JButton nextButton;
	private JButton replaceButton;
	private JButton replaceAllButton;
	private JButton closeButton;
	private JLabel findLabel;
	private JLabel replaceLabel;
	private JTextField findText;
	private JTextField replaceText;
	private ButtonGroup buttonGroup;
	private JRadioButton upButton;
	private JRadioButton downButton;
	private JCheckBox matchCase;
	private Viewer viewer;
	
	public Viewer getViewer() {
		return viewer;
	}

	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}

	public void initialize() {
		initalizeComponents();
		addListeners();
		generatePanel();
		
		add(findPanel);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pack();
		setAlwaysOnTop(true);
		//setVisible(true);
	}
	
	private void generatePanel() {
		JPanel textPanel = new JPanel(new GridLayout(2,2, 5, 5));
		textPanel.add(findLabel);
		textPanel.add(findText);
		textPanel.add(replaceLabel);
		textPanel.add(replaceText);
		
		JPanel optionsPanel = new JPanel();
		optionsPanel.add(matchCase);
		optionsPanel.add(upButton);
		optionsPanel.add(downButton);
		
		JPanel buttonsPanel = new JPanel(new GridLayout(2,2, 5, 5));
		buttonsPanel.add(nextButton);
		buttonsPanel.add(replaceButton);
		buttonsPanel.add(replaceAllButton);
		buttonsPanel.add(closeButton);
		
		findPanel.setLayout(new GridLayout(3,1));
		findPanel.add(textPanel);
		findPanel.add(optionsPanel);
		findPanel.add(buttonsPanel);
	}

	private void initalizeComponents() {
		findPanel = new JPanel();
		nextButton = new JButton("Find Next");
		replaceButton = new JButton("Replace");
		replaceAllButton = new JButton("Replace All");
		closeButton = new JButton("Close");
		findLabel = new JLabel("Find what: ");
		replaceLabel = new JLabel("Replace with: ");
		findText = new JTextField(20);
		replaceText = new JTextField(20);
		buttonGroup = new ButtonGroup();
		upButton = new JRadioButton("Up");
		downButton = new JRadioButton("Down");
		buttonGroup.add(upButton);
		buttonGroup.add(downButton);
		downButton.setSelected(true);
		matchCase = new JCheckBox("Match case");
	}
	
	private void addListeners() {
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TextArea textArea = ((TabComponentPane)viewer.getSelectedComponent()).getTextArea();
				String toSearch = matchCase.isSelected() ? findText.getText() : findText.getText().toLowerCase();
				String searchIn = matchCase.isSelected() ? textArea.getText() : textArea.getText().toLowerCase();
				if(toSearch.length() == 0) {
					Toolkit.getDefaultToolkit().beep();
					return;
				}					
				int from = downButton.isSelected() ? textArea.getSelectionEnd() : textArea.getSelectionStart()-1;
				int found = downButton.isSelected() ? searchIn.indexOf(toSearch, from) : searchIn.lastIndexOf(toSearch, from);
				if(found == -1)
					Toolkit.getDefaultToolkit().beep();
				else {
					textArea.select(found, found+toSearch.length());
				}
			}
		});
		
		replaceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TextArea textArea = ((TabComponentPane)viewer.getSelectedComponent()).getTextArea();
				String toSearch = matchCase.isSelected() ? findText.getText() : findText.getText().toLowerCase();
				String replaceString = replaceText.getText();
				String searchIn = matchCase.isSelected() ? textArea.getText() : textArea.getText().toLowerCase();
				if(toSearch.length() == 0) {
					Toolkit.getDefaultToolkit().beep();
					return;
				}
				if(textArea.getSelectedText()!=null && textArea.getSelectedText().equals(toSearch)) {
					StringBuffer newText = new StringBuffer();
					int startPosition = textArea.getSelectionStart();
					int endPosition = textArea.getSelectionEnd();
					newText.append(searchIn.substring(0, startPosition) ).append(replaceString).append(searchIn.substring(endPosition) );
					textArea.setText(newText.toString());
					if(downButton.isSelected())
						textArea.setCaretPosition(endPosition);
					else
						textArea.setCaretPosition(startPosition-1);
				}
				searchIn = matchCase.isSelected() ? textArea.getText() : textArea.getText().toLowerCase();
				int from = downButton.isSelected() ? textArea.getSelectionEnd() : textArea.getSelectionStart()-1;
				int found = downButton.isSelected() ? searchIn.indexOf(toSearch, from) : searchIn.lastIndexOf(toSearch, from);
				if(found == -1)
					Toolkit.getDefaultToolkit().beep();
				else {
					textArea.select(found, found+toSearch.length());
				}
			}			
		});
		
		replaceAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TextArea textArea = ((TabComponentPane)viewer.getSelectedComponent()).getTextArea();
				String toSearch = matchCase.isSelected() ? findText.getText() : findText.getText().toLowerCase();
				String replaceString = replaceText.getText();
				String searchIn = matchCase.isSelected() ? textArea.getText() : textArea.getText().toLowerCase();
				if(toSearch.length() == 0 || !searchIn.contains(toSearch)) {
					Toolkit.getDefaultToolkit().beep();
					return;
				}
				textArea.setText(searchIn.replace(toSearch, replaceString));
			}
		});
		
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FindMenu.this.setVisible(false);
			}
		});
	}
	
	public void findNext() {
		if(findText.getText().equals(""))
			FindMenu.this.setVisible(true);
		else
			nextButton.doClick();
	}
}