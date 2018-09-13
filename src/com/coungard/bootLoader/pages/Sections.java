package com.coungard.bootLoader.pages;

import com.coungard.bootLoader.util.ProcessResultReader;
import com.coungard.bootLoader.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Sections extends JPanel {
    private final static String disksPath = "src/com/coungard/bootLoader/res/script/getdisk.sh";

    public Sections() {
        setLayout(null);
        setOpaque(false);

        JLabel mainLabel = new JLabel("Выберите раздел для установки");
        mainLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
        mainLabel.setBounds(40,20,600,40);
        add(mainLabel);
    }

    public void showDisks() throws InterruptedException {
        final Process process = Utils.runCmd(new String[]{disksPath});
        ProcessResultReader stderr = new ProcessResultReader(process.getErrorStream(), "STDERR");
        ProcessResultReader stdout = new ProcessResultReader(process.getInputStream(), "STDOUT");

        stderr.start();
        stdout.start();
        final int exitValue = process.waitFor();
        Thread.sleep(400);
        if (exitValue == 0){
            addButtons(stdout.lines);
        } else {
            showWarningLabel(stderr.lines);
        }
    }

    private void addButtons(ArrayList<String> lines) {
        int count = lines.size();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            JButton button = new JButton(line);
            button.setBounds(170, (200 - count*30) + i * 60, 250, 40);
            add(button);

            String sector = line.substring(0, line.indexOf(" "));
            button.addActionListener(new Listener(sector));
        }
    }

    private void showWarningLabel(ArrayList<String> line) {
        JLabel label = new JLabel(line.get(0));
        label.setBounds(0,240, getWidth(), 50);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 22));
        label.setForeground(Color.RED);
        add(label);
    }

    private void hideThisPage() {
        this.setVisible(false);
    }

    private class Listener implements ActionListener {
        private String section;

        Listener(String section) {
            this.section = section;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                hideThisPage();
                Loading loading = new Loading(section);
                loading.setBounds(0,0, getWidth(), getHeight());
                add(loading);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}
