package net.preibisch.flymapping.sequencingProc;

import java.io.File;
import java.io.FileNotFoundException;

public class testDir {
public static void main(String[] args) throws FileNotFoundException {
	File f = MyPaths.Result();
	System.out.println(f.getAbsolutePath());
}
}
