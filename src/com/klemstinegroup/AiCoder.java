package com.klemstinegroup;

import com.github.plexpt.chatgpt.ChatGTP;
import com.github.plexpt.chatgpt.Chatbot;
import com.github.plexpt.chatgpt.Config;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class AiCoder {

    public static void main(String[] args) {
        new AiCoder();
    }

    public AiCoder() {
        new Thread(new Runnable() {
            String g = "hello chatbot";

            @Override
            public void run() {
                try {
                    Thread.sleep(120000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                while (true) {
                    g = chatGPT(g);
                    try {
                        Thread.sleep(180000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

//        bloom(400,"A well thought out, step by step guide to world domination\nStep 1:");
//        santacoderquery(400,"/**\n" +
//                "* Returns an Image object that can then be painted on the screen. \n" +
//                "*\n" +
//                "* @param  description  a description of the image\n" +
//                "* @return      the image described by description\n" +
//                "*/\n" +
//                "public Image getImage(String description) {" +
//                "\n", "return image;\n}");
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


//    https://github.com/ChatGPT-Hackers/ChatGPT
    //python3 -m revChatGPT
    //pip3 install revChatGPT --upgrade

    static Process processchatgpt;
    static StringBuilder textBuilder = new StringBuilder();

    static {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ProcessBuilder pb = new ProcessBuilder("python3", "-m", "revChatGPT");
                pb.redirectError(ProcessBuilder.Redirect.INHERIT);
                try {
                    processchatgpt = pb.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try (Reader reader = new BufferedReader(new InputStreamReader
                        (processchatgpt.getInputStream(), Charset.forName(StandardCharsets.UTF_8.name())))) {
                    int c = 0;
                    while ((c = reader.read()) != -1) {
                        System.out.print((char)c);
                        System.out.flush();
                        textBuilder.append((char) c);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();

    }

    public String chatGPT(String prompt) {
        System.out.println("prompt:" + prompt);
//        InputStream is = processchatgpt.getInputStream();
        OutputStream os = processchatgpt.getOutputStream();

        try {
            os.write((prompt + "\n\n").getBytes());
            os.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        textBuilder.delete(0,textBuilder.length());
        while(textBuilder.length()==0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        String text=textBuilder.toString();
        System.out.println(text);
        return text;
    }

//Get sessionToken
//https://github.com/acheong08/ChatGPT/wiki/Setup#token-authentication
//    https://github.com/ChatGPT-Hackers/ChatGPT/wiki/Setup
/*    public String chatGPT(String query){
        Config config = new Config();
//        config.setSession_token("eyJhbGciOiJkaXIiLCJlbmMiOiJBMjU2R0NNIn0..-gFQj8L0PiMoYWCz.Jug_Bm7vAV3098WlM82JazFqSVeov3HE6Ky0FyoFOlz3yKi3wRqiQOMo4162xeYAs_uMJwS0RUyZhKEvuvmcEI5PofTw-d_ijfKHxyNyn4adQvUXTYlse3oXAC_bfudmflHQfaEFPewMiWW1jitCmCz1iK1lBFNYd9AhRl5S1SR4YBr39VkAyF7tPF_6jjrrlscaFNr55cznghChfHN7r4lXE_63J5GCNGYYPGWgwDAN2YF6S8sVUPDNwyB09dZe0vMxfL_CutwmaiJp_6XCtvFoqcqYWH9qzMYI8_gMXogPSbYW_Nc8CqR-2XUBQIZv5aAPxki0G8c2LupXuVE4BccokEPIOA32elquY5uaa_Xa29KT9mh2j9EC1s3rN5SDuSYzF67CpVxxR97H7eEtIh8cEAHL5ELykM1OB-1J5y_wfkeEqIvjWd2EKqnkG-y48bUvUOs9HXOKnrPdiQCZut0aixphO6_CjktQUPW8rxg0Ycfouov3z4kNpJStjSm_kCDr1V0EdU5OL1gcQcU-5haE9uRk4HfLO2PGbo53QyhKbXuSJ-D7Y23dn-WmYk8RzMMWMF4JHHG9eKLBXK-M-paLz8tXSAPmWn7o82I5vpHbxFXDlDdCKyxOA0UKk6C3ROlIJ0TFbnw0lkrRucFe6kfEVinRVQFhqAKAXHJ323tPXQuSEuWAQqf-sf3oCA7bwSWGdEnKRQzIwYn07VehdjDK9QAbF_G9fAZi8_ZPr2N8TKYdA9YH1OU8lluXzHeSVDdWPtDDxT0aOPaDiWPuH1xHti4WYKWRoe0MjNFCHskPbvp1tXnUhdJGpvEAlEc_tFj3Gl0ULu1iXB3xCZWjET2dGflLWr72ckMdNigWeSXYLfkCAtgDi0OQd-nOuydAgCm5eI_lJzJDG9ywTXe5YdWF4IMxYwG1LKZYGM7J-o9n9VdSONlN2HpIIMwBQKq1pYDEXg7vXcHHR-KeD-oC2-YhdOf3LG3DuZ-BIQyjH3WcMQCsDwv7Nh6Q6CeAym0qZ214PRB0wMCgjJG_NxUo7Ixhk2mfvvQewGSDQbFgDxMN5eIFJmsonIVTaECAcUFqvLdY1a70MOChoytCF0zgrNRX74vFRdidzLiuk8O0yuLLBI41Zx8q6AaPs2BHgXujzVPnf5S9gUwJTnnD6ZIvTqowa3HiSWkAKsr3W6mKvPbPPX9LMF9lPZGzDuOXQ45OvkSGka4COXXMkYstNztJwRI80JNFx_mY-Y-SOBg9abs9VS7qQEyMk3_PsCldvY2FPaabKx1agc3PpGsGxmPT5GMfIomL131aGOFo9KewIFzpcglLayvskg_Ytrpus-zmtBPjskGbi4DWcwZHjmLDUctobfBRkFBDp8XkyKOQGxfOO0xaA2-kr17OUrCbTbEazatkSq_fBmmZrr57W8E5XzVoNTEgrnxJpkJA7m5gGMPZdbtUQeBhpIYtE8ejQ9V9SpksUWctfNCv0dDlhuHF0qZha9Xi8AgYwNrrI9KPc31Z2tBC1EvhCh-dha4JcD8G7opa5yqClmT3MNZ9B7DtIr9NyTEY3dRYXO3q6ImxIDZ4R92-fMx_tb166zx6iujcFcfTfaovMIc7hYF3LuVh9FDNrMDDflSxX5IYmO9oJU6n9mdeAgq9sclucDBVni1EQP57KawRzII3s2JmIAEiYgP-e7P01zTZdBFDSr2_hYbS-5pg96x0V-6pCn6HI7BxwYrrfVSRbu7aevZS5KCRsYamEf-qXmgOQG3XN_M5YYMIyzDVj3I7GVpiPM5cPljzYaiLLFAp6oAMLLw6ErMDxqF58dGG0JBrmZ8zBiJszem2r4X5j0jBQc5hu1dTWExnJrFd5UZYQ2J1ZxmbZ9nUFBFzabmpSoeaLLRdcaIA_3r02eHRFw3ng1W1-NP8f_39wiXE3MkdvHhCJaAErBHFCd6Kyi09lgSy-FL_SLHJOkK6ZbG5l-_P92fCDnWaECAxc08RhUH_Kv5y-wL_8SC5VrIz447kMO1Grhb01nuaT7GIC02T6-rKG-0WrJYtB-SCZThpmRxYgCCiQVlFnNMNknbioVM47dMdMKzIi9kN2zvM0b1dqjBZm9s_6sgI3Ali3jU9vNfFpmbV_CcIT-rkotBbHc5pCwFWU8flNJUgxv8PKzkPes6TWD-NDmcRUqzf0vVddN0-qkVTcczI__FVsCuki3x5pNn0TE--dmagzO-wiVUb41iGVFfX5V3YiwqOj-WMJwua_9AX9ghQM6pcFCXk4f0QgEGLluGRvNohE_RWSIHUwWYyM1FN8EvJ92gdFQ.V7ETVWfaQxih13vAV0jdAw");
        config.setEmail(Statics.email);
        config.setPassword(Statics.password);
        config.setSession_token(Statics.session);
        config.setUserAgent("netscape");
        Chatbot chatbot = new Chatbot(config);
        Map<String, Object> chatResponse = chatbot.getChatResponse(query);
        String response=chatResponse.get("message").toString();
        System.out.println(response);
        return response;
    }*/

    public String bloom(int length, String query) {
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

}





