package com.chinmay.javapad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuBar extends JMenuBar {
	private JMenu fileMenu, editMenu, formatMenu, viewMenu, helpMenu;
	private JMenuItem fileNew, fileOpen, fileSave, fileSaveAs, fileExit;
	private JMenuItem editUndo, editRedo, editCut, editCopy, editPaste, editDelete, editFind, editFindNext, editReplace, editSelectAll, editTimeDate;
	private JMenuItem formatFont;
	private JCheckBoxMenuItem viewStatusBar;
	private JMenuItem helpView, helpAbout;
	private Viewer viewer;
	
	public Viewer getViewer() {
		return viewer;
	}

	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}
	
	public JMenuItem getEditCut() {
		return editCut;
	}

	public void setEditCut(JMenuItem editCut) {
		this.editCut = editCut;
	}

	public JMenuItem getEditCopy() {
		return editCopy;
	}

	public void setEditCopy(JMenuItem editCopy) {
		this.editCopy = editCopy;
	}

	public JMenuItem getEditPaste() {
		return editPaste;
	}

	public void setEditPaste(JMenuItem editPaste) {
		this.editPaste = editPaste;
	}

	public JMenuItem getEditDelete() {
		return editDelete;
	}

	public void setEditDelete(JMenuItem editDelete) {
		this.editDelete = editDelete;
	}

	public JMenuItem getEditSelectAll() {
		return editSelectAll;
	}

	public void setEditSelectAll(JMenuItem editSelectAll) {
		this.editSelectAll = editSelectAll;
	}

	public void initialize() {
		generateMenus();
		generateSubMenus();
		addActionListeners();
	}

	private void addActionListeners() {
		addFileMenuListeners();
		addEditMenuListeners();
	}

	private void addEditMenuListeners() {
		editMenu.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				setButtons();				
			}			
		});
		editUndo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				((TabComponentPane) viewer.getSelectedComponent()).getTextArea().undo();
			}
		});
		editRedo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				((TabComponentPane) viewer.getSelectedComponent()).getTextArea().redo();
			}
		});
		editCut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				((TabComponentPane) viewer.getSelectedComponent()).getTextArea().cut();
			}
		});
		editCopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				((TabComponentPane) viewer.getSelectedComponent()).getTextArea().copy();
			}
		});
		editPaste.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				((TabComponentPane) viewer.getSelectedComponent()).getTextArea().paste();
			}
		});
		editDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				((TabComponentPane) viewer.getSelectedComponent()).getTextArea().delete();
			}
		});
		editSelectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				((TabComponentPane) viewer.getSelectedComponent()).getTextArea().selectAll();
			}
		});
		editTimeDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				((TabComponentPane) viewer.getSelectedComponent()).getTextArea().addTimeDate();
			}
		});
	}

	private void addFileMenuListeners() {
		fileNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				viewer.generateNewTab(null);
			}			
		});
		
		fileOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Text files(.txt)", "txt"));
				fc.showOpenDialog(MenuBar.this);				
				if(fc.getSelectedFile()==null)
					return;				
				viewer.generateNewTab(fc.getSelectedFile());
			}			
		});
		fileSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(viewer.getSelectedComponent()!=null) {
					((TabComponentPane)viewer.getSelectedComponent()).saveFile();
				}
			}			
		});
		fileSaveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(viewer.getSelectedComponent()!=null) {
					((TabComponentPane)viewer.getSelectedComponent()).saveFileAs();
				}
			}			
		});
		fileExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewer.close();
			}			
		});
	}

	public void setButtons() {
		boolean atLeastOneTab = (viewer.getTabCount() > 0);
		fileSave.setEnabled(atLeastOneTab);
		fileSaveAs.setEnabled(atLeastOneTab);
		editUndo.setEnabled(atLeastOneTab && ((TabComponentPane) viewer.getSelectedComponent()).getTextArea().isUndoEnabled());
		editRedo.setEnabled(atLeastOneTab && ((TabComponentPane) viewer.getSelectedComponent()).getTextArea().isRedoEnabled());
		editCut.setEnabled(atLeastOneTab && ((TabComponentPane) viewer.getSelectedComponent()).getTextArea().isCutEnabled());
		editCopy.setEnabled(atLeastOneTab && ((TabComponentPane) viewer.getSelectedComponent()).getTextArea().isCopyEnabled());
		editPaste.setEnabled(atLeastOneTab && ((TabComponentPane) viewer.getSelectedComponent()).getTextArea().isPasteEnabled());
		editDelete.setEnabled(atLeastOneTab && ((TabComponentPane) viewer.getSelectedComponent()).getTextArea().isDeleteEnabled());
		editSelectAll.setEnabled(atLeastOneTab && ((TabComponentPane) viewer.getSelectedComponent()).getTextArea().isSelectAllEnabled());
		editFind.setEnabled(atLeastOneTab);
		editFindNext.setEnabled(atLeastOneTab);
		editReplace.setEnabled(atLeastOneTab);
		editTimeDate.setEnabled(atLeastOneTab);
	}

	private void generateSubMenus() {
		fileNew = new JMenuItem("New");
		fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		fileMenu.add(fileNew);
		fileOpen = new JMenuItem("Open...");
		fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		fileMenu.add(fileOpen);
		fileSave = new JMenuItem("Save");
		fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		fileMenu.add(fileSave);
		fileSaveAs = new JMenuItem("Save As...");
		fileMenu.add(fileSaveAs);
		fileMenu.addSeparator();
		fileExit = new JMenuItem("Exit");
		fileMenu.add(fileExit);
		fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		
		editUndo = new JMenuItem("Undo");
		editUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		editUndo.setEnabled(false);
		editMenu.add(editUndo);
		editRedo = new JMenuItem("Redo");
		editRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		editRedo.setEnabled(false);
		editMenu.add(editRedo);
		editMenu.addSeparator();
		editCut = new JMenuItem("Cut");
		editCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		editMenu.add(editCut);
		editCopy = new JMenuItem("Copy");
		editCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		editMenu.add(editCopy);
		editPaste = new JMenuItem("Paste");
		editPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		editMenu.add(editPaste);
		editDelete = new JMenuItem("Delete");
		editMenu.add(editDelete);
		editMenu.addSeparator();
		editFind = new JMenuItem("Find");
		editMenu.add(editFind);
		editFindNext = new JMenuItem("Find Next");
		editMenu.add(editFindNext);
		editReplace = new JMenuItem("Replace");
		editMenu.add(editReplace);
		editMenu.addSeparator();
		editSelectAll = new JMenuItem("Select All");
		editSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		editMenu.add(editSelectAll);
		editTimeDate = new JMenuItem("Time/Date");
		editMenu.add(editTimeDate);
		
		formatFont = new JMenuItem("Font");
		formatMenu.add(formatFont);
		
		viewStatusBar = new JCheckBoxMenuItem("Status Bar");
		viewMenu.add(viewStatusBar);
		
		helpView = new JMenuItem("View Help");
		helpMenu.add(helpView);
		helpAbout = new JMenuItem("About");
		helpMenu.add(helpAbout);
	}

	private void generateMenus() {
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		add(fileMenu);
		
		editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		add(editMenu);
		
		formatMenu = new JMenu("Format");
		formatMenu.setMnemonic(KeyEvent.VK_F);
		add(formatMenu);
		
		viewMenu = new JMenu("View");
		viewMenu.setMnemonic(KeyEvent.VK_V);
		add(viewMenu);
		
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		add(helpMenu);
	}
}