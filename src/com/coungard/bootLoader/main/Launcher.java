package com.coungard.bootLoader.main;

import com.coungard.bootLoader.pages.Loading;
import com.coungard.bootLoader.pages.MainMenu;
import com.coungard.bootLoader.pages.Sections;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Launcher extends JFrame {
    private static final String PATH = "/com/coungard/bootLoader/res/img/background.png";
    private MainMenu mainMenu;
    private static Sections sections;
    private static Loading loading;
    private static ArrayList<JPanel> pages = new ArrayList<>();

    private Launcher() {
        super("Boot Loader");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setSize(600,400);
        getContentPane().setLayout(null);

        mainMenu = new MainMenu();
        sections = new Sections();
        loading = new Loading();
        pages.add(mainMenu);
        pages.add(sections);
        pages.add(loading);

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

    public static void bindListener() {
        for (final JButton but : sections.buttons) {
            but.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sections.setVisible(false);
                    Loading.section = but.getToolTipText();
                    loading.setVisible(true);
                    loading.createProgressBar();
                    loading.start();
                }
            });
        }
    }
}
