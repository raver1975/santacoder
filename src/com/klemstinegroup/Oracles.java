package com.klemstinegroup;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Oracles {
    public static String santacoderquery(int length, String prefix, String suffix, String temp) {
        ProcessBuilder pb = new ProcessBuilder("python", "runSantaCoder.py", "" + length, prefix.replace("\\", "\\\\\\\\").replace(" ", "`"), suffix.replace("\\", "\\\\\\\\").replace(" ", "`"), temp);
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
//            System.out.println(prefix);
//            System.out.println("-----------------");
//            System.out.println(textBuilder.toString());
//            System.out.println("-----------------");
//            System.out.println(suffix);
            return prefix + textBuilder + suffix;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public static String bloom(int length, String query) {
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
            System.out.println(textBuilder.toString());
            return textBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static String bloomLarge(int length, String query) {
        ProcessBuilder pb = new ProcessBuilder("python", "runBloomLarge.py", "" + length, query.replace("\\", "\\\\\\\\"));
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
            System.out.println(textBuilder.toString());
            return textBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
