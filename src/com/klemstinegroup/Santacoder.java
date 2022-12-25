package com.klemstinegroup;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Santacoder {

    public static void main(String[] args) {
        new Santacoder();
    }

    public Santacoder() {
        bloom(400,"a rhyming song about nothing and everything:");
        santacoderquery(400,"/**\n" +
                "* Returns an Image object that can then be painted on the screen. \n" +
                "*\n" +
                "* @param  description  a description of the image\n" +
                "* @return      the image described by description\n" +
                "*/\n" +
                "public Image getImage(String description) {" +
                "\n", "return image;\n}");
    }

    public String santacoderquery(int length, String prefix, String suffix) {
        ProcessBuilder pb = new ProcessBuilder("python", "runSantaCoder.py", "" + length, prefix.replace("\\", "\\\\\\\\"), suffix.replace("\\", "\\\\\\\\"));
//        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process process = pb.start();
            long time = System.currentTimeMillis();
            process.waitFor();
            time = System.currentTimeMillis() - time;
            System.out.println("time:" + time);
            InputStream in = process.getInputStream();
            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader
                    (in, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }
            System.out.println(prefix);
//            System.out.println("-----------------");
            System.out.println(textBuilder.toString());
//            System.out.println("-----------------");
            System.out.println(suffix);
            return textBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public String bloom(int length,String query) {
        ProcessBuilder pb = new ProcessBuilder("python", "runBloom.py", "" + length, query.replace("\\", "\\\\\\\\"));
//        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process process = pb.start();
            long time = System.currentTimeMillis();
            process.waitFor();
            time = System.currentTimeMillis() - time;
            System.out.println("time:" + time);
            InputStream in = process.getInputStream();
            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader
                    (in, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }
            System.out.println(query);
//            System.out.println("-----------------");
            System.out.println(textBuilder.toString());
//            System.out.println("-----------------");
            return textBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}





