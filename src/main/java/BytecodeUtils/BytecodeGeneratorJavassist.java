package BytecodeUtils;

import javassist.ClassPool;
import javassist.bytecode.ClassFile;

import java.io.*;
import java.util.Scanner;

public class BytecodeGeneratorJavassist extends BytecodeGenerator{
    private static int g = 1;

    @Override
    protected String getBytecode(File file) throws IOException{
        if (true) return null;

        ClassPool cp = ClassPool.getDefault();
        ClassFile cf = cp.makeClass(new FileInputStream(file)).getClassFile();
        /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
        cf.write(new DataOutputStream(stream));*/

        // Writing output to the temporary file
        String fileName = (g++) + "TempJavassist.class";
        cf.write(new DataOutputStream(new FileOutputStream(fileName)));
        // Reading output
        String path = new File("").getAbsolutePath() + "/" + fileName;
        String output = readFile(path);
        // Deleting the temporary file
        (new File(path)).delete();
        return output;
    }

    private String readFile(String path) throws IOException {
        Scanner scanner = new Scanner(new File(path));
        StringBuilder content = new StringBuilder();
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            System.out.println(str);
            content.append(str).append("\n");
        }
        scanner.close();
        return content.toString();
    }
}
