package org.zisist.tools;

import org.apache.commons.lang.RandomStringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

/**
 *
 * Simple tool to generate text files with top players<br>
 *
 * Created by zis.tax@gmail.com on 25/10/2012 at 11:09 PM
 */
public class TopPlayerFileGenerator {
    private static Random random = new Random(new Date().getTime());

    public static void createFile(String name, long numOfRecords) {
        PrintWriter pw = null;
        try {
            File file = new File(name);
            pw = new PrintWriter(file);
            for(int i=0; i < numOfRecords; i++) {
                pw.append(RandomStringUtils.randomAlphabetic(10) + " " + Math.abs(random.nextInt()) + " " + Math.abs(random.nextInt()) + " " + "GR");
                if (i != numOfRecords-1)
                    pw.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (pw != null)
                pw.close();
        }
    }

    public static void main(String[] args) {
          createFile("leaders_120000.txt", 120000);
    }
}
