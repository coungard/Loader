package com.coungard.bootLoader.util;

public class Utils {

    public static Process runCmd(String[] args) {
        try {
            Runtime runtime = Runtime.getRuntime();
            return runtime.exec(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
