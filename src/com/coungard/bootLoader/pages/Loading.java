package com.coungard.bootLoader.pages;

import com.coungard.bootLoader.util.ProcessResultReader;
import com.coungard.bootLoader.util.Utils;

import javax.swing.*;
import java.io.*;

public class Loading extends JPanel {
    private final String installerPath = "src/com/coungard/bootLoader/res/script/install.sh";
    private JProgressBar progressBar = new JProgressBar();
    private File logFile = new File("/tmp/install.log");

    public Loading(String section) throws InterruptedException {
        final Process process = Utils.runCmd(new String[]{installerPath, section});
        ProcessResultReader stderr = new ProcessResultReader(process.getErrorStream(), "STDERR");
        ProcessResultReader stdout = new ProcessResultReader(process.getInputStream(), "STDOUT");

        stderr.start();
        stdout.start();

//        final int exitValue = process.waitFor();

        try {
            FileInputStream input = new FileInputStream(logFile);
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(reader);

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
