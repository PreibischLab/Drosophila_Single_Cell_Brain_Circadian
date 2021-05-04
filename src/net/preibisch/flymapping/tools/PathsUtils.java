package net.preibisch.flymapping.tools;

import java.io.File;
import java.io.FileNotFoundException;

public class PathsUtils {
	public static File Data() throws FileNotFoundException {
		File f = new File(MyPaths.DATA_FOLDER,MyPaths.SEQUENCING_FOLDER);
		if(!(f.isDirectory()&&(f.exists()))) throw new FileNotFoundException();
		return f;	
	}
	
	public static File Result() {
		File f = new File(MyPaths.DATA_FOLDER,MyPaths.RESULT_FOLDER);
		if(!(f.isDirectory()&&(f.exists()))) f.mkdir();
		return f;	
	}
	
	public static File ResultFolder(String file) throws FileNotFoundException {
		File f = new File(Result(),file);
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

	public static File ResultFileFromString(String string) throws FileNotFoundException {
		String file = string + ".json";
		return Result(Result(), file);
	}
}
