package com.coungard.bootLoader.pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenu extends JPanel {

    public final JButton start;

    public MainMenu() {
        setLayout(null);
        setOpaque(false);

        JLabel label = new JLabel();
        label.setBounds(100,0, 500,200);
        label.setText("WELCOME TO SETUP");
        label.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));

        JButton exit = new JButton("EXIT");
        start = new JButton("CONTINUE");

        exit.setBounds(50, 240, 200, 50);
        start.setBounds(350, 240, 200, 50);

        exit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(label);
        add(exit);
        add(start);
    }
}
