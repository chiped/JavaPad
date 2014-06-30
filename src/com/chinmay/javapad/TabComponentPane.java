package com.chinmay.javapad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TabComponentPane extends JPanel {
	private TextArea textArea;
	private JButton closeButton;
	private JPanel titlePanel;
	private String name;
	private Viewer viewer;
	private File file;
	private JLabel fileNameLabel;
	private boolean edited;
	private RightClickMenu popup;
	private JScrollPane scrollPane;
	
	public TextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}

	public JButton getCloseButton() {
		return closeButton;
	}

	public void setCloseButton(JButton closeButton) {
		this.closeButton = closeButton;
	}

	public JPanel getTitlePanel() {
		return titlePanel;
	}

	public void setTitlePanel(JPanel titlePanel) {
		this.titlePanel = titlePanel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public TabComponentPane(String name, Viewer viewer) {
		this.name = name;
		this.viewer = viewer;
	}

	public void initialize() {
		textArea = new TextArea();
		
		scrollPane = new JScrollPane (textArea,                
        		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		viewer.addTab(name, this);
		
		int totalTabs = viewer.getTabCount();
		
		closeButton = new JButton("   x");
		closeButton.setBorder(BorderFactory.createEmptyBorder());
		closeButton.setContentAreaFilled(false);
		closeButton.setForeground(Color.GRAY);
		closeButton.setToolTipText("Close this tab");
		
		titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		fileNameLabel = new JLabel(name);
		titlePanel.add(fileNameLabel);
		titlePanel.add(closeButton);
		titlePanel.setOpaque(false);

		viewer.setTabComponentAt(totalTabs-1, titlePanel);	
		addListeners();
	}

	private void addListeners() {
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				closeButton.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				closeButton.setForeground(Color.GRAY);
			}
		});
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int option = JOptionPane.YES_OPTION;
				boolean saved = false;
				if(edited) {
					option = JOptionPane.showConfirmDialog(TabComponentPane.this, "Changes were made to "+name+"\nDo you want to save?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
					if( option == JOptionPane.YES_OPTION) 
						if(file==null)
							saved = saveFileAs();
						else
							saved = saveFile();
				}
				if( !edited || (option == JOptionPane.YES_OPTION && saved) || option == JOptionPane.NO_OPTION)
					viewer.remove(viewer.indexOfComponent(TabComponentPane.this));
			}			
		});
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				edited = true;
				fileNameLabel.setText("*"+name);
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				edited = true;
				fileNameLabel.setText("*"+name);
			}
			
		});
		
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(arg0.isPopupTrigger()) {
					popup = new RightClickMenu(textArea);
					popup.initialize();
					popup.show(textArea, arg0.getX(), arg0.getY());
				}
			}
		});
	}

	public boolean saveFile() {
		if(file==null) {
			return saveFileAs();
		}
		return writeFile();
	}

	public boolean saveFileAs() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Text files(.txt)", "txt"));
		fc.showSaveDialog(this);	
		file = fc.getSelectedFile();
		if(file==null)
			return false;
		else if(!file.exists()) 
			return writeFile();
		else if(JOptionPane.showConfirmDialog(this, file.getName()+" already exists. Overwrite?", "Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
			return writeFile();
		return false;        
	}
	
	public void openFile(File file) {
		this.file = file;
		StringBuffer fileContent = new StringBuffer();
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			String line;
			while((line=fileReader.readLine())!=null) {
				fileContent.append(line).append("\n");
			}
			fileReader.close();
			textArea.setText(fileContent.toString());
			edited = false;
			fileNameLabel.setText(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean writeFile() {
		try {
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
			fileWriter.write(textArea.getText());
			fileWriter.flush();
			fileWriter.close();
			fileNameLabel.setText(file.getName());
			name = file.getName();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void wrap() {
		textArea.setLineWrap(Global.isWrapped());
	}
}