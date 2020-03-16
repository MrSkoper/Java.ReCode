package site.skop.java.recode.deobfuscator.substances;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JavaArchiveSubstancesTest {

    private File file;
    private final List<CtClass> classesList = new ArrayList<>();

    private ClassPool pool = ClassPool.getDefault();

    {
        CtClass testClass = pool.makeClass("TestClass");
        testClass.addField(CtField.make("String testField = \"Test\";", testClass));
        this.classesList.add(testClass);

        CtClass otherClass = pool.makeClass("OtherClass");
        otherClass.addField(CtField.make("String otherField = \"Other\";", otherClass));
        this.classesList.add(otherClass);
    }

    JavaArchiveSubstancesTest () throws Exception {

    }

    File getFile (Path tempDir) throws IOException, CannotCompileException {
        if (this.file == null) createFile(tempDir);
        return this.file;
    }

    void createFile (Path tempDir) throws IOException, CannotCompileException {
        this.file = Files.createFile(tempDir.resolve(getClass().getName())).toFile();
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(this.file));

        for (CtClass ctClass : this.classesList) {
            jarOutputStream.putNextEntry(new ZipEntry(ctClass.getName()));
            jarOutputStream.write(ctClass.toBytecode());
            ctClass.defrost();
        }

        jarOutputStream.close();
    }

    @Test
    void next(@TempDir Path tempDir) throws Exception {
        final JavaArchiveSubstances javaArchiveSubstances = new JavaArchiveSubstances(getFile(tempDir));

        assertNotNull(javaArchiveSubstances);

        for (int index = 0; javaArchiveSubstances.hasNext(); index++) {
            CtClass get = javaArchiveSubstances.next();
            CtClass create = this.classesList.get(index);

            assertNotNull(get);
            assertNotNull(create);

        }

        javaArchiveSubstances.close();
    }

    @Test
    void remove(@TempDir Path tempDir) throws Exception {
        final JavaArchiveSubstances javaArchiveSubstances = new JavaArchiveSubstances(getFile(tempDir));

        assertNotNull(javaArchiveSubstances);

        assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                javaArchiveSubstances.remove();
            }
        });

        javaArchiveSubstances.close();
    }

}