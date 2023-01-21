package BytecodeUtils;

import Controllers.ControllersUtility;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BytecodeGenerator {

    /**
     * @param files takes list of files to take bytecodes
     * @return a hash map which maps names of given files to corresponding bytecode
     */
    public Map<String, String> generateBytecodes(List<File> files) {
        return files.stream().collect(Collectors.toMap(
                file -> file.getName().split("\\.")[0],
                file -> {
                    try {
                        return getBytecode(file);
                    } catch (Exception e) {
                        ControllersUtility
                                .getInstance()
                                .showInfoBlocking(
                                        "Error occurred while generating bytecode for file " + file.getName());
                        throw new RuntimeException(e);
                    }
                }
        ));
    }

    /**
     * @param file takes a file to create bytecode
     * @return bytecode of the class
     */
    protected abstract String getBytecode(File file) throws IOException;

    public String getName(){
        String[] name = this.getClass().getName().split("\\.");
        return name[name.length - 1];
    }
}
