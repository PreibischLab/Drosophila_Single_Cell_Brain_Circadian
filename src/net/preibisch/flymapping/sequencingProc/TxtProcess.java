package net.preibisch.flymapping.sequencingProc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TxtProcess {

	public static long lines(File file) throws IOException {
		long lineCount = Files.lines(Paths.get(file.getAbsolutePath())).count();
		return lineCount;
	}

	public static long columns(File file) throws IOException {
		String line = new Scanner(file, "UTF-8").nextLine();
		List<String> elm = Arrays.asList(line.split("	"));
		return elm.size();
	}

	public static void infos(File file) throws IOException {
		System.out.println("Infos File: " + file);
		System.out.println("Columns:" + columns(file));
		System.out.println("Lines: " + lines(file));
	}

	public static void infos(String file, long col, long lines) throws IOException {
		System.out.println("Infos File: " + file);
		System.out.println("Columns:" + col);
		System.out.println("Lines: " + lines);
	}

	

}
