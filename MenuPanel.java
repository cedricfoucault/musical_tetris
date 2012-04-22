import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.plaf.basic.BasicArrowButton;

import java.util.concurrent.atomic.AtomicInteger;

class MenuPanel extends JPanel implements ActionListener {
    final AtomicInteger startLevel;
    final JLabel levelValue; 
    
	public MenuPanel(ActionListener start, ActionListener exit, 
	    AtomicInteger level) 
	{
		super(new GridBagLayout());
        // setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBackground(Color.BLACK);
		setAlignmentY(TOP_ALIGNMENT);
		
		startLevel = level;
		
		// Layout constraints for the components
		GridBagConstraints cMenu = new GridBagConstraints();
		cMenu.insets = new Insets(0, 0, 10, 0);
		
		// "START" button
		MenuButton startButton = new MenuButton("START");
		startButton.addActionListener(start);
		
		// "EXIT" button
		MenuButton exitButton = new MenuButton("EXIT");
		exitButton.addActionListener(exit);
		
		// add the buttons
		cMenu.gridx = 0;
		cMenu.gridy = 0;
		add(startButton, cMenu);
		cMenu.gridy = 1;
		add(exitButton, cMenu);
		
		// "LEVEL" panel to choose the start level
		JPanel levelPane = new JPanel();
		levelPane.setPreferredSize(new Dimension(150, 50));
        // levelPane.setMaximumSize(new Dimension(250, 50));
        // levelPane.setAlignmentX(CENTER_ALIGNMENT);
    
        // create the labels
        JLabel levelLabel = new JLabel("CHOOSE LEVEL:");
        levelValue = new JLabel("1");
        // levelValue.setFont(TetrisFont.getTetrisFont(32f));
    
        // create a panel for the arrows
        JPanel arrowPane = new JPanel(new GridBagLayout());
        // create the arrows
        GridBagConstraints c = new GridBagConstraints();
        Dimension buttonSize = new Dimension(20, 20);
        // BasicArrowButton arrowUp   = 
        //     new BasicArrowButton(
        //         BasicArrowButton.NORTH,
        //         Color.BLACK,
        //         Color.BLACK,
        //         Color.WHITE,
        //         Color.BLACK
        //     );
        BasicArrowButton arrowUp =
            new BasicArrowButton(BasicArrowButton.NORTH);
        BasicArrowButton arrowDown = 
            new BasicArrowButton(BasicArrowButton.SOUTH);
        arrowUp.setPreferredSize(buttonSize);
        arrowUp.setActionCommand("level++");
        // arrowUp.setBackground(Color.BLACK);
        // arrowDown.setForeground(Color.WHITE);
        arrowDown.setPreferredSize(buttonSize);
        arrowDown.setActionCommand("level--");
        // arrowDown.setBackground(Color.BLACK);
        // arrowDown.setForeground(Color.WHITE);
        // add the listeners for the arrows
        arrowUp.addActionListener(this);
        arrowDown.addActionListener(this);
        // add the two arrows using the grid layout
        c.insets = new Insets(0, 0, 4, 0);
        c.gridx = 0;
        c.gridy = 0;
        arrowPane.add(arrowUp, c);
        c.gridy = 1;
        arrowPane.add(arrowDown, c);
        
        levelPane.add(levelLabel);
        levelPane.add(levelValue);
        levelPane.add(arrowPane);
        
        cMenu.gridy = 2;
        add(levelPane, cMenu);
        // add(Box.createVerticalGlue());
    }

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void actionPerformed(ActionEvent e) {
        if ("level++".equals(e.getActionCommand())) { // increment level
            if (startLevel.get() < 9) {
                startLevel.getAndIncrement();
                levelValue.setText(startLevel.toString());
            }
        } else { // decrement level
            if (startLevel.get() > 1) {
                startLevel.getAndDecrement();
                levelValue.setText(startLevel.toString());
            }
        }
    }
}

class MenuButton extends JButton {
	MenuButton(String label) {
		super(label);
	}
}

