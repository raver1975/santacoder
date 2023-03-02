package com.klemstinegroup;

import org.jd.core.v1.api.loader.Loader;
import org.jd.core.v1.api.loader.LoaderException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyLoader implements Loader {
    @Override
    public byte[] load(String internalName) throws LoaderException {
        String className = internalName;
        String classAsPath = className.replace('.', '/') + ".class";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(classAsPath);
//        InputStream is = this.getClass().getResourceAsStream("/" + internalName + ".class");

        if (is == null) {
            return null;
        } else {
            try (InputStream in = is; ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int read = in.read(buffer);

                while (read > 0) {
                    out.write(buffer, 0, read);
                    read = in.read(buffer);
                }

                return out.toByteArray();
            } catch (IOException e) {
                throw new LoaderException(e);
            }
        }
    }

    @Override
    public boolean canLoad(String internalName) {
        String className = internalName;
        String classAsPath = className.replace('.', '/') + ".class";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(classAsPath);
        if (is == null) return false;
        return true;
        //return this.getClass().getResource("/" + internalName + ".class") != null;
    }
}
