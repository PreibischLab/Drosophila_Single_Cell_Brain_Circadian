package net.preibisch.flymapping.sequencingProc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

	public static void infos(Path aerts_57k_cells_raw_path) throws IOException {
		System.out.println("Infos File: " + aerts_57k_cells_raw_path);
		System.out.println("Columns:" + columns(aerts_57k_cells_raw_path));
		System.out.println("Lines: " + lines(aerts_57k_cells_raw_path));
	}

	public static void infos(String file, long col, long lines) throws IOException {
		System.out.println("Infos File: " + file);
		System.out.println("Columns:" + col);
		System.out.println("Lines: " + lines);
	}

	public static int toChuncks(Path input, Path output_folder, String output_name, int chunk) throws IOException {
		int i = 0 , n_chunk = 0;
		
		long col = columns(input);
		long lines = lines(input);

		infos(input.toString(), col, lines);

		int totalChunks = ((int) (lines / chunk)) ;

		System.out.println("Expected total chunks: " + totalChunks);

		Scanner sc = new Scanner(input, "UTF-8");
		HashMap<String, List<String>> elements = new HashMap<String, List<String>>();
		String line = sc.nextLine();
		List<String> elm = Arrays.asList(line.split("	"));
		
		elements.put("id", elm);
		
		String outFile = "head_" + output_name + ".json";
		Path out = Paths.get(output_folder.toString(), outFile);
		save(out, elements);

		elements.clear();
		
		while (sc.hasNextLine()) {
			if (i >= chunk) {
				 outFile = n_chunk + "_" + output_name + ".json";
				 out = Paths.get(output_folder.toString(), outFile);
				save(out,elements);
				
				System.out.println(n_chunk+"/"+totalChunks+" | "+ out.toString());
				n_chunk++;
				elements.clear();
				i = 0 ; 
			}
			line = sc.nextLine();
			elm = new LinkedList<String>(Arrays.asList(line.split("	")));
			String index = elm.get(0);
			elm.remove(0);
			elements.put(index, elm);
			i++;
		}
		
		outFile = n_chunk + "_" + output_name + ".json";
		 out = Paths.get(output_folder.toString(), outFile);
		save(out,elements);
		return n_chunk;
	}

	private static void save(Path path, HashMap<String, List<String>> elements) throws UnsupportedEncodingException, FileNotFoundException {
		Writer writer = new OutputStreamWriter(new FileOutputStream(path.toString()) , "UTF-8");
        Gson gson = new GsonBuilder().create();
        gson.toJson(elements, writer);
	}

}
