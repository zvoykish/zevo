package com.zvoykish.zevo.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUtils {

    public static void extractEveryNthLineFromTextFile(String filename, int n) throws IOException {
        if (n <= 0) {
            throw new RuntimeException("N must be >= 0!");
        }

        FileReader fis = new FileReader(filename);
        BufferedReader br = new BufferedReader(fis);
        PrintWriter pw = new PrintWriter(filename + "_cut");
        String line = br.readLine();
        pw.println(line);
        int count = 0;
        while ((line = br.readLine()) != null) {
            if ((count++) % n == 0) {
                pw.println(line);
            }
        }
        pw.close();
        fis.close();
        br.close();
    }
}