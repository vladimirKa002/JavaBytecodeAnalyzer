package BytecodeUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class BytecodeGeneratorJavap extends BytecodeGenerator{
    @Override
    protected String getBytecode(File file) throws IOException {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"javap", "-p", file.getAbsolutePath()};
        Process proc = rt.exec(commands);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        StringBuilder output = new StringBuilder();
        String s;
        while ((s = stdInput.readLine()) != null) {
            output.append(s).append("\n");
        }
        return output.toString();
    }
}
