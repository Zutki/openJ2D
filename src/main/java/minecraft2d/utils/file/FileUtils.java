package minecraft2d.utils.file;

import java.io.File;

public class FileUtils {
    public static File addPaths(File file1, String file2) {
        return new File(file1.getAbsoluteFile()+"/"+file2);
    }
}
