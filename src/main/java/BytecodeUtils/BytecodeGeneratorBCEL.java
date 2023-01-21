package BytecodeUtils;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BytecodeGeneratorBCEL extends BytecodeGenerator{
    @Override
    protected String getBytecode(File file) throws IOException {
        ClassParser parser = new ClassParser(new FileInputStream(file), file.getName());
        JavaClass javaClass = parser.parse();
        return javaClass.toString();
    }
}
