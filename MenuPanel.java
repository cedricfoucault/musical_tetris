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

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    MenuPanel(ActionListener menuListener) {
        super(new GridBagLayout());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(900, 640));
        GridBagConstraints c = new GridBagConstraints();
        // add the text label "CHOOSE START LEVEL:"
        c.gridwidth = 9;
        c.insets = new Insets(0, 0, 30, 0);
        c.gridx = 0;
        c.gridy = 0;
        JLabel levelLab = new JLabel("CHOOSE START LEVEL");
        levelLab.setBackground(Color.BLACK);
        levelLab.setFont(TetrisFont.getTetrisFont(52f));
        levelLab.setForeground(Color.WHITE);
        add(levelLab, c);
        // add the 9 start level buttons
        c.gridy = 1;
        c.gridwidth = 1;
        c.insets = new Insets(0, 0, 130, 0);
        c.gridx = 0;
        StartLevelButton button; // = new StartLevelButton(menuListener, 1);
        // add(button1, c);
        // button1.requestFocusInWindow();
        for (int i = 1; i <= 9; i++) {
            c.gridx = i - 1;
            button = new StartLevelButton(menuListener, i);
            add(button, c);
            button.setFocusable(true);
            // add(new StartLevelButton(menuListener, i), c);
        }
        c.gridwidth = 9;
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        // add the exit button
        TetrisButton exitLab = 
            new TetrisButton(menuListener, "EXIT", 180, 66);
        add(exitLab, c);
    }
}

class TetrisButton extends JPanel implements MouseListener {
	private String label;
	private int width = 66;
	private int height;
	private Dimension size;
	private float fontSize = 52;
    private ActionListener listener;
    
    public TetrisButton(ActionListener buttonListener, String label, 
        int width, int height) {
        super();
        // set properties
        this.label = label;
        this.width = width;
        this.height = height;
        this.size = new Dimension(width, height);
        setSize(width, height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setFocusable(true);
        setBackground(Color.BLACK);       
        // add listeners
        enableInputMethods(true);	
        addMouseListener(this);
        addActionListener(buttonListener);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // load the font and get the text's metrics
		Graphics2D g2D  = (Graphics2D) g;
		Font baseFont   = g2D.getFont();
		Color baseColor = g2D.getColor();
		g2D.setFont(TetrisFont.getTetrisFont(fontSize));
		FontMetrics fm  = g2D.getFontMetrics();
		int strWidth    = fm.stringWidth(label);
		int strHeight   = fm.getHeight();
		// draw the text corresponding to the level in white
		g2D.setColor(Color.WHITE);
		g2D.drawString(label, 
		    (width - strWidth) / 2, (height + strHeight) / 2);
		// put back the default properties
		g2D.setFont(baseFont);
		g2D.setColor(baseColor);
    }
    
    public void mouseClicked(MouseEvent e) {
        notifyListener(e);
    }
    
    public void mouseEntered(MouseEvent e) {
        // highlight in red when mouse is over the button
        // setBackground(Color.RED);
        setBackground(new Color(0xCD0000));
        // change the cursor to the "hand cursor"
    	setCursor(new Cursor(Cursor.HAND_CURSOR));
        repaint();
    }
    
    public void mouseExited(MouseEvent e) {
        // restore the default background
        setBackground(Color.BLACK);
        // restore the default cursor
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        repaint();
    }
    public void mousePressed(MouseEvent e) {
        setBackground(new Color(0xFFA500));
        repaint();
    }
    
    public void mouseReleased(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setBackground(Color.BLACK);
        repaint();
    }
    
    private void addActionListener(ActionListener listener) {
        this.listener = listener;
    }
    
    private void notifyListener(MouseEvent e)
    {
        ActionEvent event = new ActionEvent(
            this,
            ActionEvent.ACTION_PERFORMED,
            label
        );
        
        listener.actionPerformed(event);
    }    
}

class StartLevelButton extends TetrisButton implements MouseListener {
	private static final int WIDTH = 66;
	private static final int HEIGHT = 66; 
    
    public StartLevelButton(ActionListener listener, int level) {
        super(listener, Integer.toString(level), WIDTH, HEIGHT);
    }
}


// class MenuPanel extends JPanel implements ActionListener {
//     final AtomicInteger startLevel;
//     final JLabel levelValue; 
//     
//  public MenuPanel(ActionListener start, ActionListener exit, 
//      AtomicInteger level) 
//  {
//      super(new GridBagLayout());
//         // setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
//      setBackground(Color.BLACK);
//      setAlignmentY(TOP_ALIGNMENT);
//      
//      startLevel = level;
//      
//      // Layout constraints for the components
//      GridBagConstraints cMenu = new GridBagConstraints();
//      cMenu.insets = new Insets(0, 0, 10, 0);
//      
//      // "START" button
//      MenuButton startButton = new MenuButton("START");
//      startButton.addActionListener(start);
//      
//      // "EXIT" button
//      MenuButton exitButton = new MenuButton("EXIT");
//      exitButton.addActionListener(exit);
//      
//      // add the buttons
//      cMenu.gridx = 0;
//      cMenu.gridy = 0;
//      add(startButton, cMenu);
//      cMenu.gridy = 1;
//      add(exitButton, cMenu);
//      
//      // "LEVEL" panel to choose the start level
//      JPanel levelPane = new JPanel();
//      levelPane.setPreferredSize(new Dimension(150, 50));
//         // levelPane.setMaximumSize(new Dimension(250, 50));
//         // levelPane.setAlignmentX(CENTER_ALIGNMENT);
//     
//         // create the labels
//         JLabel levelLabel = new JLabel("CHOOSE LEVEL:");
//         levelValue = new JLabel("1");
//         // levelValue.setFont(TetrisFont.getTetrisFont(32f));
//     
//         // create a panel for the arrows
//         JPanel arrowPane = new JPanel(new GridBagLayout());
//         // create the arrows
//         GridBagConstraints c = new GridBagConstraints();
//         Dimension buttonSize = new Dimension(20, 20);
//         // BasicArrowButton arrowUp   = 
//         //     new BasicArrowButton(
//         //         BasicArrowButton.NORTH,
//         //         Color.BLACK,
//         //         Color.BLACK,
//         //         Color.WHITE,
//         //         Color.BLACK
//         //     );
//         BasicArrowButton arrowUp =
//             new BasicArrowButton(BasicArrowButton.NORTH);
//         BasicArrowButton arrowDown = 
//             new BasicArrowButton(BasicArrowButton.SOUTH);
//         arrowUp.setPreferredSize(buttonSize);
//         arrowUp.setActionCommand("level++");
//         // arrowUp.setBackground(Color.BLACK);
//         // arrowDown.setForeground(Color.WHITE);
//         arrowDown.setPreferredSize(buttonSize);
//         arrowDown.setActionCommand("level--");
//         // arrowDown.setBackground(Color.BLACK);
//         // arrowDown.setForeground(Color.WHITE);
//         // add the listeners for the arrows
//         arrowUp.addActionListener(this);
//         arrowDown.addActionListener(this);
//         // add the two arrows using the grid layout
//         c.insets = new Insets(0, 0, 4, 0);
//         c.gridx = 0;
//         c.gridy = 0;
//         arrowPane.add(arrowUp, c);
//         c.gridy = 1;
//         arrowPane.add(arrowDown, c);
//         
//         levelPane.add(levelLabel);
//         levelPane.add(levelValue);
//         levelPane.add(arrowPane);
//         
//         cMenu.gridy = 2;
//         add(levelPane, cMenu);
//     }
// 
//  public void paintComponent(Graphics g) {
//         super.paintComponent(g);
//     }
// 
//     public void actionPerformed(ActionEvent e) {
//         if ("level++".equals(e.getActionCommand())) { // increment level
//             if (startLevel.get() < 9) {
//                 startLevel.getAndIncrement();
//                 levelValue.setText(startLevel.toString());
//             }
//         } else { // decrement level
//             if (startLevel.get() > 1) {
//                 startLevel.getAndDecrement();
//                 levelValue.setText(startLevel.toString());
//             }
//         }
//     }
// }
// 
// class MenuButton extends JButton {
//  MenuButton(String label) {
//      super(label);
//  }
// }

