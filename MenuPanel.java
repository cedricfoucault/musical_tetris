import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

class MenuPanel extends JPanel {
	public MenuPanel(ActionListener start, ActionListener exit) {
		super();
		setBackground(Color.BLACK);
		MenuButton startButton = new MenuButton("START");
		startButton.addActionListener(start);
		MenuButton exitButton = new MenuButton("EXIT");
		exitButton.addActionListener(exit);
		add(startButton);
		add(exitButton);
    }

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}

class MenuButton extends JButton {
	MenuButton(String label) {
		super(label);
	}
}
