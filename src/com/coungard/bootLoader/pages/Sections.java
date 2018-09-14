package com.coungard.bootLoader.pages;

import com.coungard.bootLoader.main.Launcher;
import com.coungard.bootLoader.util.ProcessResultReader;
import com.coungard.bootLoader.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Sections extends JPanel {
    private final static String disksPath = "src/com/coungard/bootLoader/res/script/getdisk.sh";
    public ArrayList<JButton> buttons = new ArrayList<>();

    public Sections() {
        setLayout(null);
        setOpaque(false);

        JLabel mainLabel = new JLabel("Select disk to install");
        mainLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
        mainLabel.setBounds(140, 20, 600, 40);
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
        if (exitValue == 0) {
            addButtons(stdout.lines);
        } else {
            showWarningLabel(stderr.lines);
        }
        Launcher.bindListener();
    }

    private void addButtons(ArrayList<String> lines) {
        int count = lines.size();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            JButton button = new JButton(line);
            button.setBounds(170, (200 - count * 30) + i * 60, 250, 40);
            add(button);

            String sector = line.substring(0, line.indexOf(" "));
            button.setToolTipText(sector);
            buttons.add(button);
        }
    }

    private void showWarningLabel(ArrayList<String> line) {
        JLabel label = new JLabel(line.get(0));
        label.setBounds(0, 240, getWidth(), 50);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 22));
        label.setForeground(Color.RED);
        add(label);
    }
}
