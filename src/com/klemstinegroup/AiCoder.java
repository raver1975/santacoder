package com.klemstinegroup;

import com.baeldung.inmemorycompilation.InMemoryFileManager;
import com.baeldung.inmemorycompilation.JavaSourceFromString;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LineNumberAttribute;
import org.jd.core.v1.ClassFileToJavaSourceDecompiler;
import org.jd.core.v1.api.loader.Loader;
import org.jd.core.v1.api.loader.LoaderException;
import org.jd.core.v1.api.printer.Printer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.instrument.ClassDefinition;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AiCoder {
    MainInterface mi = new MainInterface();
    public MyPrinter myPrinter = new MyPrinter();
    private MyLoader myLoader = new MyLoader();

    public static void main(String[] args) {
        new AiCoder();
    }

    public AiCoder() {
        openFrame();

        String testClass = "com.klemstinegroup.AiCoder";
        String source = getClass(testClass);
        System.out.println(source.substring(0, Math.min(100, source.length())));
        System.out.println("-------------------------------------------");
       /* for (int i = 0; i < 3; i++) {
            System.out.println("\niter:" + i);
            whenStringIsCompiled_ThenCodeShouldExecute(testClass, source,false);
            try {
                Method method = this.getClass().getDeclaredMethod("toLowerCase", String.class);
                System.out.println(method.invoke(this, "TEST"));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }*/
//        Oracles.bloom(400, "10 step guide to creating general artificial intelligence:\n1. Build a language model that can generate plans.\n2. Build a language model that can generate code.\n3. Combine the two models by");

//        while (true) {
//            String sourcecode = getClass("com.klemstinegroup.TestClass");
//            sourcecode = "package com.klemstinegroup;\n" + sourcecode;
//            System.out.println("-------------------");
//            String prefix = sourcecode.substring(0, sourcecode.length() - 2) + "\n /**\nquit application\n*/\npublic void quit(){";
//        prefix = prefix.replaceAll("TestClass", "TestClass1");
//        String suffix="\n}\n}\n";
//            System.out.println(prefix + suffix);
//            String newcode = Oracles.santacoderquery(20, prefix, suffix, "1.5");
//            System.out.println(newcode);
//            Object obj=whenStringIsCompiled_ThenCodeShouldExecute("com.klemstinegroup.TestClass1", newcode,true);
//            if (obj!=null) {
//                System.out.println("invoking quit");
//                try {
//                    Method method = obj.getClass().getMethod("quit");
//                    method.invoke(obj);
//                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("forbidden spot");
//            }
//        }


    }


    public String getClass(String c) {

        ClassFileToJavaSourceDecompiler decompiler = new ClassFileToJavaSourceDecompiler();

        try {
            decompiler.decompile(myLoader, myPrinter, c);
        } catch (Exception e) {
            return null;
        }
        String source = myPrinter.getSource(true);
        return source;
    }

    public Class<?> compile(String QUALIFIED_CLASS_NAME, String SOURCE_CODE, boolean obej) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        InMemoryFileManager manager = new InMemoryFileManager(compiler.getStandardFileManager(null, null, null));

        List<JavaFileObject> sourceFiles = Collections.singletonList(new JavaSourceFromString(QUALIFIED_CLASS_NAME, SOURCE_CODE));
        ArrayList<String> options = new ArrayList<>();
//            options.add("--add-exports=java.base/jdk.internal=ALL-UNNAMED");
//            options.add("--add-exports=java.base/jdk.internal.vm.annotation=ALL-UNNAMED");
//            options.add("--add-exports=java.base/java.lang=ALL-UNNAMED");
//            options.add("-XDignore.symbol.file");
//            options.addAll(Arrays.asList("-classpath",System.getProperty("java.class.path")));

        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostics, options, null, sourceFiles);

        boolean result = task.call();

        if (!result) {
            diagnostics.getDiagnostics().forEach(d -> System.out.println(String.valueOf(d)));
        } else {
            ClassLoader classLoader = manager.getClassLoader(null);
            try {
                return classLoader.loadClass(QUALIFIED_CLASS_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public Object whenStringIsCompiled_ThenCodeShouldExecute(String QUALIFIED_CLASS_NAME, String SOURCE_CODE, boolean obej) {
        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
            InMemoryFileManager manager = new InMemoryFileManager(compiler.getStandardFileManager(null, null, null));

            List<JavaFileObject> sourceFiles = Collections.singletonList(new JavaSourceFromString(QUALIFIED_CLASS_NAME, SOURCE_CODE));
            ArrayList<String> options = new ArrayList<>();
//            options.add("--add-exports=java.base/jdk.internal=ALL-UNNAMED");
//            options.add("--add-exports=java.base/jdk.internal.vm.annotation=ALL-UNNAMED");
//            options.add("--add-exports=java.base/java.lang=ALL-UNNAMED");
//            options.add("-XDignore.symbol.file");
//            options.addAll(Arrays.asList("-classpath",System.getProperty("java.class.path")));

            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostics, options, null, sourceFiles);

            boolean result = task.call();

            if (!result) {
                diagnostics.getDiagnostics().forEach(d -> System.out.println(String.valueOf(d)));
            } else {
                ClassLoader classLoader = manager.getClassLoader(null);
                Class<?> clazz = classLoader.loadClass(QUALIFIED_CLASS_NAME);


                if (obej) {

                    return clazz.newInstance();
                } else {
                    // find a reference to the class and method you wish to inject
                    ClassPool classPool = ClassPool.getDefault();
                    CtClass ctClass = null;
                    if (classPool.getOrNull(QUALIFIED_CLASS_NAME) == null) {
                        ctClass = classPool.makeClass(new ByteArrayInputStream(manager.getBytesMap().get(QUALIFIED_CLASS_NAME).getBytes()));
                    } else {
                        ctClass = classPool.get(QUALIFIED_CLASS_NAME);
                    }
                    System.out.println(ctClass.getName());
                    ctClass.stopPruning(true);

                    // javaassist freezes methods if their bytecode is saved
                    // defrost so we can still make changes.
                    if (ctClass.isFrozen()) {
                        try {
                            ctClass.defrost();
                        } catch (RuntimeException r) {
                            r.printStackTrace();
                        }
                    }
//                CtMethod newmethod = CtNewMethod.make("    public String toLowerCase(String s){\n" +
//                        "        return s.toLowerCase();\n" +
//                        "    }",ctClass);
//                ctClass.addMethod(newmethod);
                    CtMethod method = ctClass.getDeclaredMethod("toLowerCase"); // populate this from ctClass however you wish
                    method.setBody("{ System.out.println(\"Wheeeeee!" + ((int) (Math.random() * 1000f)) + "\"); return null;}");
                    byte[] bytecode = ctClass.toBytecode();

                    ClassDefinition definition = new ClassDefinition(clazz, bytecode);
                    RedefineClassAgent.redefineClasses(definition);
                }

//            Assertions.assertInstanceOf(InMemoryClass.class, instanceOfClass);

//                instanceOfClass.runCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toLowerCase(String s) {
        return s.toLowerCase();
    }

    public void openFrame() {
        JFrame frame = new JFrame("AI Coder");

        mi.compileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("invoking quit");
                Object obj = whenStringIsCompiled_ThenCodeShouldExecute("com.klemstinegroup.TestClass1", mi.santaresult.getText(), true);
                if (obj != null) {
                    System.out.println("invoking quit");
                    try {
                        Method method = obj.getClass().getMethod("quit");
                        method.invoke(obj);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println("forbidden spot");
                }
            }
        });
        mi.runbloom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked bloom");
                mi.runbloom.setEnabled(false);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mi.bloomresult.setText(Oracles.bloom(400, mi.bloomprompt.getText()));
                        mi.runbloom.setEnabled(true);
                    }
                }).start();
            }
        });
        mi.runsanta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked santa");
                mi.runsanta.setEnabled(false);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String[] s = mi.santaprompt.getText().split("<here>");
                        mi.santaresult.setText(Oracles.santacoderquery(400, s[0], s[1], "1.0"));
                        mi.runsanta.setEnabled(true);

                        Class<?> clazz = compile("com.klemstinegroup.TestClass1", mi.santaresult.getText(), true);
                        mi.compileButton.setEnabled(clazz != null);
                    }
                }).start();

            }
        });
        String sourcecode = getClass("com.klemstinegroup.TestClass");
        sourcecode = "package com.klemstinegroup;\n" + sourcecode;

            String prefix = sourcecode.substring(0, sourcecode.length() - 2) + "\n /**\nquit application\n*/\npublic void quit(){";
        prefix = prefix.replaceAll("TestClass", "TestClass1");
        String suffix="\n}\n}\n";
        String outt=prefix+"\n<here>"+suffix;
        mi.santaprompt.setText(outt);


        MessageConsole mc = new MessageConsole(mi.Sytem);
        mc.redirectOut(null, System.out);
        mc.redirectErr(Color.RED, System.err);
        mc.setMessageLines(10);
        mi.panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(mi.$$$getRootComponent$$$());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(640, 480);
        frame.setVisible(true);
    }
}





