package BytecodeUtils;

import javassist.ClassPool;
import javassist.bytecode.ClassFile;

import java.io.*;

public class BytecodeGeneratorJavassist extends BytecodeGenerator{
    @Override
    protected String getBytecode(File file) throws IOException{
        ClassPool cp = ClassPool.getDefault();
        ClassFile cf = cp.makeClass(new FileInputStream(file)).getClassFile();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        cf.write(new DataOutputStream(stream));
        return new String(stream.toByteArray());
    }
}
