package BytecodeUtils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.*;

public class BytecodeGeneratorASM extends BytecodeGenerator{
    @Override
    protected String getBytecode(File file) throws IOException {
        ClassReader reader = new ClassReader(new FileInputStream(file));
        StringWriter sw = new StringWriter();
        TraceClassVisitor tcv = new TraceClassVisitor(new PrintWriter(sw));
        reader.accept(tcv, 0);
        return sw.toString();
    }
}
