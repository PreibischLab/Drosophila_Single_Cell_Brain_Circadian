package net.preibisch.flymapping.sequencingProc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TxtProcess {

	public static long lines(Path path) throws IOException {
		long lineCount = Files.lines(path).count();
		return lineCount;
	}

	public static long columns(Path path) throws IOException {
		String line = new Scanner(path, "UTF-8").nextLine();
		List<String> elm = Arrays.asList(line.split("	"));
		return elm.size();
	}

	public static void infos(Path path) throws IOException {
		System.out.println("Infos File: " + path);
		System.out.println("Columns:" + columns(path));
		System.out.println("Lines: " + lines(path));
	}

	public static void infos(String file, long col, long lines) throws IOException {
		System.out.println("Infos File: " + file);
		System.out.println("Columns:" + col);
		System.out.println("Lines: " + lines);
	}

	

}
