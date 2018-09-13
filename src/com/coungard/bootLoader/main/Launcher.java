package com.coungard.bootLoader.main;

import com.coungard.bootLoader.pages.Loading;
import com.coungard.bootLoader.pages.MainMenu;
import com.coungard.bootLoader.pages.Sections;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Launcher extends JFrame {
    private static MainMenu mainMenu;
    private static Sections sections;
    private static Loading loading;
    private static ArrayList<JPanel> pages = new ArrayList<>();
    private static final String PATH = "/com/coungard/bootLoader/res/img/background.png";

    private Launcher() {
        super("Boot Loader");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600,400);
        getContentPane().setLayout(null);

        mainMenu = new MainMenu();
        sections = new Sections();
        pages.add(mainMenu);
        pages.add(sections);

        for (JPanel panel : pages) {
            panel.setBounds(0,0, getWidth(), getHeight());
            getContentPane().add(panel);
            panel.setVisible(false);
        }
        init();
    }

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                Launcher launcher = new Launcher();
                launcher.setVisible(true);
            }
        });
    }

    private void init() {
        JLabel background = new JLabel();
        background.setIcon(new ImageIcon(getClass().getResource(PATH)));
        background.setBounds(0,0, background.getIcon().getIconWidth(), background.getIcon().getIconHeight());
        getContentPane().add(background, JLayeredPane.DEFAULT_LAYER);

        mainMenu.setVisible(true);
        mainMenu.start.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mainMenu.setVisible(false);
                    sections.showDisks();
                    sections.setVisible(true);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

//    public void startLoading(String section) {
//        sections.setVisible(false);
//        loading = new Loading(section);
//        loading.setBounds(0,0,getWidth(),getHeight());
//        getContentPane().add(loading);
//    }


}
