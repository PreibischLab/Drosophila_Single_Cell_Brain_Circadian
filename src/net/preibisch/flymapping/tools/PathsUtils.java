package net.preibisch.flymapping.tools;

import java.io.File;
import java.io.FileNotFoundException;

public class PathsUtils {
    public static File getInputFolder() throws FileNotFoundException {
        File f = new File(MyPaths.DATA_FOLDER, MyPaths.INPUT_FOLDER);
        if (!(f.isDirectory() && (f.exists())))
            throw new FileNotFoundException("File not found: " + f.getAbsolutePath());
        return f;
    }

    public static File getOrCreateResultFolder() {
        File f = new File(MyPaths.DATA_FOLDER, MyPaths.RESULT_FOLDER);
        if (!(f.isDirectory() && (f.exists()))) f.mkdir();
        return f;
    }

    public static File getOrCreateResultSubfolder(String file) {
        File f = new File(getOrCreateResultFolder(), file);
        if (!(f.isDirectory() && (f.exists()))) f.mkdir();
        return f;
    }

    public static File getOrCreateFolder(String file) {
        File f = new File(file);
        if (!(f.isDirectory() && (f.exists()))) f.mkdir();
        return f;
    }

    public static File getInputPathForFile(String file) throws FileNotFoundException {
        File f = new File(getInputFolder(), file);
        if (!(f.exists())) {
            System.out.println("File path: " + f.getAbsolutePath());
            throw new FileNotFoundException("File not found: " + f.getAbsolutePath());
        }
        return f;
    }

    public static File getPathForResultFile(String file) {
        return new File(getOrCreateResultFolder(), file);
    }

    @Deprecated
    public static File Result(File folder, String file) {
        return new File(folder, file);
    }

    @Deprecated
    public static File File(String file) throws FileNotFoundException {
        return getInputPathForFile(file);
    }

    @Deprecated
    public static File ResultFile(String file) throws FileNotFoundException {
        return getPathForResultFile(file);
    }

    @Deprecated
    public static File ResultFileFromString(String filename) {
        return getPathForResultFile(filename + ".json");
    }
}
