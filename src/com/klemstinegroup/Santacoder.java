package com.klemstinegroup;

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Santacoder {

    public static void main(String[] args) {
        new Santacoder();
    }

    public Santacoder() {
//        String checkpoint = "bigcode/santacoder";
//
//        HuggingFaceTokenizer tokenizer = HuggingFaceTokenizer.newInstance(checkpoint);
//
//        List<String> out = tokenizer.tokenize("//hello world java class");
//        for (String s : out) {
//            System.out.println(s);
//        }
//        python runLocal.py 100 $'//greatest common denominator\npublic long gcd(long a,long b){' $'}'

        String prefix = "//polynomial time factoring of a biginteger\npublic BitInteger factor(BigInteger n){";
        String suffix = "}";
        int length=1000;
        ProcessBuilder pb = new ProcessBuilder("python", "runLocal.py", ""+length, prefix.replace("\\","\\\\\\\\"), suffix.replace("\\","\\\\\\\\"));
//        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process process = pb.start();
            long time=System.currentTimeMillis();
            process.waitFor();
            time=System.currentTimeMillis()-time;
            System.out.println("time:"+time);
            InputStream in =process.getInputStream();
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

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}





