package com.coungard.bootLoader.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProcessResultReader extends Thread{
    final InputStream is;
    final String type;
    final StringBuilder sb;

    public ArrayList<String> lines = new ArrayList<>();

    public ProcessResultReader(final InputStream is, String type){
        this.is = is;
        this.type = type;
        this.sb = new StringBuilder();
    }

    public void run()
    {
        try{
            final InputStreamReader isr = new InputStreamReader(is);
            final BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null)
            {
                lines.add(line);
                this.sb.append(line).append("\n");
            }
            br.close();
        }
        catch (final IOException ioe)
        {
            System.err.println(ioe.getMessage());
            throw new RuntimeException(ioe);
        }
    }
    @Override
    public String toString()
    {
        return this.sb.toString();
    }
}
