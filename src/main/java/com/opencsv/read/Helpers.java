package com.opencsv.read;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Helpers {

    public static Path twoColumnCsvPath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.TWO_COLUMN_CSV).toURI();
        return Paths.get(uri);
    }

    public static Path namedColumnCsvPath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.NAMED_COLUMN_CSV).toURI();
        return Paths.get(uri);
    }

    /**
     * Simple File Reader
     */

    public static String readFile(Path path) {
        String response = "";
        try {
            FileReader fr = new FileReader(path.toString());
            BufferedReader br = new BufferedReader(fr);
            String strLine;
            StringBuffer sb = new StringBuffer();
            while ((strLine = br.readLine()) != null) {
                sb.append(strLine);
            }
            response = sb.toString();
            System.out.println(response);
            fr.close();
            br.close();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return response;
    }

    /**
     * Message Helpers
     */

    public static void print(String msg) {
        System.out.println(msg);
    }

    public static void err(Exception ex) {
        System.out.println(Constants.GENERIC_EXCEPTION + " " + ex);
    }
}
