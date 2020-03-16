package site.skop.java.recode.deobfuscator.substances;

import javassist.ClassPool;
import javassist.CtClass;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JavaArchiveSubstances implements SubstancesInterface, AutoCloseable {

    private JarFile jarFile;
    private ClassPool classPool = ClassPool.getDefault();
    private Enumeration<JarEntry> entries;

    public JavaArchiveSubstances(final File fileArchive) throws IOException {
        this(new JarFile(fileArchive, true));
    }

    public JavaArchiveSubstances(final String pathArchive) throws IOException {
        this(new File(pathArchive));
    }

    public JavaArchiveSubstances(final JarFile jarFileArchive) {
        this.jarFile = jarFileArchive;
        this.entries = jarFileArchive.entries();
    }

    @Override
    public boolean hasNext() {
        return this.entries.hasMoreElements();
    }

    @Override
    public CtClass next() {
        try {
            JarEntry element = this.entries.nextElement();
            BufferedInputStream inputStream = new BufferedInputStream(jarFile.getInputStream(element));

            CtClass clazz = this.classPool.makeClass(inputStream);

            inputStream.close();

            return clazz;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove() {
        throw new IllegalStateException("Can't remove item out jar entry enumerations");
    }

    @Override
    public void close() throws Exception {
        jarFile.close();
    }
}
