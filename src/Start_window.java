import javax.swing.*;
import java.awt.*;

public class Start_window extends JFrame {
  public Start_window() {
    setTitle("Object-Oriented Programming in Java"); //set title of frame
    setSize(600, 400); //set size of frame
    this.setLocationRelativeTo(null); //display the frame center of screen
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close app when click X   

    JLabel label_start = new JLabel("Welcome to Traffic Simulation"); //create label
    JButton button_start = new JButton("START"); //create button
    JPanel main_panel = new JPanel(); //create main panel to store label and button
    main_panel.setLayout(new GridBagLayout()); //set layout for main panel

    ImageIcon icon_start = new ImageIcon("gif_start.gif"); //create icon, gif
    JLabel icon_start_label = new JLabel(icon_start); //create label to store icon
    
    GridBagConstraints gbc_main_panel = new GridBagConstraints(); //create constraints for main panel
    gbc_main_panel.gridx = 0; //set x for main panel, 1 column
    gbc_main_panel.gridy = 0; //set y for main panel, 1st row
    gbc_main_panel.insets = new Insets(0, 0, 20, 0); //set padding for 1st row
    main_panel.add(label_start, gbc_main_panel); //add label to main panel, 1st row

    gbc_main_panel.gridy = 1; //set y for main panel, 2nd row
    gbc_main_panel.insets = new Insets(0, 0, 50, 0); //set padding for 2nd row
    gbc_main_panel.ipadx = 50; //set padding x for button
    gbc_main_panel.ipady = 10; //set padding y for button
    main_panel.add(button_start, gbc_main_panel); //add button to main panel, 2nd row

    gbc_main_panel.gridy = 2; //set y for main panel, 3rd row
    main_panel.add(icon_start_label, gbc_main_panel); //add icon to main panel, 3rd row

    //DECORATION

    //label
    label_start.setFont(new Font("Tahoma", Font.BOLD, 28)); //set font for label
    label_start.setForeground(new Color(0xE4E4E4)); //set color for label

    //button
    button_start.setFont(new Font("Tahoma", Font.BOLD, 16)); //set font for button
    button_start.setBackground(new Color(0x1C1C1C)); //set background color for button
    button_start.setForeground(new Color(0xE4E4E4)); //set letter color for button
    button_start.setFocusPainted(false); //remove focus border, rectangle around letter, for button
    button_start.setBorderPainted(false); //remove border for button
    button_start.setToolTipText("\"Linku stato\""); //set tooltip for button
    button_start.setCursor(new Cursor(Cursor.HAND_CURSOR)); //change cursor when hover button

    //main panel
    main_panel.setBackground(new Color(0x2C2C2C)); //set background color for main panel

    this.add(main_panel, BorderLayout.CENTER); //add main panel to frame, center (must have to display)

    //FUNCITON OF BUTTON
    button_start.addActionListener(e -> {
      // printout when click
      System.out.println("Start button clicked!");
      // stop the application
      System.exit(0);
    });
  }
}