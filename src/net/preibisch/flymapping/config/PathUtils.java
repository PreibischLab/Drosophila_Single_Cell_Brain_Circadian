package net.preibisch.flymapping.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class PathUtils {
	public static File Data() throws FileNotFoundException {
		File f = new File(Paths.get("").toAbsolutePath().getParent().getParent().resolve(MyPaths.SEQUENCING_FOLDER).toString());
		if(!(f.isDirectory()&&(f.exists()))) throw new FileNotFoundException();
		return f;	
	}
	
	public static File Result() throws FileNotFoundException {
		File f = new File(Data(),MyPaths.RESULT_FOLDER);
		if(!(f.isDirectory()&&(f.exists()))) f.mkdir();
		return f;	
	}
	
	public static File ResultFolder(String file) throws FileNotFoundException {
		File f = new File(Result(),MyPaths.RESULT_FOLDER);
		if(!(f.isDirectory()&&(f.exists()))) f.mkdir();
		return f;	
	}
	
	
	public static File File(String file) throws FileNotFoundException {
		File f = new File(Data(), file);
		if(!(f.exists())) {
			System.out.println("File path: "+f.getAbsolutePath());
			throw new FileNotFoundException();}
		return f;
	}
	
	public static File Result(File folder, String file) throws FileNotFoundException {
		File f = new File(folder, file);
		return f;
	}

	public static File ResultFile(String file) throws FileNotFoundException {
		return Result(Result(), file);
	}
}
