package com.coungard.bootLoader.pages;

import com.coungard.bootLoader.util.ProcessResultReader;
import com.coungard.bootLoader.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class Loading extends JPanel {
    private final String installerPath = "src/com/coungard/bootLoader/res/script/install.sh";
    private File logFile = new File("/tmp/install.log");

    private static JButton reset;
    private static String info = "";
    private JProgressBar progressBar;
    public static String section;
    private int progress = 0;

    public Loading() {
        setLayout(null);
        setOpaque(false);
    }

    public void start() {
        final Thread rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    createProgressBar();
                    final Process process = Utils.runCmd(new String[]{installerPath, section});
                    ProcessResultReader stderr = new ProcessResultReader(process.getErrorStream(), "STDERR");
                    ProcessResultReader stdout = new ProcessResultReader(process.getInputStream(), "STDOUT");

                    stderr.start();
                    stdout.start();
                    FileInputStream input = new FileInputStream(logFile);
                    InputStreamReader reader = new InputStreamReader(input);
                    BufferedReader buffer = new BufferedReader(reader);

                    int iter = 0;
                    while (true) {
                        if ((info = buffer.readLine()) != null) {
                            if (info.equals("install_error")) {
                                addInfo(iter,true);
                                reset.setVisible(true);
                                System.out.println(info);
                                break;
                            }
                            if (info.equals("finish")) {
                                reset.setVisible(true);
                                System.out.println(info);
                                break;
                            }
                            System.out.println(info);
                            addInfo(iter++,false);
                            progress = iter * 15;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        rxThread.start();
        Thread endThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!rxThread.isAlive()) {
                        progressBar.setValue(100);
                        break;
                    } else {
                        progressBar.setValue(progress);
                    }
                }
            }
        });
        endThread.start();
    }

    private void addInfo(int position, boolean error) {
        JLabel label = new JLabel();
        label.setText(info);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        label.setForeground(error ? Color.RED : null);
        label.setBounds(30, 80 + position * 30, 500, 30);
        add(label);
        repaint();
    }

    public void createProgressBar() {
        JLabel label = new JLabel();
        label.setText("INSTALLING TERMINAL");
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        label.setBounds(130, 20, 500, 40);
        add(label);

        progressBar = new JProgressBar();
//        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setBounds(20, 310, 350, 20);
        add(progressBar);

        reset = new JButton("SHUTDOWN");
        reset.setBounds(420,300,150, 40);
        reset.setVisible(false);
        reset.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utils.runCmd(new String[]{"halt"});
            }
        });
        add(reset);
    }
}
