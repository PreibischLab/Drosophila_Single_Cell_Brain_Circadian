package net.preibisch.flymapping.sequencingProc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class Aerts_57k_cells {
	int chunk;
	HashMap<String, List<String>> elments;
	
	public Aerts_57k_cells(HashMap<String, List<String>> elments,int chunk) {
		this.elments = elments;
		this.chunk = chunk;
	}

	public static Aerts_57k_cells fromFile(String path) throws FileNotFoundException {
		
		Gson gson = new Gson();
	
		JsonReader json = new JsonReader(new FileReader(path));
		return gson.fromJson(json, Aerts_57k_cells.class);
		
	}
	
	@Override
	public String toString() {
		Set<Entry<String, List<String>>> entry = elments.entrySet();
		return "Size: "+ elments.size() + "List: " + elments.values().iterator().next().size();
	}
	
	public static int toChuncks(Path input, Path output_folder, String output_name, int chunk) throws IOException {
		int i = 0 , n_chunk = 0;
		
		long col = TxtProcess.columns(input);
		long lines = TxtProcess.lines(input);

		TxtProcess.infos(input.toString(), col, lines);

		int totalChunks = ((int) (lines / chunk)) ;

		System.out.println("Expected total chunks: " + totalChunks);

		Scanner sc = new Scanner(input, "UTF-8");
		HashMap<String, List<String>> elements = new HashMap<String, List<String>>();
		String line = sc.nextLine();
		List<String> elm = Arrays.asList(line.split("	"));
		
		elements.put("id", elm);
		
		String outFile = "head_" + output_name + ".json";
		Path out = Paths.get(output_folder.toString(), outFile);
		save(-1,out, elements);

		elements.clear();
		
		while (sc.hasNextLine()) {
			if (i >= chunk) {
				 outFile = n_chunk + "_" + output_name + ".json";
				 out = Paths.get(output_folder.toString(), outFile);
				save(chunk,out,elements);
				
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
		save(chunk,out,elements);
		return n_chunk;
	}

	private static void save(int chunk, Path path, HashMap<String, List<String>> elements) throws UnsupportedEncodingException, FileNotFoundException {
		
		Writer writer = new OutputStreamWriter(new FileOutputStream(path.toString()) , "UTF-8");
		Aerts_57k_cells aerts_57k_cells = new Aerts_57k_cells(elements, chunk);
        Gson gson = new GsonBuilder().create();
        gson.toJson(aerts_57k_cells, writer);
	}


}
