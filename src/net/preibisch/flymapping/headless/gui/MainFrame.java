package net.preibisch.flymapping.headless.gui;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class MainFrame extends JFrame {

	public MainFrame() {
		super("Main");
		this.setSize(400, 400);
		this.setVisible(true);
		menu();
	}

	private JMenuBar menu() {
		//in the constructor for a JFrame subclass:
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;
		JCheckBoxMenuItem cbMenuItem;
		JRadioButtonMenuItem rbMenuItem;

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		//Build the first menu.
		menu = new JMenu("Config");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem = new JMenuItem("Update paths", new ImageIcon("img/gui/folder.png"));
		menu.add(menuItem);
		menuItem = new JMenuItem("Update cells base", 
				new ImageIcon("img/gui/database.png"));
		menu.add(menuItem);

		//a group of radio button menu items
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
		rbMenuItem.setSelected(true);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
		rbMenuItem = new JRadioButtonMenuItem("Another one");
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		//a group of check box menu items
		menu.addSeparator();
		cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
		menu.add(cbMenuItem);
		cbMenuItem = new JCheckBoxMenuItem("Another one");
		menu.add(cbMenuItem);

		//a submenu
		menu.addSeparator();
		submenu = new JMenu("A submenu");
		menuItem = new JMenuItem("An item in the submenu");
		submenu.add(menuItem);
		menuItem = new JMenuItem("Another item");
		submenu.add(menuItem);
		menu.add(submenu);

		//Build second menu in the menu bar.
		menu = new JMenu("Another Menu");
		menuBar.add(menu);
		return menuBar;

	}

	public static void main(String[] args) {
		new MainFrame();
	}


}
